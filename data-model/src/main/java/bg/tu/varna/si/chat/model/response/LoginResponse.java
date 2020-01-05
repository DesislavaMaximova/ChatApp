package bg.tu.varna.si.chat.model.response;

import java.util.Collection;

import bg.tu.varna.si.chat.model.User;
import bg.tu.varna.si.chat.model.request.MessageRequest;

public class LoginResponse extends Response {

	private static final long serialVersionUID = 5336694067920377728L;

	private User currentUser;
	
	private Collection<User> users;
	
	private Collection <MessageRequest> undeliveredMessages;
	
	public LoginResponse(User currentUser, 
			Collection<User> users, 
			Collection<MessageRequest> undeliveredMessages) {
		super(ResponseType.LOGIN);
		this.currentUser = currentUser;
		this.users = users;
		this.undeliveredMessages = undeliveredMessages;
	}
	
	@Override
	public ResponseType getResponseType() {
		return ResponseType.LOGIN;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public Collection<User> getUsers() {
		return users;
	}

	public Collection<MessageRequest> getUndeliveredMessages() {
		return undeliveredMessages;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoginResponse [currentUser=");
		builder.append(currentUser);
		builder.append(", users=");
		builder.append(users);
		builder.append(", undeliveredMessages=");
		builder.append(undeliveredMessages);
		builder.append("]");
		return builder.toString();
	}

}
