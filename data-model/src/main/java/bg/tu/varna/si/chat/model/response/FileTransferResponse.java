package bg.tu.varna.si.chat.model.response;

public class FileTransferResponse extends Response {

	private String fileRecipient;
	
	private String fileSender;
	
	private String fileName;
	
	private String message;

	private boolean approved;

	private static final long serialVersionUID = -4952544425036481767L;

	public FileTransferResponse() {
		super(ResponseType.FILE_TRANSFER);

	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public boolean getApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public String getFileRecipient() {
		return fileRecipient;
	}

	public void setFileRecipient(String fileRecipient) {
		this.fileRecipient = fileRecipient;
	}

	public String getFileSender() {
		return fileSender;
	}

	public void setFileSender(String fileSender) {
		this.fileSender = fileSender;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FileTransferResponse [fileRecipient=");
		builder.append(fileRecipient);
		builder.append(", fileSender=");
		builder.append(fileSender);
		builder.append(", fileName=");
		builder.append(fileName);
		builder.append(", message=");
		builder.append(message);
		builder.append(", approved=");
		builder.append(approved);
		builder.append("]");
		return builder.toString();
	}

	

}
