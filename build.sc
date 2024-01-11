import $ivy.`io.chris-kipp::mill-giter8::0.2.7`

import io.kipp.mill.giter8.G8Module

object g8 extends G8Module {
  override def validationTargets =
    Seq("app.reformat", "app.compile", "app.test.compile")
}
