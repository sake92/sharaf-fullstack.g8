package $package$.web.models

import ba.sake.validson.Validator
import ba.sake.querson.QueryStringRW
import $package$.common.PageRequest

case class PageQP(number: Int = 0) derives QueryStringRW:
  def toPageRequest = PageRequest(number)

object PageQP:

  val first = PageQP(0)

  given Validator[PageQP] = Validator
    .derived[PageQP]
    .positive(_.number)
