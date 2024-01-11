package $package$.playwright
package posts

import com.microsoft.playwright.*
import com.microsoft.playwright.options.*
import com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat

class PostsTests extends PlaywrightTest {

  test("List posts") {
    withPage { page =>
      assertThat(page.getByRole(AriaRole.HEADING)).containsText("Welcome to my blog!")
      page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("here")).click()
      assertThat(page.getByRole(AriaRole.ROWGROUP)).containsText("Example post 1")
      page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Next")).click()
      page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Next")).click()
    }
  }

  test("Create post") {
    withPage { page =>
      page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add Post")).click()
      page.locator("input[name=\"title\"]").click()
      page.locator("input[name=\"title\"]").fill("New post")
      page.locator("textarea[name=\"mdContent\"]").click()
      page.locator("textarea[name=\"mdContent\"]").fill("New post content\n\nTest 123")
      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create")).click()
      assertThat(page.locator("b")).containsText("New post")
      assertThat(page.locator("body")).containsText("New post content")
    }
  }

}
