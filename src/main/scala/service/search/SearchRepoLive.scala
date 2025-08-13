package service.search

import db.MovieData
import exception.SearchError.ResourceNotFound
import models.Movie
import zio.{IO, ZLayer}

final case class SearchRepoLive(db: MovieData) extends SearchRepo {

  override def searchByKeyword(query: String): IO[ResourceNotFound, List[Movie]] = ???
}

object SearchRepoLive {
  val layer = ZLayer.fromFunction(SearchRepoLive.apply _)
}