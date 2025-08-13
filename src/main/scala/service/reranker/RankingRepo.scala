package service.reranker

import exception.RankingError.GenreNotFound
import models.Movie
import zio.macros.accessible
import zio.{IO, UIO}

@accessible
trait RankingRepo {
  def getAll: UIO[List[Movie]]
  def getRanked(preferredGenre: String): IO[GenreNotFound, List[Movie]]
}

object RankingRepo {
  val live = RankingRepoLive.layer
}