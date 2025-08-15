package utils

import models.Movie
import zio.Console.printLine
import zio._

import java.io.IOException
import scala.annotation.tailrec

object Common {

  /** Common methods 👇🏻
    */
  def printMovies(movies: List[Movie]): ZIO[Any, IOException, Unit] =
    printLine {
      val body = movies.zipWithIndex
        .map { case (m, i) =>
          val title    = sanitize(m.title)
          val mkGenres = m.genres.mkString(", ")
          s"${i + 1}. $title — [$mkGenres]"
        }
        .mkString("\n")

      s"Movies (${movies.size}):\n$body"
    }

  private def sanitize(s: String): String =
    s.iterator
      .map { ch =>
        if (Character.isISOControl(ch) && ch != ' ' && ch != '\t') ' ' else ch
      }
      .mkString
      .trim

  def normalize(s: String): String = s.trim.toLowerCase

  /** Methods for executing the ranking command 👇🏻
    */

  private val lambda = 0.8

  private def rel(genres: Set[String], pref: String): Double =
    if (genres.contains(pref)) 1.0 else 0.0

  private def jaccard(a: Set[String], b: Set[String]): Double =
    if (a.isEmpty && b.isEmpty) 0.0
    else {
      val inter = a.intersect(b).size.toDouble
      val uni   = a.union(b).size.toDouble
      inter / uni
    }

  @tailrec
  def loop(
      preferredGenre: String,
      selected: Vector[(Movie, Int, Set[String])],
      remaining: Vector[(Movie, Int, Set[String])]
  ): Vector[(Movie, Int, Set[String])] = {

    if (remaining.isEmpty) selected
    else {
      val scored =
        remaining.map { case triple @ (_, idx, gs) =>
          val maxSim =
            if (selected.isEmpty) 0.0
            else selected.iterator.map(s => jaccard(gs, s._3)).max
          val score = lambda * rel(gs, preferredGenre) - (1.0 - lambda) * maxSim
          ((score, -idx), triple)
        }

      val best = scored.maxBy(_._1)._2
      loop(preferredGenre, selected :+ best, remaining.filterNot(_ == best))
    }
  }

  /** Methods for executing the search command 👇🏻
    */

  private val stop: Set[String] =
    Set(
      "a",
      "an",
      "the",
      "and",
      "or",
      "but",
      "if",
      "then",
      "else",
      "when",
      "while",
      "of",
      "in",
      "on",
      "at",
      "to",
      "for",
      "from",
      "by",
      "with",
      "about",
      "as",
      "into",
      "is",
      "am",
      "are",
      "was",
      "were",
      "be",
      "been",
      "being",
      "do",
      "does",
      "did",
      "doing",
      "this",
      "that",
      "these",
      "those",
      "it",
      "its",
      "he",
      "she",
      "they",
      "them",
      "his",
      "her",
      "their",
      "we",
      "us",
      "you",
      "your",
      "i",
      "me",
      "my",
      "ours",
      "yours"
    )

  def tokenize(s: String): Vector[String] = {
    val tokenRe = """[\p{L}\p{Nd}]+""".r

    tokenRe
      .findAllIn(normalize(s))
      .toVector
      .filter(t => t.nonEmpty && !stop.contains(t))
  }

  def termsFor(m: Movie): Vector[String] = {
    val titleTerms = tokenize(m.title)
    val descTerms  = tokenize(m.description)
    titleTerms ++ titleTerms ++ descTerms
  }
}
