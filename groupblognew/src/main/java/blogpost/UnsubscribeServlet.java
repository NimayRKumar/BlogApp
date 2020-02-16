package blogpost;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


public class UnsubscribeServlet extends HttpServlet{
		
	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
		      throws IOException {
	
	
		String email = req.getParameter("email");
		
	    Key subscriberKey = KeyFactory.createKey("subscriber", email);

	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	    datastore.delete(subscriberKey);
	    resp.sendRedirect("/home.jsp");

	}
}