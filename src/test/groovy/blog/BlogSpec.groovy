package blog

import geb.GebSpec;

import org.junit.Rule;

import spock.lang.Specification

import blog.config.TestConfig;
import blog.data.BlogDataSet;
import blog.pages.BlogPage;

import com.seitenbau.testing.dbunit.rule.*
import static blog.BlogDataSetRefs.*

class BlogSpec extends GebSpec
{
  
  @Rule
  public DatabaseTesterRule databaseTesterRule = new DatabaseTesterRule(TestConfig.class)
  
  BlogDataSet dataSet = new BlogDataSet()
  
  def setup() {
    browser.baseUrl = TestConfig.baseUrl
  }
  
  def listAllBlogContent() {
    given: "Some blog posts in the database"
      dataSet.tables {
        postTable.rows {
          REF          | id  | title       | content
          FIRST_POST   |  1  | "One Title" | "<p> Some Content <b>1</b> </p>"
           _           |  2  | "Two Title" | "Some Content 2"
        }
        tagTable.rows {
          REF    |  name
          OSGi   |  "OSGi"
          JAVA   |  "Java"
        }
      }
      dataSet.relations {
        FIRST_POST.hasTags(OSGi, JAVA)
      }
      databaseTesterRule.cleanInsert(dataSet)
    when: "invoke the blog"
      to BlogPage
    then: "verify that the posts from the database are shown on the blog site"
      def posts = []
      dataSet.postTable.foreach { posts.add(it) }
      for(int index= 0; index < posts.size(); index++) {
        post(index).title() == posts[index].title
        post(index).content() == posts[index].content
      }
      post(0).tags() == ["OSGi", "Java"]
      post(1).tags() == []
  }
  
}
