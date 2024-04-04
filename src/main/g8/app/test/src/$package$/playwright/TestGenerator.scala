package $package$
package playwright

import ba.sake.sharaf.*

@main def main: Unit =

  val itItestFixture = ItTestFixture()
  itItestFixture.start()
  
  val module = itItestFixture.module
  val classpath = System.getProperty("java.class.path")

  os.proc("java", "-cp", classpath, "com.microsoft.playwright.CLI", "codegen", module.config.baseUrl)
    .call()

  itItestFixture.stop()
