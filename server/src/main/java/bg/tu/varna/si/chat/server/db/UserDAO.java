package bg.tu.varna.si.chat.server.db;

import java.util.Collection;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import bg.tu.varna.si.chat.server.db.entity.UserEntity;

public class UserDAO {

	private static UserDAO INSTANCE_HOLDER;
	
	private SessionFactory sessionFactory =  new Configuration().configure().buildSessionFactory();
	
	private UserDAO() {
		
	}
	
	public static UserDAO getInstance() {
		if (INSTANCE_HOLDER == null) {
			INSTANCE_HOLDER = new UserDAO();
		}
		
		return INSTANCE_HOLDER;
	}

	public Collection<UserEntity> getAllUsers() {
		try (Session session = sessionFactory.openSession() ) {
			return session.createQuery("from UserEntity", UserEntity.class).list();
		}
	}
	
	public UserEntity getUserEntity(String username) {
		try (Session session = sessionFactory.openSession() ) {
			return session.createQuery("from UserEntity where userName = :userName", UserEntity.class)
					.setParameter("userName", username).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public void addUserEntity(UserEntity userEntity) {

		Transaction transaction = null;
		
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.save(userEntity);
			
			transaction.commit();
			
			System.out.println("Saved user: " + userEntity.getUserName());
			
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} 
		
	}

}
