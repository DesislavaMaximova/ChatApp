package bg.tu.varna.si.chat.model.response;

public class AcknowledgeResponse extends Response {

	private static final long serialVersionUID = 2489349005650038675L;
	
	private String message;

	public AcknowledgeResponse(String message) {
		super(ResponseType.ACKNOWLEGE);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AcknowledgeResponse [message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}

}
