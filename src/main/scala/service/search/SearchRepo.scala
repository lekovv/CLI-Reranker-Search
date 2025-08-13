package service.search

import exception.SearchError.ResourceNotFound
import models.Movie
import zio._
import zio.macros.accessible

@accessible
trait SearchRepo {

  def searchByKeyword(query: String): IO[ResourceNotFound, List[Movie]]
}

object SearchRepo {
  val live = SearchRepoLive.layer
}
