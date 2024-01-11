package $package$.playwright

import scala.util.Using
import com.microsoft.playwright.*
import $package$.IntegrationTest

trait PlaywrightTest extends IntegrationTest {

  def withPage[T](testFun: Page => T) = {
    val fixture = itFixture()
    val baseUrl = fixture.config.baseUrl

    Using.resource(Playwright.create()) { playwright =>
      val browser = playwright
        .chromium()
        .launch(new BrowserType.LaunchOptions().setHeadless(true))
      val context = browser.newContext()
      val page = context.newPage()
      page.navigate(baseUrl)

      testFun(page)
    }
  }
}
