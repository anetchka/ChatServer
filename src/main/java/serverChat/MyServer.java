package serverChat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.print.Doc;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import util.Config;

public class MyServer {
	// mongo CLient
	public static MongoClient mongoClient;
	// database
	public static MongoDatabase db;
	// collection for the chatServer
	public static MongoCollection<Document> collection;
	public static MongoCollection<Document> chatCollection;

	public MyServer() {
		// connect to mongo
		mongoClient = new MongoClient("localhost", 27017);
		// create a database
		db = mongoClient.getDatabase("chatDB");
		// create collection for the chatServer
		collection = db.getCollection("users");
		chatCollection = db.getCollection("chats");

	}

	public boolean registerUser(String username, String password) {
		if (findUserByName(username) != null) {
			return false;
		} else {
			saveUser(username, password);
			return true;
		}
	}

	public void saveUser(String username, String password) {
		Document newUser = new Document();
		newUser.append(Config.USERNAME_KEY, username);
		newUser.append(Config.PASSWORD_KEY, password);
		newUser.append(Config.USER_STATUS_KEY, "");
		newUser.append(Config.USER_CHAT_KEY, new ArrayList<String>());
		collection.insertOne(newUser);
		// mongoClient.fsync(false);
	}

	public void deleteAllUsers() {
		MongoCursor<Document> cursor = collection.find().iterator();
		try {
			while (cursor.hasNext()) {
				Document doc = cursor.next();
				collection.deleteOne(doc);
			}
		} finally {
			cursor.close();
		}
	}

	public boolean validateLogin(String username, String password) {
		if (findUserByName(username) == null) {
			return false;
		} else {
			Document myDoc = findUserByName(username);
			if (myDoc.get(Config.PASSWORD_KEY).equals(password)) {
				collection.updateOne(Filters.eq(Config.USERNAME_KEY, username),
						new Document("$set", new Document(Config.USER_STATUS_KEY, Config.USER_STATUS_ACTIVE_VALUE)));
				return true;
			} else {
				collection.updateOne(Filters.eq(Config.USERNAME_KEY, username),
						new Document("$set", new Document(Config.USER_STATUS_KEY, Config.USER_STATUS_OFFLINE_VALUE)));
				return false;
			}
		}
	}

	public void findAndUpdate(String username, String newKey, String newValue, boolean isList) {

		if (isList) {
			collection.updateOne(Filters.eq(Config.USERNAME_KEY, username),
					new Document("$set", new Document(newKey, Arrays.asList(newValue))));
		} else {
			collection.updateOne(Filters.eq(Config.USERNAME_KEY, username),
					new Document("$set", new Document(newKey, newValue)));
		}
	}

	public Document findUserByName(String username) {
		Bson filter = Filters.eq(Config.USERNAME_KEY, username);
		return collection.find(filter).first();
	}

	public int getUserStatus(String username) {
		Document myDoc = findUserByName(username);
		if (myDoc == null) {
			return -1;
		}
		return (int) myDoc.get(Config.USER_STATUS_KEY);
	}

	public void createChat(String userName, String chatName) {
		Document doc = findUserByName(userName);
		if (doc == null) {
			return;
		}
		Document chatDocument = chatCollection.find(Filters.eq(Config.USER_CHAT_KEY, chatName)).first();
		if (chatDocument != null) {
			return;
		}
		// if chat doesn't exist i the user profile
		if (!isChatInTheList(userName, chatName)) {
			// create a new chat
			Document oneChat = new Document();
			oneChat.append(Config.CHAT_NAME_KEY, chatName);
			oneChat.append(Config.CHAT_ADMIN_KEY, userName);
			// put user as a member of the chat
			ArrayList<String> memberList = new ArrayList<String>();
			memberList.add(userName);
			oneChat.append(Config.CHAT_MEMBERS_KEY, memberList);
			oneChat.append(Config.CHAT_MESSAGES_KEY, new ArrayList<Document>());
			// add chat to chat collection
			chatCollection.insertOne(oneChat);

			// get a list of chats from user's profile
			ArrayList<String> chats = (ArrayList<String>) findUserByName(userName).get(Config.USER_CHAT_KEY);
			// add oneChat to chats
			chats.add(chatName);
			collection.updateOne(Filters.eq(Config.USERNAME_KEY, userName),
					new Document("$set", new Document(Config.USER_CHAT_KEY, chats)));
		}
	}

