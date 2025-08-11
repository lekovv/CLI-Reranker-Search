import zio.cli.HelpDoc.Span.text
import zio.cli._

object Main extends ZIOCliDefault {

  private val cliApp = CliApp.make(
    name = "Movie Reranker",
    version = "1.0.0",
    summary = text("Service for ranking films by genre"),
    command = ???
  ) {
    ???
  }

}
