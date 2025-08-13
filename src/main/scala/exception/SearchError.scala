package exception

sealed trait SearchError extends Product with Serializable

object SearchError {
  case class ResourceNotFound(query: String) extends SearchError {
    override def toString: String = s"No results for '$query'"
  }
}
