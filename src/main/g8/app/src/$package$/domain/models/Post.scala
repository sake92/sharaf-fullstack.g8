package $package$.domain.models

import java.util.UUID
import io.scalaland.chimney.dsl.*
import $package$.db.models.*

case class Post(
    id: UUID,
    title: String,
    mdContent: String,
    tags: Seq[Tag]
):
  def toRow: PostRow = this
    .into[PostRow]
    .withFieldRenamed(_.mdContent, _.md_content)
    .transform

object Post:
  def fromRow(row: PostRow, tagRows: Seq[TagRow]): Post =
    val tags = tagRows.map(Tag.fromRow)
    row
      .into[Post]
      .withFieldRenamed(_.md_content, _.mdContent)
      .withFieldConst(_.tags, tags)
      .transform