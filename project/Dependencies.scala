import sbt.*

object Dependencies {

  object Version {
    val scala        = "2.13.10"
    val zio          = "2.1.15"
    val zioCLI       = "0.7.2"
  }

  object ZIO {
    lazy val core    = "dev.zio" %% "zio"         % Version.zio
    lazy val cli     = "dev.zio" %% "zio-cli"     % Version.zioCLI
  }

  lazy val globalProjectDependencies = Seq(
    ZIO.core
  )
}
