package bg.tu.varna.si.chat.server.db.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MESSAGE")
public class MessageEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
		
	private String sender;
	
	private String recipient;
	
	private String content;
	
	private Date timestamp;
	
	private boolean delivered;
	
	public MessageEntity() {
		
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isDelivered() {
		return delivered;
	}

	public void setDelivered(boolean delivered) {
		this.delivered = delivered;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MessageEntity [sender=");
		builder.append(sender);
		builder.append(", recipient=");
		builder.append(recipient);
		builder.append(", content=");
		builder.append(content);
		builder.append(", timestamp=");
		builder.append(timestamp);
		builder.append(", delivered=");
		builder.append(delivered);
		builder.append("]");
		return builder.toString();
	}

}
