package bg.tu.varna.si.chat.model.response;

public class FileTransferResponse extends Response {

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FileTransferResponse [message=");
		builder.append(message);
		builder.append("]");
		
		return builder.toString();
	}

}
