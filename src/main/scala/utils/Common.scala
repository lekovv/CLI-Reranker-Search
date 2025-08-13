package utils

import models.Movie
import zio.Console.printLine
import zio._

import java.io.IOException
import scala.annotation.tailrec

object Common {

  def printMovies(movies: List[Movie]): ZIO[Any, IOException, Unit] =
    printLine {
      val body = movies.zipWithIndex
        .map { case (m, i) =>
          val title    = sanitize(m.title)
          val mkGenres = m.genres.mkString(", ")
          s"${i + 1}. $title — [$mkGenres]"
        }
        .mkString("\n")

      s"Movies (${movies.size}):\n$body"
    }

  private def sanitize(s: String): String =
    s.iterator
      .map { ch =>
        if (Character.isISOControl(ch) && ch != ' ' && ch != '\t') ' ' else ch
      }
      .mkString
      .trim

  def normalize(s: String): String = s.trim.toLowerCase

  private val lambda = 0.8

  private def rel(genres: Set[String], pref: String): Double =
    if (genres.contains(pref)) 1.0 else 0.0

  private def jaccard(a: Set[String], b: Set[String]): Double =
    if (a.isEmpty && b.isEmpty) 0.0
    else {
      val inter = a.intersect(b).size.toDouble
      val uni   = a.union(b).size.toDouble
      inter / uni
    }

  @tailrec
  def loop(
      preferredGenre: String,
      selected: Vector[(Movie, Int, Set[String])],
      remaining: Vector[(Movie, Int, Set[String])]
  ): Vector[(Movie, Int, Set[String])] = {

    if (remaining.isEmpty) selected
    else {
      val scored: Vector[((Double, Int), (Movie, Int, Set[String]))] =
        remaining.map { case triple @ (_, idx, gs) =>
          val maxSim =
            if (selected.isEmpty) 0.0
            else selected.iterator.map(s => jaccard(gs, s._3)).max
          val score = lambda * rel(gs, preferredGenre) - (1.0 - lambda) * maxSim
          ((score, -idx), triple)
        }

      val best = scored.maxBy(_._1)._2
      loop(preferredGenre, selected :+ best, remaining.filterNot(_ == best))
    }
  }
}
