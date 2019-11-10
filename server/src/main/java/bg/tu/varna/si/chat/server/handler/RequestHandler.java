package bg.tu.varna.si.chat.server.handler;

import bg.tu.varna.si.chat.model.request.Request;
import bg.tu.varna.si.chat.model.response.Response;

public abstract class RequestHandler {
	
	public abstract Response handle(Request request);
	
}
