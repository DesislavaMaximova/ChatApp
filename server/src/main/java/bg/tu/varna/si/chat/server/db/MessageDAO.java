package bg.tu.varna.si.chat.server.db;

import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import bg.tu.varna.si.chat.model.request.MessageRequest;
import bg.tu.varna.si.chat.server.db.entity.MessageEntity;

public class MessageDAO {

	private static MessageDAO INSTANCE_HOLDER;

	private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

	private MessageDAO() {

	}

	public static MessageDAO getInstance() {
		if (INSTANCE_HOLDER == null) {
			INSTANCE_HOLDER = new MessageDAO();
		}

		return INSTANCE_HOLDER;
	}


	public void storeMessage(MessageRequest message, boolean delivered) {

		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			MessageEntity messageEntity = new MessageEntity();
			messageEntity.setContent(message.getMessageContent());
			messageEntity.setRecipient(message.getRecipientName());
			messageEntity.setSender(message.getSenderName());
			messageEntity.setDelivered(delivered);
			messageEntity.setTimestamp(message.getTimeStamp());
			session.save(messageEntity);

			transaction.commit();

		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}
	}

	public Collection<MessageEntity> getAllMessages(String username) {

		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("from MessageEntity where recipientName = :recipientName  ", MessageEntity.class)
					.list();
		}
	}

	public Collection<MessageEntity> getUndeliveredMessages(String username) {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("from MessageEntity where recipientName = :recipientName and delivered = false",
					MessageEntity.class).list();
		}
	}

}
