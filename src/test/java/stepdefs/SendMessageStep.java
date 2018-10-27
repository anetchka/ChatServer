package stepdefs;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.bson.Document;

import com.mongodb.client.MongoCursor;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import database.MyDb;

public class SendMessageStep {

	MyDb myDb = new MyDb();

	@Before("@First")
	@After("@Last")
	public void cleanDB() {

		MongoCursor<Document> cursor = MyDb.collection.find().iterator();
		try {
			while (cursor.hasNext()) {
				Document doc = cursor.next();
				System.out.println("HAHA " + doc.toString());
				MyDb.collection.deleteOne(doc);
			}
		} finally {
			cursor.close();
		}
		
		MongoCursor<Document> cursorChat = MyDb.chatCollection.find().iterator();
		try {
			while (cursorChat.hasNext()) {
				Document doc = cursorChat.next();
				System.out.println("HAHA " + doc.toString());
				MyDb.chatCollection.deleteOne(doc);
			}
		} finally {
			cursorChat.close();
		}
	}

	@Given("the username \"(.*)\" doesn't have chat \"(.*)\"")
	public void the_username_doesn_t_have_chat(String username, String chat) {
		// create user without any chats
		myDb.registerUser(username, "123");
		assertFalse(myDb.isChatInTheList(username, chat));
	} 

	@When("the user \"(.*)\" sends message \"(.*)\" to the chat \"(.*)\"")
	public void the_user_sends_message_to_the_chat(String username, String message, String chat) {
		myDb.createMessage(username, chat, message);
		myDb.sendMessage(username, chat, message);
	}
	
	@Then("the message\"(.*)\" is failed to be sent to chat \"(.*)\" of user \"(.*)\"")
	public void the_message_is_failed_to_be_sent_to_chat_of_user(String message, String chat, String username) {
		assertFalse(myDb.isMessageInTheHistory(username, chat, message));
	}
	
	@Given("the username \"(.*)\" has chat \"(.*)\"")
	public void the_username_has_chat(String username, String chat) {
		// add chat to user's profile
		myDb.createChat(username, chat);
		assertTrue(myDb.isChatInTheList(username, chat));
	}

	@Then("the message \"(.*)\" is added to username's \"(.*)\" chat \"(.*)\" history")
	public void the_message_is_added_to_username_s_chat_history(String message, String username, String chat) {
		assertTrue(myDb.isMessageInTheHistory(username, chat, message));
	}
}
