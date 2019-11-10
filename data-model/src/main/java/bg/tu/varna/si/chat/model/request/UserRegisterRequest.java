package bg.tu.varna.si.chat.model.request;

public class UserRegisterRequest extends Request {

	private static final long serialVersionUID = 1207781374734361920L;

	private String userName;

	private String firstName;

	private String lastName;

	private String displayName;

	private String password;

	public UserRegisterRequest(
			String userName, 
			String firstName,
			String lastName, 
			String displayName, 
			String password) {
		super(RequestType.USER_REGISTER_REQUEST);
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.displayName = displayName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
