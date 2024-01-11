package $package$.web.controllers

import ba.sake.sharaf.routing.Routes

trait Controller:
  def routes: Routes
