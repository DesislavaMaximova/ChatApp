package bg.tu.varna.si.chat.model.request;

public class Message extends Request {

	private static final long serialVersionUID = 4141594803871656267L;
	private String messageContent;
	private String recieverName;
	private String senderName;

	public Message(String messageContent, String recieverName, String senderName) {
		super(RequestType.MESSAGE);
		this.messageContent = messageContent;
		this.recieverName = recieverName;
		this.senderName = senderName;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public String getRecieverName() {
		return recieverName;
	}

	public String getSenderName() {
		return senderName;
	}

}
