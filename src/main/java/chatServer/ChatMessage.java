package chatServer;

import java.util.Date;

public class ChatMessage {
	
	private String message;
	private User fromUser;
	private Date messageTime;
	
	public ChatMessage(String message, User fromUser, Date messageTime) {
		super();
		this.message = message;
		this.fromUser = fromUser;
		this.messageTime = messageTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}

	public Date getMessageTime() {
		return messageTime;
	}

	public void setMessageTime(Date messageTime) {
		this.messageTime = messageTime;
	}
	
}
