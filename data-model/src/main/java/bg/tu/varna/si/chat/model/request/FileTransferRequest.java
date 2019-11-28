package bg.tu.varna.si.chat.model.request;

public class FileTransferRequest extends Request {
	
	private String recipientName;
	
	private String senderName;
	
	private String fileName;
	
	private int size;
	

	
	private static final long serialVersionUID = 8291601432838574410L;

	public FileTransferRequest(String recipientName, String senderName, String fileName, int size) {
		super(RequestType.FILE_TRANSFER_REQUEST);
		this.recipientName = recipientName;
		this.senderName = senderName;
		this.fileName = fileName;
		this.size = size;
		
	}

	public String getRecipientName() {
		return recipientName;
	}

	public String getSenderName() {
		return senderName;
	}


	public String getFileName() {
		return fileName;
	}

	public int getSize() {
		return size;
	}
	
}
