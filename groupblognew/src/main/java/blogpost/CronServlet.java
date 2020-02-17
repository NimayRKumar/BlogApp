//nimay driving

package blogpost;

import java.util.List;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import javax.mail.*;
import javax.mail.internet.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;


@SuppressWarnings("serial")
public class CronServlet extends HttpServlet {
private static final Logger _logger = Logger.getLogger(CronServlet.class.getName());


public void doGet(HttpServletRequest req, HttpServletResponse resp)
throws IOException {
try {
_logger.info("Cron Job has been executed");


Properties props = new Properties();

Session session = Session.getDefaultInstance(props, null);

try {
	
	
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	Key subkey = KeyFactory.createKey("subscriber", "total");
	Key blogkey = KeyFactory.createKey("blog", "total");
	
	
	Query query = new Query("subscriber", subkey);
    Query bquery = new Query("blog", blogkey).addSort("date", Query.SortDirection.DESCENDING);
    
    List<Entity> blog = datastore.prepare(bquery).asList(FetchOptions.Builder.withLimit(50));
    
    String emailBody = "";
    
    for(Entity b : blog) {
    	
    	if(withinDay(b)) {
    		
    		String author = (String) b.getProperty("author");
    		String title = (String) b.getProperty("title");
    		emailBody += author;
    		emailBody += ": ";
    		emailBody += title + "\n";
    	}
    }
    
    
	Date blogUpdated = (Date) blog.get(0).getProperty("date");
	
	long milSecPerDay = 60 * 60 * 24 * 1000;
	Date now = new Date();
    
    
    if(emailBody == "") {
    	return;
    }
    
    
    
    List<Entity> sub = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(50));

    if (sub.isEmpty()) {
    	
    	
    }
	
	for(Entity s : sub) {
		
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("raokumar512@gmail.com", "Our Blog"));
		
		String address = (String) s.getProperty("email");
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
		
		msg.setSubject("Check out these new blogs!");
		msg.setText(emailBody);
		
		Transport.send(msg);
		
	}
		//msg.addRecipient(Message.RecipientType.TO, new InternetAddress("raokumar512@gmail.com", "Nimay Kumar"));
		
	
} catch (AddressException e) {
	
	
} catch (MessagingException e) {
	
	
} catch (UnsupportedEncodingException e) {
	
}




}
catch (Exception ex) {
//Log any exceptions in your Cron Job
}
}
@Override
public void doPost(HttpServletRequest req, HttpServletResponse resp)
throws ServletException, IOException {
doGet(req, resp);
}


static long msPerDay = 60 * 60 * 24 * 1000;

public boolean withinDay(Entity b) {
	
	Date then = (Date) b.getProperty("date");
	Date now = new Date();
	
	if(now.getTime() - then.getTime() <= msPerDay) {
		return true;
	}
	
	return false;
}



}

