package $package$


import scala.compiletime.uninitialized

trait IntegrationTest extends munit.FunSuite {

  protected val itFixture = new Fixture[ItTestFixture]("ItTestFixture") {

    private var itTestFixture: ItTestFixture = uninitialized

    def apply() = itTestFixture

    override def beforeAll(): Unit =
      itTestFixture = ItTestFixture()
      itTestFixture.start()

    override def afterAll(): Unit =
      itTestFixture.stop()
  }

  override def munitFixtures = List(itFixture)
}
