package bg.tu.varna.si.chat.server.db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public abstract class SessionManager {
	
	private static final SessionFactory SESSION_FACTORY = new Configuration().configure().buildSessionFactory();
	
	public static SessionFactory getSessionFactory() {
		return SESSION_FACTORY;
	}
	

}
