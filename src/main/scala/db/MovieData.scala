package db

import models.Movie
import zio.ZLayer

case class MovieData(movies: List[Movie])

object MovieData {
  val live = ZLayer.succeed(
    MovieData(
      List(
        Movie("The Revenant", List("thriller", "adventure")),
        Movie("John Wick", List("thriller", "crime")),
        Movie("Green Book", List("drama")),
        Movie("1+1", List("drama", "comedy")),
        Movie("The Martian", List("adventure", "fantasy"))
      )
    )
  )
}
