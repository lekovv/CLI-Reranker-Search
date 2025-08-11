package models

case class Movie(title: String, genres: List[String]) {
  override def toString: String = s"- $title [${genres.mkString(", ")}]"
}
