package bg.tu.varna.si.chat.server.handler;

import bg.tu.varna.si.chat.model.request.Request;
import bg.tu.varna.si.chat.model.request.RequestType;
import bg.tu.varna.si.chat.model.response.ErrorType;
import bg.tu.varna.si.chat.server.exception.IllegalRequestException;
import bg.tu.varna.si.chat.server.exception.UnsupportedRequestException;

public final class RequestHandlerFactory {
	
	private RequestHandlerFactory() {
		
	}
	
	public static RequestHandler buildRequestHandler(Request request) {
		
		if (request == null || request.getRequestType() == null) {
			throw new IllegalRequestException(ErrorType.ILLEGAL_REQUEST, "Undefined request or request type.");
		}
		
		RequestType requestType = request.getRequestType();
		
		switch (requestType) {
		case LOGIN_REQUEST:
			return new LoginHandler();
			
		case USER_REGISTER_REQUEST:
			return new RegisterRequestHandler();
			
		case MESSAGE_REQUEST:
			return new MessageHandler();

		default:
			throw new UnsupportedRequestException(ErrorType.UNSUPPORTED_REQUEST, "Unsupported request [" + requestType + "]");
		}
		
	}

}
