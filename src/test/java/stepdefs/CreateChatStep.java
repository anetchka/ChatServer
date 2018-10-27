package stepdefs;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.bson.Document;

import com.mongodb.client.MongoCursor;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import serverChat.MyServer;

public class CreateChatStep {
	
	MyServer myDb = new MyServer();
	
	@Before("@First")
	@After("@Last")
	public void cleanDB() {
		
		MongoCursor<Document> cursor = MyServer.collection.find().iterator();
		try {
		    while (cursor.hasNext()) {
		        Document doc = cursor.next();
		        System.out.println("HAHA " + doc.toString());
		        MyServer.collection.deleteOne(doc);
		    }
		} finally {
		    cursor.close();
		}
	}
		@Given("the chat name \"(.*)\" is not in user's list \"(.*)\"")
		public void the_chat_name_is_not_in_user_s_list(String chat, String username) {
			myDb.saveUser(username, "1234");
			assertFalse(myDb.isChatInTheList(username, chat));
		}

		@When("the user \"(.*)\" wants to create the chat \"(.*)\"")
		public void the_user_wants_to_create_the_chat(String username, String chat) {
			myDb.createChat(username, chat);
		}

		@Then("the user \"(.*)\" creates the chat \"(.*)\"")
		public void the_user_creates_the_chat(String username, String chat) {
			assertTrue(myDb.isChatInTheList(username, chat));
		}

		@Then("the user \"(.*)\" is the admin of the chat \"(.*)\"")
		public void the_user_is_the_admin_of_the_chat(String username, String chat) {
			assertTrue(myDb.isAdmin(username, chat));
		}

		@Given("the chat name \"(.*)\" is in  user's list \"(.*)\"")
		public void the_chat_name_is_in_user_s_list(String chat, String username) {
			assertTrue(myDb.isChatInTheList(username, chat));
		}

		@Then("the user \"(.*)\" is asked to use another chatname \"(.*)\"")
		public void the_user_is_asked_to_use_another_chatname(String username, String chat) {
			myDb.createChat(username, chat);
			assertTrue(myDb.isChatInTheList(username, chat));
		}

}
