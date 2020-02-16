<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>

<%@ page import="com.google.appengine.api.users.User" %>

<%@ page import="com.google.appengine.api.users.UserService" %>

<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<html>


	<head>

		<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
	
	</head>
	
	
  <body>
  
  
	<div class = "header">
	
		<div class = "blogname">
		
	     	<span>Art Blog</span>
	     	<hr noshade>
	     	
		     <div class = "description">
		     	<p>Subscribe to receive daily email updates of the blog!</p>
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
    Manage your Subscription
  </h3>

  <form method="get">

    <div>
      <input placeholder="Enter your email address" type="text" name="email" id="email" size="40" value="${fn:escapeXml(subscribe.email)}" class="form-control" />
    </div>
    
    <button type="submit" formaction="/subscribe">Subscribe</button>
    <button type="submit" formaction="/unsubscribe">Unsubscribe</button>
    
  </form>
</div>

</body>


</html>