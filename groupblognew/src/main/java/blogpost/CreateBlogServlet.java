package blogpost;

import com.google.appengine.api.datastore.DatastoreService;

import com.google.appengine.api.datastore.DatastoreServiceFactory;

import com.google.appengine.api.datastore.Entity;

import com.google.appengine.api.datastore.Key;

import com.google.appengine.api.datastore.KeyFactory;

import com.google.appengine.api.users.User;

import com.google.appengine.api.users.UserService;

import com.google.appengine.api.users.UserServiceFactory;

 

import java.io.IOException;

import java.util.Date;

 

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;



public class CreateBlogServlet extends HttpServlet {

  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

   
    String title = req.getParameter("title");
    String content = req.getParameter("description");
    String author = req.getParameter("author");
    Date date = new Date();
    Key blogKey = KeyFactory.createKey("blog", "total");
    Entity blog = new Entity("blog", blogKey);
    blog.setProperty("title", title);
    blog.setProperty("author", author);

    blog.setProperty("date", date);

    blog.setProperty("content", content);



    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    datastore.put(blog);
    resp.sendRedirect("/home.jsp");


   
  }
}