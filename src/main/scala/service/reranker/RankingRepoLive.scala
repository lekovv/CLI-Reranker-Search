package service.reranker

import db.MovieData
import exception.RankingError.GenreNotFound
import models.Movie
import zio.{IO, UIO, ZIO, ZLayer}

final case class RankingRepoLive(db: MovieData) extends RankingRepo {
  override def getAll: UIO[List[Movie]] = ZIO.succeed(db.movies)

  override def getRanked(preferredGenre: String): IO[GenreNotFound, List[Movie]] = {
    ???
  }
}

object RankingRepoLive {
  val layer = ZLayer.fromFunction(RankingRepoLive.apply _)
}
