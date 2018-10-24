package chatServer;

import java.util.List;

public class User {
	private String userName;
	private String password;
	private List<Chat> userChats;
	
	/**
	 * Constructor
	 * @param userName the name of the user
	 * @param password the password of the user
	 */
	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Chat> getUsersChat() {
		return userChats;
	}

	public void setUsersChat(List<Chat> usersChat) {
		this.userChats = usersChat;
	}
}
