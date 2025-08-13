package service

import utils.Subcommand
import zio.cli._

object Commands {

  val getAllCommand: Command[Subcommand] = Command(
    name = "all",
    Options.none,
    Args.none
  ).map(_ => Subcommand.GetAll())

  private val genreArg = Args.text("genre")

  val getRankedCommand: Command[Subcommand] = Command(
    name = "rank",
    Options.none,
    genreArg,
  ).map(genre => Subcommand.GetRanked(genre))
}