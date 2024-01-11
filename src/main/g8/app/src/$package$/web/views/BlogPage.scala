package $package$.web.views

import Bundle.*, Tags.*

trait BlogPage extends Page:

  override def siteSettings =
    super.siteSettings.withName("My Blog")

  override def styleURLs: List[String] = super.styleURLs ++ List(
    "/styles/main.css"
  )

  override def bodyContent: Frag = frag(
    Navbar.nav(
      brandUrl = "/",
      brandName = Some("My Blog"),
      left = Seq(
        Form.form(action := "/posts", method := "GET")(
          input(name := "q", tpe := "search", cls := "form-control", placeholder := "Search posts")
        ),
        Navbar.link("/posts/new", "Add Post")
      )
    ),
    div(cls := "container")(
      pageContent
    )
  )
