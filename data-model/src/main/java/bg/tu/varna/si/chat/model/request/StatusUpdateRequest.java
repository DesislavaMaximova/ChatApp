package bg.tu.varna.si.chat.model.request;

import bg.tu.varna.si.chat.model.User;

public class StatusUpdateRequest extends Request {

	private static final long serialVersionUID = 1L;

	private User user;

	public StatusUpdateRequest(User user) {
		super(RequestType.STATUS_UPDATE_REQUEST);

		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StatusUpdateRequest [user=");
		builder.append(user);
		builder.append("]");
		return builder.toString();
	}

}
