package $package$.main

import com.typesafe.config.ConfigFactory
import ba.sake.tupson.config.*

@main def main: Unit =

  val config = ConfigFactory.load().parseConfig[BlogConfig]
  val module = BlogModule(config)

  module.flyway.migrate()
  module.server.start()

  println(s"Started HTTP server at \${config.baseUrl}")
