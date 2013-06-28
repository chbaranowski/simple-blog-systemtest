/*
 * Geb configuration
 * See: http://www.gebish.org/manual/current/configuration.html
 */

import org.openqa.selenium.firefox.*
import org.openqa.selenium.htmlunit.*
 
waiting { timeout = 10 }

driver = {
  new FirefoxDriver()
}

environments {
  firefox {
    driver = { new FirefoxDriver() }
  }
  htmlunit {
    driver = { new HtmlUnitDriver() }
  }
}
