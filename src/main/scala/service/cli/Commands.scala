package service.cli

import zio.cli._

object Commands {

  val getAllCommand: Command[Subcommand] = Command(
    name = "all",
    Options.none,
    Args.none
  ).map(_ => Subcommand.GetAll())

  val helpGetAllCommand: HelpDoc = HelpDoc.p(" -- Get all movies")

  val getRankedCommand: Command[Subcommand] = Command(
    name = "rank",
    Options.none,
    Args.text("genre")
  ).map(genre => Subcommand.GetRanked(genre))

  val helpGetRankedCommand: HelpDoc = HelpDoc.p(" -- Rank movies by your favorite genre")

  val searchCommand: Command[Subcommand] = Command(
    name = "search",
    Options.none,
    Args.text("query")
  ).map(query => Subcommand.Search(query))

  val helpSearch: HelpDoc = HelpDoc.p(" -- Search movies by keywords")
}
