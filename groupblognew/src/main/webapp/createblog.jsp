<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>

<%@ page import="com.google.appengine.api.users.User" %>

<%@ page import="com.google.appengine.api.users.UserService" %>

<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

 

<html>
<head><link type="text/css" rel="stylesheet" href="/stylesheets/main.css" /></head>
  <body>
<div class = "header">
	<div class = "blogname">
     <span>Art Blog</span>
     <hr noshade>
     <div class = "description">
     <p>Everybody is an artist. Create and share the artwork you like to inspire other visual communicators</p>
     </div>
     
	</div>
	
	
	<div class = "login">
	<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();

    if (user != null) {
      
      pageContext.setAttribute("user", user);
	%>
	<a href="home.jsp">Back</a>

	<%
    	} else {

	%>

	<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>

	  <%
    	}
	%>
	</div>
</div>

<div class="newpost">
  <h3>
    Create a Blog Post
  </h3>

  <form method="POST" action="/create">

    <div>
     
      <input placeholder="Blog Title" type="text" name="title" id="title" size="40" value="${fn:escapeXml(blog.title)}" class="form-control" />
    </div>
    <div>
     
      <input placeholder="Author Name" type="text" name="author" id="author" size="40" value="${fn:escapeXml(blog.author)}" class="form-control" />
    </div>
    <div>
      <textarea placeholder="Type your contents here" name="description" id="description" rows="10" cols="50" class="form-control">${fn:escapeXml(blog.content)}</textarea>
    </div>
    <button type="submit">Save</button>
  </form>
</div>


  </body>

</html>