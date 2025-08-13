package utils

import models.Movie
import zio.Console.printLine
import zio._

import java.io.IOException

object PrintService {

  def printMovies(movies: List[Movie]): ZIO[Any, IOException, Unit] =
    printLine {
      val body = movies.zipWithIndex
        .map { case (m, i) =>
          val title = sanitize(m.title)
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
}
