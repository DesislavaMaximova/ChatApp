package bg.tu.varna.si.chat.model.response;

public class ErrorResponse extends Response {
	
	private static final long serialVersionUID = 4417063198087269225L;
	
	private ErrorType errorType;
	
	private String errorMessage;

	public ErrorResponse(ErrorType errorType, String errorMessage) {
		super(ResponseType.ERROR);
		this.errorType = errorType;
		this.errorMessage = errorMessage;
	}

	public ErrorType getErrorType() {
		return errorType;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ErrorResponse [errorType=");
		builder.append(errorType);
		builder.append(", errorMessage=");
		builder.append(errorMessage);
		builder.append("]");
		return builder.toString();
	}

}
