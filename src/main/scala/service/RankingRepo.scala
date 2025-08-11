package service

import exception.RankingError
import models.Movie
import zio.macros.accessible
import zio.{IO, UIO}

@accessible
trait RankingRepo {
  def getAll: UIO[List[Movie]]
  def getRanked(preferredGenre: String): IO[RankingError, List[Movie]]
}

object RankingRepo {
  val live = RankingRepoLive.layer
}