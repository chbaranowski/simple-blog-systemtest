package blog.config

import com.seitenbau.testing.config.StoredProperty;
import com.seitenbau.testing.config.TestConfiguration;
import com.seitenbau.testing.dbunit.TestConfigDatabase

class TestConfig implements TestConfigDatabase
{
  
  static { TestConfiguration.load(TestConfig) }
  
  @StoredProperty(key = "web.host")
  static String webHost
  
  @StoredProperty(key = "web.port")
  static Integer webPort 
  
  @StoredProperty(key = "web.contextRoot")
  static String webContextRoot
  
  @StoredProperty(key = "web.protocol")
  static String webProtocol
  
  @StoredProperty(key = '${web.protocol}://${web.host}:${web.port}/${web.contextRoot}/')
  static String baseUrl
  
}
