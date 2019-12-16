package bg.tu.varna.si.chat.model.request;

public class FileTransferRequest extends Request {
	
	private String recipient;
	
	private String sender;
	
	private String fileName;
	
	private int size;
	

	
	private static final long serialVersionUID = 8291601432838574410L;

	public FileTransferRequest(String recipient, String sender, String fileName, int size) {
		super(RequestType.FILE_TRANSFER_REQUEST);
		this.recipient = recipient;
		this.sender = sender;
		this.fileName = fileName;
		this.size = size;
		
	}

	public String getRecipient() {
		return recipient;
	}

	public String getSender() {
		return sender;
	}


	public String getFileName() {
		return fileName;
	}

	public int getSize() {
		return size;
	}
	
}
