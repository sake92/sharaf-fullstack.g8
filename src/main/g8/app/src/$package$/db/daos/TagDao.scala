package $package$.db.daos

import ba.sake.squery.{*, given}
import ba.sake.squery.postgres.given
import $package$.db.models.*

class TagDao() {

  def insert(t: TagRow): DbAction[Unit] =
    sql"""
      INSERT INTO blog.tag(id, name)
      VALUES (\${t.id}, \${t.name})
    """.insert()

  def findAll(): DbAction[Seq[TagRow]] =
    sql"""
      SELECT id, name
      FROM blog.tag t
    """.readRows()

}
