package bg.tu.varna.si.chat.model.request;

import java.io.Serializable;
import java.util.Date;

public class Request implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private final RequestType requestType;
	
	private final Date timeStamp;

	public Request(RequestType requestType) {
		this.requestType = requestType;
		this.timeStamp = new Date();
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}
	
}
