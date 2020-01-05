package bg.tu.varna.si.chat.model.request;

import java.util.Date;

public class MessageRequest extends Request {

	private static final long serialVersionUID = 4141594803871656267L;
	
	private String messageContent;
	
	private String recipientName;
	
	private String senderName;
	
	private long groupID;

	public MessageRequest(String messageContent, String recipientName, String senderName) {
		super(RequestType.MESSAGE_REQUEST);
		this.messageContent = messageContent;
		this.recipientName = recipientName;
		this.senderName = senderName;
	}
	
	public MessageRequest(String messageContent, String recipientName, String senderName, Date timeStamp) {
		super(RequestType.MESSAGE_REQUEST);
		this.messageContent = messageContent;
		this.recipientName = recipientName;
		this.senderName = senderName;
		this.timeStamp = timeStamp;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public String getRecipientName() {
		return recipientName;
	}

	public String getSenderName() {
		return senderName;
	}
	
	public long getGroupID() {
		return groupID;
	}

	public void setGroupID(long groupID) {
		this.groupID = groupID;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Message [messageContent=");
		builder.append(messageContent);
		builder.append(", recipientName=");
		builder.append(recipientName);
		builder.append(", senderName=");
		builder.append(senderName);
		builder.append("]");
		return builder.toString();
	}

}
