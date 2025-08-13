package utils

trait Subcommand extends Product with Serializable

object Subcommand {
  final case class GetAll() extends Subcommand
  final case class GetRanked(preferredGenre: String) extends Subcommand
}
