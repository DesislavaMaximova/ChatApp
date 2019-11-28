package bg.tu.varna.si.chat.model.response;

public class FileTransferResponse extends Response {

	private String message;

	private static final long serialVersionUID = -4952544425036481767L;

	public FileTransferResponse(String message) {
		super(ResponseType.FILE_TRANSFER);
		
		this.message = message;
	}

	public String getMessage() {
		return message;
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
