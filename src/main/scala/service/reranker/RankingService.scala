package service.reranker

import utils.Common.printMovies
import zio.Console.printLine
import zio.ZIO

import java.io.IOException

object RankingService {

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
