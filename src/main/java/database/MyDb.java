package database;

import org.bson.BSON;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import chatServer.User;
import util.Config;

import static java.util.concurrent.TimeUnit.SECONDS;

public class MyDb {
	// mongo CLient
	public static MongoClient mongoClient;
	// database
	public static MongoDatabase db;
	// collection for the chatServer
	public static MongoCollection<Document> collection;

	public MyDb() {
		// connect to mongo
		this.mongoClient = new MongoClient("localhost", 27017);
		// create a database
		this.db = mongoClient.getDatabase("chatDB");
		// create collection for the chatServer
		this.collection = db.getCollection("users");
	}
	
	public boolean createUser(String username, String password) {
		// check if user with the password exists in database
		Bson filter = Filters.eq(Config.USERNAME_KEY, username);
		Document myDoc = collection.find(filter).first();
		if (myDoc != null) {
			return false;
		} else {
			Document newUser = new Document();
			newUser.append(Config.USERNAME_KEY, username);
			newUser.append(Config.PASSWORD_KEY, password);
			collection.insertOne(newUser);
			return true;
		}
	}
}
