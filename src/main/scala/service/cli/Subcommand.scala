package service.cli

trait Subcommand extends Product with Serializable

object Subcommand {
  final case class GetAll()                          extends Subcommand
  final case class GetRanked(preferredGenre: String) extends Subcommand
  final case class Search(query: String)             extends Subcommand
}
