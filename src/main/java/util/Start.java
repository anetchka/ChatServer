package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;

import database.MyDb;

public class Start {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyDb db = new MyDb();
		db.registerUser("Ana", "121");
		Bson filter = Filters.eq(Config.USERNAME_KEY, "Ana");
	//	System.out.println("1122 " + MyDb.collection.find(filter).first());
		

		
	//	MyDb.collection.updateOne(filter, new Document("$set", new Document(Config.USER_CHAT_LIST_KEY, Arrays.asList("RS", "RG"))));
	/*	Document doc = MyDb.collection.find(filter).first();

		Document oneChat = new Document();
		oneChat.append("TEst", new ArrayList<String>());
		oneChat.append("Sexond value", "Hello, it works");
		
		
		Document secondChat = new Document();
		secondChat.append("TEst", new ArrayList<String>());
		secondChat.append("Sexond value", "Hello, it works two");  
		
		ArrayList<Document> chats = new ArrayList<>();
		chats.add(oneChat);
		chats.add(secondChat);
		
		MyDb.collection.updateOne(filter, new Document("$set", new Document(Config.USER_CHAT_KEY, chats)));
		
		System.out.println(MyDb.collection.find(filter).first());
		
		Document thrirdChat = new Document();
		thrirdChat.append("TEst", new ArrayList<String>());
		thrirdChat.append("Sexond value", "I DID IT!!!!");  
		
		chats.add(thrirdChat);
		
		MyDb.collection.updateOne(filter, new Document("$set", new Document(Config.USER_CHAT_KEY, chats)));
		System.out.println(MyDb.collection.find(filter).first());
		*/
		
		db.createChat("Ana", "AAAA");
		db.createChat("Ana", "CCC");
		db.createChat("Ana", "AAAA");
		System.out.println(db.isAdmin("Ana", "AAAA"));
		//System.out.println(db.isChatInTheList("Ana", "AAAA"));
		//db.createChat("Ana", "AAAA");
		//db.createChat("Ana", "CCC");
		//db.createChat("Ana", "DDD");
		
	//	System.out.println(MyDb.collection.find(filter).first());
		
		db.createMessage("Ana", "AAAA", "Hello World");
		db.createMessage("Ana", "AAAA", "WWERTZUI");
		db.sendMessage("Ana", "AAAA", "WWERTZUI");
		System.out.println(db.isMessageInTheHistory("Ana", "AAAA", "WWERUI"));
		//Bson ff = Filters.and(Filters.eq(Config.USER_CHAT_KEY),Filters.eq(Config.USER_CHAT_NAME_KEY, "AAAA"), Filters.eq(Config.USER_MESSAGES_KEY, ""));
		//MyDb.collection.updateOne(ff, new Document("$set", new Document(Config.MESSAGE_TEXT_KEY, "Hello World")));
		
	}
}
