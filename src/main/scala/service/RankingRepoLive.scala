package service

import exception.RankingError
import models.Movie
import zio.{IO, UIO, ZIO, ZLayer}

final case class RankingRepoLive(db: MovieData) extends RankingRepo {
  override def getAll: UIO[List[Movie]] = ZIO.succeed(db.movies)

  override def getRanked(preferredGenre: String): IO[RankingError, List[Movie]] = {
    ???
  }
}

object RankingRepoLive {
  val layer = ZLayer.fromFunction(RankingRepoLive.apply _)
}
