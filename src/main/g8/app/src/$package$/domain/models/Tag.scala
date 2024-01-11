package $package$.domain.models

import java.util.UUID
import io.scalaland.chimney.dsl.*
import $package$.db.models.*

// TODO ne treba uuid ???
case class Tag(
    id: UUID,
    name: String
):
  def toRow: TagRow = this.transformInto[TagRow]

object Tag:
  def fromRow(row: TagRow): Tag = row.transformInto[Tag]
