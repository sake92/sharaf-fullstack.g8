package $package$.web.views

import Bundle.*, Tags.*
import $package$.domain.models.Tag

class HomePage(val tags: Seq[Tag]) extends BlogPage:

  override def pageSettings = super.pageSettings
    .withTitle("Welcome!")

  private val tagsMd = tags
    .map { tag =>
      s"- [\${tag.name}](/posts?tag=\${tag.name})"
    }
    .mkString("\n")

  override def pageContent: Frag = div(
    h1("Welcome to my blog!"),
    s"""|
        |Click [here](/posts) to see all posts.
        |
        |Posts by tag:
        |\${tagsMd}
        |""".stripMargin.md
  )
