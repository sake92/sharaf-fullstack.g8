package $package$.db.daos

import ba.sake.squery.{*, given}
import ba.sake.squery.postgres.given
import $package$.db.models.*

class TagDao(ctx: SqueryContext) {

  def insert(t: TagRow) = ctx.run {
    sql"""
      INSERT INTO blog.tag(id, name)
      VALUES (\${t.id}, \${t.name})
    """.insert()
  }

  def findAll(): Seq[TagRow] = ctx.run {
    sql"""
      SELECT id, name
      FROM blog.tag t
    """.readRows()
  }
}
