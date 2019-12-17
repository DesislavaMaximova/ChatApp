package bg.tu.varna.si.chat.server.db;

import java.util.Collection;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.Transaction;

import bg.tu.varna.si.chat.server.db.entity.UserEntity;

public class UserDAO {

	private static UserDAO INSTANCE_HOLDER;
	
	private UserDAO() {
		
	}
	
	public static UserDAO getInstance() {
		if (INSTANCE_HOLDER == null) {
			INSTANCE_HOLDER = new UserDAO();
		}
		
		return INSTANCE_HOLDER;
	}

	public Collection<UserEntity> getAllUsers() {
		try (Session session = SessionManager.getSessionFactory().openSession() ) {
			return session.createQuery("from UserEntity", UserEntity.class).list();
		}
	}
	
	public UserEntity getUserEntity(String username) {
		try (Session session = SessionManager.getSessionFactory().openSession() ) {
			return session.createQuery("from UserEntity where userName = :userName", UserEntity.class)
					.setParameter("userName", username).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public void addUserEntity(UserEntity userEntity) {

		Transaction transaction = null;
		
		try (Session session = SessionManager.getSessionFactory().openSession()) {
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
