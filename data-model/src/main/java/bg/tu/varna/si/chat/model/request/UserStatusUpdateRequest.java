package bg.tu.varna.si.chat.model.request;

public class UserStatusUpdateRequest extends Request {

	private static final long serialVersionUID = 7138960390291433910L;

	private String userName;
	
	private boolean online;

	public UserStatusUpdateRequest(String userName, boolean online) {
		super(RequestType.USER_STATUS_UPDATE_REQUEST);
		this.userName = userName;
		this.online = online;
	}

	public String getUserName() {
		return userName;
	}
	
	public boolean isOnline() {
		return online;
	}

}
