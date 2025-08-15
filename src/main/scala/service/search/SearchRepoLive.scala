package service.search

import db.MovieData
import exception.SearchError.ResourceNotFound
import models.Movie
import utils.Common.{termsFor, tokenize}
import zio.{IO, ZIO, ZLayer}

final case class SearchRepoLive(db: MovieData) extends SearchRepo {

  override def searchByKeyword(query: String): IO[ResourceNotFound, List[Movie]] = {

    val qTerms = tokenize(query)
    if (qTerms.isEmpty) ZIO.fail(ResourceNotFound(query))

    val docs = db.movies.toVector
    val N    = docs.size.toDouble
    if (N == 0.0) ZIO.fail(ResourceNotFound(query))

    val tfPerDoc =
      docs.map { m =>
        val ts = termsFor(m)
        ts.groupBy(identity).view.mapValues(_.size.toDouble).toMap
      }

    val df = scala.collection.mutable.Map.empty[String, Int].withDefaultValue(0)
    tfPerDoc.foreach { tf =>
      tf.keys.foreach { t => df(t) = df(t) + 1 }
    }
    val idf =
      df.iterator.map { case (t, dfi) =>
        t -> (math.log((N + 1.0) / (dfi + 1.0)) + 1.0)
      }.toMap

    val docWeights =
      tfPerDoc.map { tf =>
        tf.iterator.map { case (t, f) =>
          val tfw = 1.0 + math.log(f)
          val w   = tfw * idf.getOrElse(t, 0.0)
          t -> w
        }.toMap
      }

    val docNorms =
      docWeights.map { wmap =>
        math.sqrt(wmap.values.foldLeft(0.0)((acc, w) => acc + w * w))
      }

    val qTf = qTerms.groupBy(identity).view.mapValues(_.size.toDouble).toMap
    val qWeights =
      qTf.iterator.map { case (t, f) =>
        val tfw  = 1.0 + math.log(f)
        val idfw = idf.getOrElse(t, 0.0)
        t -> (tfw * idfw)
      }.toMap

    val qNonZero = qWeights.filter(_._2 > 0.0)
    if (qNonZero.isEmpty) ZIO.fail(ResourceNotFound(query))

    val scored =
      docs.indices.iterator
        .map { i =>
          val wdoc = docWeights(i)
          var dot  = 0.0
          qNonZero.foreach { case (t, qw) =>
            val dw = wdoc.getOrElse(t, 0.0)
            if (dw != 0.0) dot += dw * qw
          }
          val norm  = docNorms(i)
          val score = if (norm == 0.0) 0.0 else dot / norm
          i -> score
        }
        .filter(_._2 > 0.0)
        .toVector
        .sortBy { case (_, s) => -s }

    val result =
      scored.map { case (i, _) => docs(i) }.toList

    if (result.isEmpty) ZIO.fail(ResourceNotFound(query))
    else ZIO.succeed(result)
  }
}

object SearchRepoLive {
  val layer = ZLayer.fromFunction(SearchRepoLive.apply _)
}