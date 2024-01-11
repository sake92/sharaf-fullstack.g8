package $package$
package playwright

import ba.sake.sharaf.*

@main def main: Unit =

  val itItestFixture = ItTestFixture()
  val module = itItestFixture.module
  module.flyway.migrate()
  module.server.start()

  val classpath = System.getProperty("java.class.path")

  os.proc("java", "-cp", classpath, "com.microsoft.playwright.CLI", "codegen", module.config.baseUrl)
    .call()

  module.server.stop()
  itItestFixture.pgContainer.stop()
