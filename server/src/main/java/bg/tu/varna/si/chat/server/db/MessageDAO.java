package bg.tu.varna.si.chat.server.db;

import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.Transaction;

import bg.tu.varna.si.chat.model.request.MessageRequest;
import bg.tu.varna.si.chat.server.db.entity.MessageEntity;

public class MessageDAO {

	private static MessageDAO INSTANCE_HOLDER;

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
		try (Session session = SessionManager.getSessionFactory().openSession()) {
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

		try (Session session = SessionManager.getSessionFactory().openSession()) {
			return session.createQuery("from MessageEntity where recipientName = :recipientName  ", MessageEntity.class)
					.list();
		}
	}

	public Collection<MessageEntity> getUndeliveredMessages(String username) {
		try (Session session = SessionManager.getSessionFactory().openSession()) {
			return session.createQuery("from MessageEntity where recipientName = :recipientName and delivered = false",
					MessageEntity.class).list();
		}
	}

}
