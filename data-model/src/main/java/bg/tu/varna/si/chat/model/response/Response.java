package bg.tu.varna.si.chat.model.response;

import java.io.Serializable;
import java.util.Date;

public abstract class Response implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final ResponseType responseType;
	
	private final Date timeStamp;

	public Response(ResponseType responseType) {
		this.responseType = responseType;
		this.timeStamp = new Date();
	}
	
	public ResponseType getResponseType() {
		return responseType;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

}
