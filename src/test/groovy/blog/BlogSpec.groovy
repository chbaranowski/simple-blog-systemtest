package blog

import geb.GebSpec;

import org.junit.Rule;

import spock.lang.Specification

import blog.config.TestConfig;
import blog.data.BlogDataSet;
import blog.db.PostTable.RowBuilder_Post;
import blog.pages.BlogPage;
import blog.pages.LoginPage;
import blog.pages.PostListPage;

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
  
  def "show all Blog content"() {
    given: "Some blog posts in the database"
      dataSet.tables {
        postTable.rows {
          REF          | id  | title       | content
          FIRST_POST   |  1  | "One Title" | "Some Content"
          SECOND_POST  |  2  | "Two Title" | "Some Content 2"
        }
        tagTable.rows {
          REF    |  name
          OSGi   |  "OSGi"
          JAVA   |  "Java"
          _      |  "CVS"
        }
      }
      dataSet.relations {
        FIRST_POST.hasTags(OSGi, JAVA)
        SECOND_POST.hasTags(JAVA)
      }
      databaseTesterRule.cleanInsert(dataSet)
    when: "invoke the blog"
      to BlogPage
    then: "verify that the posts from the database are shown on the blog site"
      dataSet.postTable.findWhere.rows.eachWithIndex { dbPost, index ->
          assert post(index).title()     == dbPost.title
          assert post(index).content()   == dbPost.content
          dataSet.postTagTable.findWhere.rows
              .findAll { it.postsId == dbPost.id } 
              .each { assert it.tagsName in post(index).tags() }
      }
  }
  
  def "create new Blog post"() {
      given: "Empty blog"
          databaseTesterRule.cleanInsert(dataSet)
      and: "Session with admin user"
          to LoginPage
          user       = 'admin'
          passsword  = 'admin'
          loginButton.click()
      when: "Create a new Blog post"
          to PostListPage
          newPostButton.click()   
          postTitle   = 'Continuous Delivery (CD)'
          postContent << """
                       <ul>
                          <li> Continuous Delivery has been implemented many different places
                          <li> Jez Humble. Continuous delivery : reliable software releases through build, test, and deployment automation
                       </ul>
                       """
          tags       = 'CD, Java'
          savePostButton.click()
      then: "Post count should be 1" 
          postListCount == 1
  }
  
  def "create two new Blog post"() {
      given: "Empty blog"
          databaseTesterRule.cleanInsert(dataSet)
      and: "Session with admin user"
          to LoginPage
          user       = 'admin'
          passsword  = 'admin'
          loginButton.click()
      when: "Create first Blog post"
          to PostListPage
          newPostButton.click()
          postTitle   = 'Continuous Delivery (CD) I'
          postContent << """
                       <ul>
                          <li> Continuous Delivery has been implemented many different places
                          <li> Jez Humble. Continuous delivery : reliable software releases through build, test, and deployment automation
                       </ul>
                       """
          tags       = 'CD, Java'
          savePostButton.click()
       and: "Create second Blog post"
          newPostButton.click()
          postTitle   = 'Continuous Delivery (CD) II'
          postContent << """
                       <ul>
                          <li> Continuous Delivery has been implemented many different places
                          <li> Jez Humble. Continuous delivery : reliable software releases through build, test, and deployment automation
                       </ul>
                       """
          tags       = 'CD, Java'
          savePostButton.click()
      then: "Post count should be 2" 
          postListCount == 2
    }
  
}
