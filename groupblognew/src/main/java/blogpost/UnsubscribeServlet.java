package blogpost;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;


public class UnsubscribeServlet extends HttpServlet{
		
	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
		      throws IOException {
	
	
		String email = req.getParameter("email");
		
	    Key subkey = KeyFactory.createKey("subscriber", "total");

		Query query = new Query("subscriber", subkey);

	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	    List<Entity> sub = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(50));

	    for(Entity s : sub) {
	    	
	    	String address = (String) s.getProperty("email");
	    	if(address.equals(email)) {
	    		
	    		Key key = s.getKey();
	    		datastore.delete(key);
	    		
	    	}
	    	
	    }
	    resp.sendRedirect("/home.jsp");

	}
}