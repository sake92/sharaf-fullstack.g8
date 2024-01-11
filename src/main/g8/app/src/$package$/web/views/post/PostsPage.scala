package $package$.web.views
package post

import ba.sake.querson.*
import $package$.common.*
import $package$.domain.models.*
import $package$.web.models.*
import Bundle.*, Tags.*

class PostsPage(qp: FindPostsQP, postsPageRes: PageResponse[Post]) extends BlogPage:

  override def pageSettings = super.pageSettings
    .withTitle("Posts")

  override def pageContent: Frag = div(
    h1("Posts"),
    helpers.bsTablePrimary(
      tr(th("Name"), th("Tags")),
      postsPageRes.items.map { owner =>
        tr(
          td(a(href := s"/posts/\${owner.id}")(owner.title)),
          td(owner.tags.map(_.name))
        )
      }
    ),
    fragments.pagination(postsPageRes, getLink)
  )

  private def getLink(pr: PageRequest): String =
    val newQP = qp.copy(p = PageQP(pr.number))
    s"/posts?\${newQP.toQueryString()}"
