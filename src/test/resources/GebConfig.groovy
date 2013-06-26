/*
 * Geb configuration
 * See: http://www.gebish.org/manual/current/configuration.html
 */

 import org.openqa.selenium.firefox.*

waiting { timeout = 10 }

driver = {
  new FirefoxDriver()
}

environments {
  firefox {
    driver = { new FirefoxDriver() }
  }
}
