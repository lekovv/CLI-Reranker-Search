import db.MovieData
import service.reranker.RankingRepo
import service.search.SearchRepo

object Layers {
  val all = MovieData.live >+> RankingRepo.live >+> SearchRepo.live
}