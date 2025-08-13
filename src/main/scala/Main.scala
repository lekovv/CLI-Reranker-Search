import service.cli.Commands._
import service.cli.Subcommand
import service.reranker.RankingService._
import service.search.SearchService._
import zio.cli.HelpDoc.Span.text
import zio.cli._

object Main extends ZIOCliDefault {

  private val main: Command[Subcommand] =
    Command("run", Options.none, Args.none)
      .subcommands(
        getAllCommand.withHelp(helpGetAllCommand),
        getRankedCommand.withHelp(helpGetRankedCommand),
        searchCommand.withHelp(helpSearch)
      )

  val cliApp = CliApp.make(
    name = "CLI-Reranker + Search",
    version = "1.0.0",
    summary = text("Service for ranking films by genre and searching by keywords"),
    command = main
  ) {
    case Subcommand.GetAll()         => getAll.provide(Layers.all)
    case Subcommand.GetRanked(genre) => getRanked(genre).provide(Layers.all)
    case Subcommand.Search(query)    => searchByKeyword(query).provide(Layers.all)
  }
}
