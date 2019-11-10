package bg.tu.varna.si.chat.server.exception;

import bg.tu.varna.si.chat.model.response.ErrorType;

public class IllegalRequestException extends IllegalArgumentException {
	
	private static final long serialVersionUID = 1L;

	private final ErrorType errorType;
	
	private final String errorMessage;

	public IllegalRequestException(ErrorType errorType, String errorMessage) {
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
