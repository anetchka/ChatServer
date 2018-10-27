package util;

import java.util.Date;

public class Config {
	public static final String USERNAME_KEY = "Username";
	public static final String PASSWORD_KEY = "Password";
	public static final String USER_STATUS_KEY = "OnlineStatus";
	// 1 means is online
	public static final int USER_STATUS_ACTIVE_VALUE = 1;
	public static final int USER_STATUS_OFFLINE_VALUE = 0;
	public static final String USER_CHAT_KEY = "Chats";

	public static final String CHAT_NAME_KEY = "Chat name";
	public static final String CHAT_MEMBERS_KEY = "Members";
	public static final String CHAT_MESSAGES_KEY = "Messages";
	public static final String CHAT_ADMIN_KEY = "Chat Admin";
	public static final boolean CHAT_NOT_ADMIN_VALUE = false;
	public static final boolean CHAT_IS_ADMIN_VALUE = true;
	
	public static final String MESSAGE_TEXT_KEY = "Text";
	public static final String MESSAGE_FROM_KEY = "From user";
	public static final String MESSAGE_DATE_KEY = "Date";
}
