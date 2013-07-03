package blog.pages

import geb.Page;

class PostListPage extends Page {

    static url = "mvc/blog/posts"
    
    static content = {
        newPostButton(to: PostPage) { $('#newPost') }
        postListCount { $('tbody tr')*.tag().size }
    }
}
