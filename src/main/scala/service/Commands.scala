package service

import utils.Subcommand
import zio.cli._

object Commands {

  val getAllCommand: Command[Subcommand] = Command(
    name = "all",
    Options.none,
    Args.none
  ).map(_ => Subcommand.GetAll())

  val getRanked: Command[Subcommand] = Command(
    name = "rank",
    Options.none,
    Args.none,
  ).map(_ => Subcommand.GetRanked())
}
