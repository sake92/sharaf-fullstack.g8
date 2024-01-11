package $package$.main

import io.undertow.Undertow
import io.undertow.util.StatusCodes
import org.flywaydb.core.Flyway
import ba.sake.squery.SqueryContext
import ba.sake.sharaf.*
import ba.sake.sharaf.routing.*
import ba.sake.sharaf.handlers.ErrorMapper
import $package$.db.daos.*
import $package$.domain.services.*
import $package$.web.controllers.*
import $package$.web.views.*

class BlogModule(
    val config: BlogConfig,
    val flyway: Flyway,
    val server: Undertow
)

object BlogModule {

  def apply(config: BlogConfig): BlogModule = {
    val ds = com.zaxxer.hikari.HikariDataSource()
    ds.setJdbcUrl(config.db.jdbcUrl)
    ds.setUsername(config.db.username)
    ds.setPassword(config.db.password)

    val flyway = Flyway.configure().dataSource(ds).schemas("blog").load()

    val squeryContext = SqueryContext(ds)
    val postDao = PostDao(squeryContext)
    val tagDao = TagDao(squeryContext)

    val postService = PostService(postDao)
    val tagService = TagService(tagDao)

    val controllers = Seq(
      HomeController(tagService),
      PostController(postService)
    )
    val routes = Routes.merge(controllers.map(_.routes))

    val customErrorMapper: ErrorMapper = { case e: RuntimeException =>
      val errorPage = ErrorPage(e.getMessage())
      Response
        .withBody(errorPage)
        .withStatus(StatusCodes.INTERNAL_SERVER_ERROR)
    }

    val httpHandler = SharafHandler(routes)
      .withErrorMapper(customErrorMapper.orElse(ErrorMapper.default))
      .withNotFoundHandler { _ =>
        Response
          .withBody(NotFoundPage)
          .withStatus(StatusCodes.NOT_FOUND)
      }

    val server = Undertow
      .builder()
      .addHttpListener(config.port, "0.0.0.0", httpHandler)
      .build()

    new BlogModule(config, flyway, server)
  }

}
