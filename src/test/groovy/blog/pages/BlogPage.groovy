package blog.pages

import geb.Page;

public class BlogPage extends Page
{
  
  static url = "mvc/blog"
  
  static content = {
    post { index -> 
      [
        title: { $(".post h3")[index].text() },
        content: {$(".post div.content")[index].text()},
        tags: { $(".post", index).find(".tags span")*.text() }
      ]
    }
  }
}
