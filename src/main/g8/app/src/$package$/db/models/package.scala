package $package$.db.models

import java.util.UUID
import ba.sake.squery.*

import ba.sake.squery.postgres.{*, given}

case class PostRow(id: UUID, title: String, md_content: String) derives SqlReadRow

case class TagRow(id: UUID, name: String) derives SqlReadRow

case class PostTagRow(p: PostRow, t: Option[TagRow]) derives SqlReadRow
