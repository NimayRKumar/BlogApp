//nimay driving

package blogpost;

import java.util.List;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import javax.mail.*;
import javax.mail.internet.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
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
	Query query = new Query("subscriber", subkey);
	
	List<Entity> sub =  datastore.prepare(query).asList(null);
	
	
	
	Message msg = new MimeMessage(session);
	msg.setFrom(new InternetAddress("raokumar512@gmail.com", "Our Blog"));
	
	
	for(Entity s : sub) {
		
		String address = (String) s.getProperty("email");
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
				
	}
	
	msg.addRecipient(Message.RecipientType.TO, new InternetAddress("kumar.nimay@yahoo.com", "Nimay Kumar"));
	msg.setSubject("Check out these new blogs!");
	msg.setText("blogs");
	
	Transport.send(msg);
	
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
}

