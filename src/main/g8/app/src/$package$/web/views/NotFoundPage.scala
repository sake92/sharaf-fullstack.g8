package $package$.web.views

import Bundle.*, Tags.*

object NotFoundPage extends BlogPage:

  override def pageSettings = super.pageSettings
    .withTitle("Not Found")

  override def pageContent: Frag = div(
    h1("Not Found...")
  )
