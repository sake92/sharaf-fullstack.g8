package $package$.domain.services

import ba.sake.squery.SqueryContext
import $package$.domain.models.*
import $package$.db.daos.TagDao

class TagService(ctx: SqueryContext, tagDao: TagDao) {

  def findAll(): Seq[Tag] = ctx.run {
    tagDao.findAll().map(Tag.fromRow)
  }
}