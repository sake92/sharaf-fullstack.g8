package $package$.db.daos

import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.postgres.given
import ba.sake.squery.utils.*
import $package$.db.models.*
import $package$.common.PageRequest

class PostDao(ctx: SqueryContext) {

  def insert(p: PostRow) = ctx.run {
    sql"""
      INSERT INTO blog.post(id, title, md_content)
      VALUES (\${p.id}, \${p.title}, \${p.md_content})
    """.insert()
  }

  def update(p: PostRow) = ctx.run {
    sql"""
      UPDATE blog.post
      SET title = \${p.title},
          md_content = \${p.md_content}
      WHERE id = \${p.id}
    """.update()
  }

  def findById(id: UUID): Seq[PostTagRow] = ctx.run {
    sql"""
      SELECT  p.id, p.title, p.md_content,
              t.id, t.name
      FROM blog.post p
      LEFT JOIN blog.post_tag pt ON pt.post_id = p.id
      LEFT JOIN blog.tag t ON t.id = pt.tag_id
      WHERE p.id = \${id}
    """.readRows()
  }

  def filter(pr: PageRequest, tag: String, q: String): PageRows[PostTagRow] = ctx.run {
    val likeArg = s"%\${q.trim}%"

    val cond = Seq(
      Option.when(q.trim.nonEmpty)(sql"(title ILIKE \${likeArg} OR md_content ILIKE \${likeArg})"),
      Option.when(tag.trim.nonEmpty)(sql"(t.name = \${tag})")
    ).concatenate(sql"AND", sql"true")

    val sliceQuery = sql"""
      SELECT DISTINCT p.id, p.title
      FROM blog.post p
      LEFT JOIN blog.post_tag pt ON pt.post_id = p.id
      LEFT JOIN blog.tag t ON t.id = pt.tag_id
      WHERE \${cond}
    """
    val query = sql"""
      WITH post_slice AS (
        \${sliceQuery}
        ORDER BY p.title ASC
        LIMIT \${pr.limit}
        OFFSET \${pr.offset}
      )
      SELECT  p.id, p.title, p.md_content,
              t.id, t.name
      FROM post_slice slice
      JOIN blog.post p ON p.id = slice.id
      LEFT JOIN blog.post_tag pt ON pt.post_id = p.id
      LEFT JOIN blog.tag t ON t.id = pt.tag_id
    """
    val items = query.readRows[PostTagRow]()
    val total = (sql"WITH post_slice AS (\${sliceQuery}) SELECT COUNT(*) FROM post_slice").readValue[Int]()
    PageRows(items, total)
  }
}
