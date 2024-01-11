package $package$

import org.testcontainers.containers.PostgreSQLContainer
import com.typesafe.config.ConfigFactory
import ba.sake.tupson.config.*
import ba.sake.sharaf.utils.*
import $package$.main.*

class ItTestFixture {
  val pgContainer = locally {
    var container = PostgreSQLContainer("postgres:14")
    container = container.withDatabaseName("blog")
    container = container.withUsername("blog_test")
    container = container.withPassword("blog_test")
    container.start()
    container
  }

  private val dbConfig = DatabaseConfig(pgContainer.getJdbcUrl(), pgContainer.getUsername(), pgContainer.getPassword())

  private val port = getFreePort()
  private val config = ConfigFactory
    .load()
    .parseConfig[BlogConfig]
    .copy(port = port, baseUrl = s"http://localhost:\${port}", db = dbConfig)

  val module = BlogModule(config)
}
