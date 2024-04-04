package $package$.domain.services

import java.util.UUID
import ba.sake.squery.SqueryContext
import ba.sake.squery.utils.*
import $package$.common.*
import $package$.domain.models.*
import $package$.db.daos.PostDao

class PostService(ctx: SqueryContext, postDao: PostDao) {

  def insert(post: Post): Post = ctx.run {
    postDao.insert(post.toRow)
    post
  }

  def update(post: Post): Unit = ctx.run {
    postDao.update(post.toRow)
  }

  def findById(id: UUID): Option[Post] = ctx.run {
    val rawRows = postDao.findById(id)
    val postRowsMap = rawRows.groupByOrderedOpt(_.p, _.t)
    postRowsMap.map { case (postRow, tagRows) =>
      Post.fromRow(postRow, tagRows)
    }.headOption
  }

  def filter(pr: PageRequest, tag: String, q: String): PageResponse[Post] = ctx.run {
    val rawPage = postDao.filter(pr, tag, q)
    val postRowsMap = rawPage.rows.groupByOrderedOpt(_.p, _.t)
    val pageItems = postRowsMap.map { case (postRow, tagRows) =>
      Post.fromRow(postRow, tagRows)
    }.toSeq
    PageResponse(pageItems, pr.number, rawPage.total)
  }

}
