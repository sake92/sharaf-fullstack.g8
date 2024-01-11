package $package$.web.models

import io.scalaland.chimney.dsl.*
import ba.sake.formson.FormDataRW
import ba.sake.validson.Validator
import $package$.domain.models.*
import java.util.UUID

case class UpsertPostForm(
    title: String,
    mdContent: String
) derives FormDataRW:
  def toNewPost: Post = this
    .into[Post]
    .withFieldConst(_.id, UUID.randomUUID())
    .withFieldConst(_.tags, Seq.empty)
    .transform

object UpsertPostForm:

  val empty = UpsertPostForm("", "")

  given Validator[UpsertPostForm] = Validator
    .derived[UpsertPostForm]
    .notBlank(_.title)

  def fromPost(p: Post): UpsertPostForm = p.transformInto[UpsertPostForm]
