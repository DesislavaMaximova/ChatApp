package bg.tu.varna.si.chat.server.exception;

import bg.tu.varna.si.chat.model.response.ErrorType;

public class UnsupportedRequestException extends UnsupportedOperationException {

	private static final long serialVersionUID = 1L;
	
	private final ErrorType errorType;
	
	private final String errorMessage;

	public UnsupportedRequestException(ErrorType errorType, String errorMessage) {
		super();
		this.errorType = errorType;
		this.errorMessage = errorMessage;
	}

	public ErrorType getErrorType() {
		return errorType;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
