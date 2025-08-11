package exception

sealed trait RankingError extends Product with Serializable

object RankingError {
  case class GenreNotFound(genre: String) extends RankingError {
    override def toString: String = s"Genre '$genre' not found"
  }
}
