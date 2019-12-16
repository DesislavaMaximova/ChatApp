package bg.tu.varna.si.chat.model;

import java.util.List;

import bg.tu.varna.si.chat.model.request.RecipientType;

public class Group extends Recipient {

	private static final long serialVersionUID = 1L;

	private long id;

	private String name;
	
	private List <User> users;

	public Group(String name) {
		super(RecipientType.GROUP);
		this.name = name;
		
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsersID(List<User> users) {
		this.users = users;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + "]";
	}

}