	public boolean isChatInTheList(String userName, String chatName) {
		Document doc = findUserByName(userName);
		if (doc == null) {
			return false;
		}
		// if the chat key doesn't exist in the users profile
		ArrayList<String> chats = (ArrayList<String>) doc.get(Config.USER_CHAT_KEY);
		// check if chat exist in the list
		for (String oneChat : chats) {
			if (oneChat.equals(chatName)) {
				return true;
			}
		}
		return false;
	}

	public boolean isAdmin(String userName, String chatName) {
		Document userProfile = chatCollection.find(Filters.eq(Config.CHAT_NAME_KEY, chatName)).first();
		if (userProfile == null) {
			return false;
		}
		ArrayList<String> chatMembers = (ArrayList<String>) userProfile.get(Config.CHAT_MEMBERS_KEY);
		return chatMembers.contains(userName) ? true : false;
	}

	public void sendMessage(String username, String chatname, String message) {
		Document userProfile = findUserByName(username);
		if (userProfile == null) {
			return;
		}
		ArrayList<String> chats = (ArrayList<String>) userProfile.get(Config.USER_CHAT_KEY);
		if (chats == null) {
			return;
		}
		for (String oneChat : chats) {
			if (oneChat.equals(chatname)) {
				Document oneMessage = createMessage(username, chatname, message);
				Document chat = chatCollection.find(Filters.eq(Config.CHAT_NAME_KEY, chatname)).first();
				ArrayList<Document> messages = (ArrayList<Document>) chat.get(Config.CHAT_MESSAGES_KEY);
				messages.add(oneMessage);
				chatCollection.updateOne(Filters.eq(Config.CHAT_NAME_KEY, chatname),
						new Document("$set", new Document(Config.CHAT_MESSAGES_KEY, messages)));
			}
		}
	}

	public Document createMessage(String username, String chatname, String message) {
		Document userProfile = findUserByName(username);
		if (userProfile == null) {
			return null;
		}
		ArrayList<String> chats = (ArrayList<String>) userProfile.get(Config.USER_CHAT_KEY);
		if (chats == null) {
			return null;
		}
		for (String oneChat : chats) {
			if (oneChat.equals(chatname)) {
				// create one Document for message
				Document oneMessage = new Document();
				oneMessage.append(Config.MESSAGE_TEXT_KEY, message);
				oneMessage.append(Config.MESSAGE_FROM_KEY, username);
				oneMessage.append(Config.MESSAGE_DATE_KEY, new Date().toString());
				return oneMessage;
			}
		}
		return null;
	}

	public boolean isMessageInTheHistory(String username, String chatname, String message) {
		Document userProfile = findUserByName(username);
		if (userProfile == null) {
			return false;
		}
		ArrayList<String> chats = (ArrayList<String>) userProfile.get(Config.USER_CHAT_KEY);
		if (chats == null) {
			return false;
		}
		for (String oneChat : chats) {
			if (oneChat.equals(chatname)) {
				Document chat = chatCollection.find(Filters.eq(Config.CHAT_NAME_KEY, chatname)).first();
				ArrayList<Document> messages = (ArrayList<Document>) chat.get(Config.CHAT_MESSAGES_KEY);
				for (Document oneMessage : messages) {
					String textFromChat = (String)oneMessage.get(Config.MESSAGE_TEXT_KEY);
					return textFromChat.equals(message);
				}
			}
		}
		
		return false;
	}
}
