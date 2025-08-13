package service

import models.Movie
import zio.Console.printLine
import zio.ZIO

import java.io.IOException

object RankingService {

  private def printMovies(movies: List[Movie]): ZIO[Any, IOException, Unit] =
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

  def getAll: ZIO[RankingRepo, IOException, Unit] =
    for {
      service <- ZIO.service[RankingRepo]
      movies  <- service.getAll
      _       <- printMovies(movies)
    } yield ()

  def getRanked(preferredGenre: String): ZIO[RankingRepo, IOException, Unit] =
    for {
      service <- ZIO.service[RankingRepo]
      _ <- service
        .getRanked(preferredGenre)
        .foldZIO(
          err => printLine(err.toString),
          movies => printMovies(movies)
        )
    } yield ()
}
