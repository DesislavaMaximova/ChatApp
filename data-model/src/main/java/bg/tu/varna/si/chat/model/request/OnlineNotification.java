package bg.tu.varna.si.chat.model.request;

public class OnlineNotification extends Request {

	private static final long serialVersionUID = 7138960390291433910L;

	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public OnlineNotification(String userName) {
		super(RequestType.ONLINE_NOTIFICATION);
		this.userName = userName;
	}

}
