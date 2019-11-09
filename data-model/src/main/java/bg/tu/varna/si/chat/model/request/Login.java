package bg.tu.varna.si.chat.model.request;

import bg.tu.varna.si.chat.model.Credentials;

public class Login extends Request {

	private static final long serialVersionUID = -7522568570039372235L;

	private Credentials credentials;

	public Login(Credentials credentials) {
		super(RequestType.LOGIN);
		this.setCredentials(credentials);

	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

}
