package bg.tu.varna.si.chat.model;

import java.io.Serializable;

import bg.tu.varna.si.chat.model.request.RecipientType;

public abstract class Recipient implements Serializable {

	private static final long serialVersionUID = -4611611875521521169L;

	private final RecipientType recipientType;

	public Recipient(RecipientType recipientType) {
		this.recipientType = recipientType;
	}

	public RecipientType getRecipientType() {
		return recipientType;
	}

}
