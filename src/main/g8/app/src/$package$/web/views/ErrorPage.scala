package $package$.web.views

import Bundle.*, Tags.*

class ErrorPage(message: String) extends BlogPage:

  override def pageSettings = super.pageSettings
    .withTitle("An error happened :/")

  // you shouldn't do this in a real app,
  // you shouldn't leak (all) exception stacktraces
  override def pageContent: Frag = div(
    h1("Something happened..."),
    hr,
    h2("Stacktrace:"),
    div(message)
  )
