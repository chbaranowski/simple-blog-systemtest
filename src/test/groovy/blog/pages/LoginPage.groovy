package blog.pages

import geb.Page;

class LoginPage extends Page{
    
    static url = "mvc/auth/login"
    
    static content = {
        user { $('form').user() }
        passsword { $('form').pwd() }
        loginButton(to: PostListPage) { $('form').login() }
    }
    
}
