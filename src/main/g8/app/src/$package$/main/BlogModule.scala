package $package$.main

import io.undertow.Undertow
import io.undertow.util.StatusCodes
import org.flywaydb.core.Flyway
import ba.sake.squery.SqueryContext
import ba.sake.sharaf.*
import ba.sake.sharaf.routing.*
import ba.sake.sharaf.exceptions.ExceptionMapper
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
    val postDao = PostDao()
    val tagDao = TagDao()

    val postService = PostService(squeryContext, postDao)
    val tagService = TagService(squeryContext, tagDao)

    val controllers = Seq(
      HomeController(tagService),
      PostController(postService)
    )
    val routes = Routes.merge(controllers.map(_.routes))

    val customExceptionMapper: ExceptionMapper = { case e: RuntimeException =>
      val errorPage = ErrorPage(e.getMessage())
      Response
        .withBody(errorPage)
        .withStatus(StatusCodes.INTERNAL_SERVER_ERROR)
    }

    val httpHandler = SharafHandler(routes)
      .withExceptionMapper(customExceptionMapper.orElse(ExceptionMapper.default))
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
