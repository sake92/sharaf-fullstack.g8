package $package$

import org.testcontainers.containers.PostgreSQLContainer
import com.typesafe.config.ConfigFactory
import ba.sake.tupson.config.*
import ba.sake.sharaf.utils.*
import $package$.main.*

trait IntegrationTest extends munit.FunSuite {

  protected val itFixture = new Fixture[BlogModule]("BlogModule") {

    private var pgContainer: PostgreSQLContainer[?] = _

    private var module: BlogModule = _

    def apply() = module

    override def beforeAll(): Unit =
      val itItestFixture = ItTestFixture()
      pgContainer = itItestFixture.pgContainer
      module = itItestFixture.module
      module.flyway.migrate()
      module.server.start()

    override def afterAll(): Unit =
      module.server.stop()
      pgContainer.stop()
  }

  override def munitFixtures = List(itFixture)
}
