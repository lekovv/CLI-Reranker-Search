package db

import models.Movie
import zio.ZLayer

case class MovieData(movies: List[Movie])

object MovieData {
  val live = ZLayer.succeed(
    MovieData(
      List(
        Movie(
          "The Revenant",
          "Left for dead on the frontier, a wounded scout fights through brutal wilderness and betrayal, driven by sheer will and a thirst for justice",
          List("thriller", "adventure")
        ),
        Movie(
          "John Wick",
          "A retired hitman is pulled back into the underworld after a devastating loss, unleashing precise, relentless vengeance against a web of criminal power",
          List("thriller", "crime")
        ),
        Movie(
          "Green Book",
          "A rough-edged bouncer and a refined pianist embark on a risky tour of the 1960s American South, discovering humor, conflict, and unexpected respect along the way",
          List("drama")
        ),
        Movie(
          "1+1",
          "A wealthy quadriplegic hires an irreverent caretaker; their clashing worlds spark candid humor, rule-breaking adventures, and a friendship that reshapes them both",
          List("drama", "comedy")
        ),
        Movie(
          "The Martian",
          "Stranded on a hostile planet, a resourceful astronaut hacks science to survive, turning scarcity into strategy while Earth races to attempt a daring rescue",
          List("adventure", "fantasy")
        )
      )
    )
  )
}
