package database;

import java.util.ArrayList;
import java.util.Arrays;
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

public class MyDb {
	// mongo CLient
	public static MongoClient mongoClient;
	// database
	public static MongoDatabase db;
	// collection for the chatServer
	public static MongoCollection<Document> collection;

	public MyDb() {
		// connect to mongo
		mongoClient = new MongoClient("localhost", 27017);
		// create a database
		db = mongoClient.getDatabase("chatDB");
		// create collection for the chatServer
		collection = db.getCollection("users");
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
				MyDb.collection.updateOne(Filters.eq(Config.USERNAME_KEY, username),
						new Document("$set", new Document(Config.USER_STATUS_KEY, Config.USER_STATUS_ACTIVE_VALUE)));
				return true;
			} else {
				MyDb.collection.updateOne(Filters.eq(Config.USERNAME_KEY, username),
						new Document("$set", new Document(Config.USER_STATUS_KEY, Config.USER_STATUS_OFFLINE_VALUE)));
				return false;
			}
		}
	}

	public void findAndUpdate(String username, String newKey, String newValue, boolean isList) {
		if (isList) {
			MyDb.collection.updateOne(Filters.eq(Config.USERNAME_KEY, username),
					new Document("$set", new Document(newKey, Arrays.asList(newValue))));
		} else {
			MyDb.collection.updateOne(Filters.eq(Config.USERNAME_KEY, username),
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
		// if chat doesn't exist i the user profile
		if (!isChatInTheList(userName, chatName)) {
			// create a new chat
			Document oneChat = new Document();
			oneChat.append(Config.USER_CHAT_NAME_KEY, chatName);
			oneChat.append(Config.USER_ADMIN_KEY, Config.USER_IS_ADMIN_VALUE);
			// put user as a member of the chat
			ArrayList<String> memberList = new ArrayList<String>();
			memberList.add(userName);
			oneChat.append(Config.USER_MEMBERS_KEY, memberList);
			oneChat.append(Config.USER_MESSEGES_KEY, new Document());
			// if the chat key is not in user's profile
			if (!doc.containsKey(Config.USER_CHAT_KEY)) {
				// create chats list
				ArrayList<Document> chats = new ArrayList<Document>();
				// add oneChat to chats
				chats.add(oneChat);
				MyDb.collection.updateOne(Filters.eq(Config.USERNAME_KEY, userName),
						new Document("$set", new Document(Config.USER_CHAT_KEY, chats)));
			} else {
				System.out.println("Chat exists");
				// get a list of chats
				ArrayList<Document> chats = (ArrayList<Document>) doc.get(Config.USER_CHAT_KEY);
				chats.add(oneChat);
				MyDb.collection.updateOne(Filters.eq(Config.USERNAME_KEY, userName),
						new Document("$set", new Document(Config.USER_CHAT_KEY, chats)));
			}
		}
	}

	public boolean isChatInTheList(String userName, String chatName) {
		Document doc = findUserByName(userName);
		// if the chat key doesn't exist in the users profile
		// then he has no chats
		if (!doc.containsKey(Config.USER_CHAT_KEY)) {
			return false;
		} else {
			ArrayList<Document> chats = (ArrayList<Document>) doc.get(Config.USER_CHAT_KEY);
			// check if chat exist in the list
			for (Document oneChat : chats) {
				String existingChatName = oneChat.getString(Config.USER_CHAT_NAME_KEY);
				if (existingChatName.equals(chatName)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isAdmin(String userName, String chatName) {
		Document userProfile = findUserByName(userName);
		if (userProfile == null) {
			return false;
		}
		ArrayList<Document> chats = (ArrayList<Document>) userProfile.get(Config.USER_CHAT_KEY);
		for (Document oneChat : chats) {
			String existingChatName = oneChat.getString(Config.USER_CHAT_NAME_KEY);
			if (existingChatName.equals(chatName)) {
				return oneChat.getBoolean(Config.USER_ADMIN_KEY);
			}
		}
		return false;
	}
}
