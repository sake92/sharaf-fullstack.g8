package $package$

import scala.compiletime.uninitialized
import org.testcontainers.containers.PostgreSQLContainer
import com.typesafe.config.ConfigFactory
import ba.sake.tupson.config.*
import ba.sake.sharaf.utils.*
import $package$.main.*

class ItTestFixture {
  private var pgContainer: PostgreSQLContainer[?] = uninitialized
  var module: BlogModule = uninitialized

  def start(): Unit = {
    pgContainer = PostgreSQLContainer("postgres:14")
    pgContainer = pgContainer
      .withDatabaseName("blog")
      .withUsername("blog_test")
      .withPassword("blog_test")
    pgContainer.start()

    val dbConfig = DatabaseConfig(pgContainer.getJdbcUrl(), pgContainer.getUsername(), pgContainer.getPassword())

    val port = getFreePort()
    val config = ConfigFactory
      .load()
      .parseConfig[BlogConfig]
      .copy(port = port, baseUrl = s"http://localhost:\${port}", db = dbConfig)

    module = BlogModule(config)
    module.flyway.migrate()
    module.server.start()
  }

  def stop(): Unit =
    module.server.stop()
    pgContainer.stop()

}
