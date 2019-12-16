package bg.tu.varna.si.chat.server.db.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table (name = "GROUP")
public class GroupEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String groupName;
	
	private List <UserEntity> userEntities;

	public GroupEntity(String name, List <UserEntity> userEntities){
		this.groupName = name;
		this.userEntities = userEntities ;
	}
	
	public GroupEntity() {
		
	}

	public long getId() {
		return id;
	}

	public List<UserEntity> getUserEntities() {
		return userEntities;
	}

	public void setUserEntitiesID(List<UserEntity> userEntities) {
		this.userEntities = userEntities;
	}

	public String getName() {
		return groupName;
	}

	public void setName(String name) {
		this.groupName = name;
	}
	
	

}
