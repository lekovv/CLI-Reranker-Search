package service.search

import utils.Common.printMovies
import zio.Console.printLine
import zio.ZIO

import java.io.IOException

object SearchService {

  def searchByKeyword(query: String): ZIO[SearchRepo, IOException, Unit] =
    for {
      service <- ZIO.service[SearchRepo]
      _ <- service
        .searchByKeyword(query)
        .foldZIO(
          err => printLine(err.toString),
          movies => printMovies(movies)
        )
    } yield ()
}
