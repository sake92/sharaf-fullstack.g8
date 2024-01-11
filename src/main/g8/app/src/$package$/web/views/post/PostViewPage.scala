package $package$.web.views
package post

import $package$.domain.models.*
import Bundle.*, Tags.*

class PostViewPage(post: Post) extends BlogPage:

  override def pageSettings = super.pageSettings
    .withTitle(post.title)

  override def pageContent: Frag = div(
    b(post.title),
    br,
    a(href := s"/posts/\${post.id}/edit", Classes.btnClass, Classes.btnPrimary)("Edit"),
    hr(),
    post.mdContent.md
  )
