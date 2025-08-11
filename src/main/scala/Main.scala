import service.RankingService.getAll
import service.Commands.{getAllCommand, getRanked}
import utils.Subcommand
import zio.Console.printLine
import zio.cli.HelpDoc.Span.text
import zio.cli._

object Main extends ZIOCliDefault {

  private val main: Command[Subcommand] =
    Command("run", Options.none, Args.none)
      .subcommands(getAllCommand.withHelp(HelpDoc.p(" -- Get all movies")), getRanked.withHelp(HelpDoc.p(" -- Test command")))

  val cliApp = CliApp.make(
    name = "Movie Reranker",
    version = "1.0.0",
    summary = text("Service for ranking films by genre"),
    command = main
  ) {
    case Subcommand.GetAll()    => getAll.provide(Layers.all)
    case Subcommand.GetRanked() => printLine("TEST 🎁")
  }
}
