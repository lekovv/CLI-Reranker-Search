import service.{MovieData, RankingRepo}

object Layers {
  val all = MovieData.live >+> RankingRepo.live
}
