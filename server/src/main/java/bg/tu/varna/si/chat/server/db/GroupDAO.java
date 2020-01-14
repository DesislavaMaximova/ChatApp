package bg.tu.varna.si.chat.server.db;

import java.util.Collection;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.Transaction;

import bg.tu.varna.si.chat.server.db.entity.GroupEntity;
import bg.tu.varna.si.chat.server.db.entity.UserEntity;

public class GroupDAO {

	private static GroupDAO INSTANCE_HOLDER;

	private GroupDAO() {

	}

	public static GroupDAO getInstance() {
		if (INSTANCE_HOLDER == null) {
			INSTANCE_HOLDER = new GroupDAO();
		}
		return INSTANCE_HOLDER;
	}

	public GroupEntity getGroupEntity(long groupID) {
		try (Session session = SessionManager.getSessionFactory().openSession()) {
			return session.createQuery("from GroupEntity where groupID = :groupID", GroupEntity.class)

					.setParameter("groupID", groupID).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public void addGroupEntity(GroupEntity groupEntity) {

		Transaction transaction = null;

		try (Session session = SessionManager.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.save(groupEntity);

			transaction.commit();

			System.out.println("Saved group: " + groupEntity.getName());

		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}

	}

	public void addUserToGroup(GroupEntity groupEntity, UserEntity userEntity) {
		Transaction transaction = null;
		try (Session session = SessionManager.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.save(groupEntity.getUserEntities().add(userEntity));

			transaction.commit();

			System.out.println("User" + userEntity.getDisplayName() + "added to group" + groupEntity.getName());
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}
	}

	public Collection<GroupEntity> getAllGroups() {
		try (Session session = SessionManager.getSessionFactory().openSession()) {
			return session.createQuery("from GroupEntity", GroupEntity.class).list();
		}
	}
}
