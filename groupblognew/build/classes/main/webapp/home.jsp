
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>

<%@ page import="com.google.appengine.api.users.User" %>

<%@ page import="com.google.appengine.api.users.UserService" %>

<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>

<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>

<%@ page import="com.google.appengine.api.datastore.Query" %>

<%@ page import="com.google.appengine.api.datastore.Entity" %>

<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>

<%@ page import="com.google.appengine.api.datastore.Key" %>

<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>

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
	<a href="createblog.jsp">Create Post</a>
	<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">Sign out</a>
	<%
    	} else {

	%>

	<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>

	  <%
    	}
	%>
	

	</div>
</div>

 <h1 style="font-family:Sans-serif;color:gray">Recent Posts
         </h1>
<div style="margin-left:5%;">


<%

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    Key blogKey = KeyFactory.createKey("blog", "total");

    // Run an ancestor query to ensure we see the most up-to-date

    // view of the Greetings belonging to the selected Guestbook.

    Query query = new Query("blog", blogKey).addSort("date", Query.SortDirection.DESCENDING);

    List<Entity> blog = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));

    if (blog.isEmpty()) {

        %>

        <p>no new blogs</p>

        <%

    } else {

        %>

        

        <%
        int num = 0;

        for (Entity b : blog) {
        	num = num + 1;
            pageContext.setAttribute("author",b.getProperty("author"));
            pageContext.setAttribute("title",b.getProperty("title"));
            pageContext.setAttribute("content",b.getProperty("content"));
            pageContext.setAttribute("date",b.getProperty("date"));
 
          %> 
         
         
         <div style="display:inline">
         <span style="font-style:italic;font-family:Sans-serif;color:gray;font-size:40pt;">
         
         <%=num%>

        </span>
        </div>
        <div style="display:inline;float:right;margin-right:40%;margin-top:4%;">
         <span style="width:70%;font-family:Sans-serif;color:black;font-size:10pt;margin-left:-5%;">
          Title: ${fn:escapeXml(title)} Author: ${fn:escapeXml(author)} Time: ${fn:escapeXml(date)}              
         </span>
         
         </div>
         
          <div>
         <span style="width:80%;font-family:Sans-serif; marghome.jspin-top:1%;margin-right:5%;">
         ${fn:escapeXml(content)}
         </span>
         </div>
           
     
   
            <%  

            }
    }

            %>
          
      


</div>


    <a href="allposts.jsp">More Posts</a>



  </body>

</html>