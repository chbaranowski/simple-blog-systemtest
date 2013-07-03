package blog.pages

import geb.Page;

class PostPage extends Page {
    
    static content = {
        postTitle { $('form').title() }
        postContent { $('form').content() }
        tags { $('form').names() }
        savePostButton(to: PostListPage) { $('form').save() }
    }
    
}
