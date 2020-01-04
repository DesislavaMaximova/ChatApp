package bg.tu.varna.si.chat.model.request;

public class FileContentRequest extends Request {

	private static final long serialVersionUID = 1L;

	private String fileName;

	private byte[] content;

	private String recipient;

	private String sender;

	public FileContentRequest(String fileName, String recipient, String sender, byte[] content) {
		super(RequestType.FILE_CONTENT_REQUEST);

		this.fileName = fileName;
		this.recipient = recipient;
		this.sender = sender;
		this.content = content;

	}

	public String getFileName() {
		return fileName;
	}

	public byte[] getContent() {
		return content;
	}

	public String getRecipient() {
		return recipient;
	}

	public String getSender() {
		return sender;
	}

}
