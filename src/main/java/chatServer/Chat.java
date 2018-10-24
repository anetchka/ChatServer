package chatServer;

import java.util.List;

public class Chat {

	private String chatName;
	private User admin;
	private List <User> chatMembers;
	private List<String> chatMessages;
	public Chat(String chatName, User admin, List<User> chatMembers) {
		super();
		this.chatName = chatName;
		this.admin = admin;
		this.chatMembers = chatMembers;
	}
	public String getChatName() {
		return chatName;
	}
	public void setChatName(String chatName) {
		this.chatName = chatName;
	}
	public User getAdmin() {
		return admin;
	}
	public void setAdmin(User admin) {
		this.admin = admin;
	}
	public List<User> getChatMembers() {
		return chatMembers;
	}
	public void setChatMembers(List<User> chatMembers) {
		this.chatMembers = chatMembers;
	}
	public List<String> getChatMessages() {
		return chatMessages;
	}
	public void setChatMessages(List<String> chatMessages) {
		this.chatMessages = chatMessages;
	}
	
	
	
}
