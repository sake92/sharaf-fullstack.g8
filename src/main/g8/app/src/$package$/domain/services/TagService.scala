package $package$.domain.services

import $package$.domain.models.*
import $package$.db.daos.TagDao

class TagService(tagDao: TagDao) {

  def findAll(): Seq[Tag] =
    tagDao.findAll().map(Tag.fromRow)
}