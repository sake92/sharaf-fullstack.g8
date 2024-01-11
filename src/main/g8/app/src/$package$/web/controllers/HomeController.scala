package $package$.web.controllers

import ba.sake.sharaf.*, routing.*
import $package$.domain.services.*
import $package$.web.views.*

class HomeController(tagService: TagService) extends Controller:

  override def routes = Routes:
    case GET() -> Path() =>
      val tags = tagService.findAll()
      Response.withBody(HomePage(tags))
