package service.reranker

import db.MovieData
import exception.RankingError.GenreNotFound
import models.Movie
import utils.Common._
import zio._

final case class RankingRepoLive(db: MovieData) extends RankingRepo {
  override def getAll: UIO[List[Movie]] = ZIO.succeed(db.movies)

  override def getRanked(preferredGenre: String): IO[GenreNotFound, List[Movie]] = {

    val genre = normalize(preferredGenre)

    val annotated: Vector[(Movie, Int, Set[String])] =
      db.movies.zipWithIndex.map { case (m, idx) =>
        val gs = m.genres.map(normalize).toSet
        (m, idx, gs)
      }.toVector

    val hasAnyPreferred = annotated.exists { case (_, _, gs) => gs.contains(genre) }

    if (!hasAnyPreferred)
      ZIO.fail(GenreNotFound(preferredGenre))
    else {
      val ranked = loop(genre, Vector.empty, annotated).map(_._1).toList
      ZIO.succeed(ranked)
    }
  }
}

object RankingRepoLive {
  val layer = ZLayer.fromFunction(RankingRepoLive.apply _)
}
