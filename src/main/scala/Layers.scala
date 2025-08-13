import db.MovieData
import service.RankingRepo

object Layers {
  val all = MovieData.live >+> RankingRepo.live
}
