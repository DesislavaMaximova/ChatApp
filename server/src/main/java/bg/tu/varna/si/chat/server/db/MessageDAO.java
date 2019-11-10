package bg.tu.varna.si.chat.server.db;

import java.util.LinkedList;
import java.util.List;

import bg.tu.varna.si.chat.model.request.MessageRequest;
import bg.tu.varna.si.chat.server.db.entity.MessageEntity;

public class MessageDAO {

	private List<MessageEntity> messages = new LinkedList<MessageEntity>();

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
		MessageEntity messageEntity = new MessageEntity();
		messageEntity.setContent(message.getMessageContent());
		messageEntity.setRecipient(message.getRecieverName());
		messageEntity.setSender(message.getSenderName());
		messageEntity.setDelivered(delivered);
		messageEntity.setTimestamp(message.getTimeStamp());

		messages.add(messageEntity);
	}

	public List<MessageRequest> getUndeliveredMessages(String username) {
		List<MessageRequest> undeliveredMessages = new LinkedList<MessageRequest>();

		for (MessageEntity entity : messages) {
			if (entity.getRecipient().equals(username) && !entity.isDelivered()) {
				undeliveredMessages.add(
						new MessageRequest(entity.getContent(), 
								entity.getRecipient(),
								entity.getSender()));
			}
		}

		return undeliveredMessages;
	}
	
}
