package bg.tu.varna.si.chat.model.request;

public class MessageRequest extends Request {

	private static final long serialVersionUID = 4141594803871656267L;
	
	private String messageContent;
	
	private String recipientName;
	
	private String senderName;

	public MessageRequest(String messageContent, String recipientName, String senderName) {
		super(RequestType.MESSAGE_REQUEST);
		this.messageContent = messageContent;
		this.recipientName = recipientName;
		this.senderName = senderName;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public String getRecieverName() {
		return recipientName;
	}

	public String getSenderName() {
		return senderName;
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
