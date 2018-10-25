package database;

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
		//mongoClient.fsync(false);
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
	
	public boolean validateLogin (String username, String password) {
		if (findUserByName(username) == null) {
			return false;
		} else {
			Document myDoc = findUserByName(username);
			if (myDoc.get(Config.PASSWORD_KEY).equals(password)) {
				MyDb.collection.updateOne(Filters.eq(Config.USERNAME_KEY, username), new Document("$set", new Document(Config.USER_STATUS_KEY, Config.USER_STATUS_ACTIVE_VALUE)));
				return true;
			} else {
				MyDb.collection.updateOne(Filters.eq(Config.USERNAME_KEY, username), new Document("$set", new Document(Config.USER_STATUS_KEY, Config.USER_STATUS_OFFLINE_VALUE)));
				return false;
			}
		}
	}
	
	public void findAndUpdate (String username, String newKey, String newValue) {
		Document myDoc = findUserByName(username);
		MyDb.collection.updateOne(Filters.eq(Config.USERNAME_KEY, username), new Document("$set", new Document(newKey, newValue)));
	}
	
	public Document findUserByName (String username) {
		Bson filter = Filters.eq(Config.USERNAME_KEY, username);
		return collection.find(filter).first();
	}
	
	public int getUserStatus (String username) {
		Document myDoc = findUserByName(username);
		if (myDoc == null) {
			return -1;
		}
		return (int)myDoc.get(Config.USER_STATUS_KEY);
	}
}
