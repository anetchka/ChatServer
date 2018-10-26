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
import database.MyDb;

public class CreateChatStep {
	
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
	}
		@Given("the chat name \"(.*)\" is not in user's list \"(.*)\"")
		public void the_chat_name_is_not_in_user_s_list(String string, String string2) {
			myDb.saveUser(string2, "1234");
			assertFalse(myDb.isChatInTheList(string2, string));
		}

		@When("the user \"(.*)\" wants to create the chat \"(.*)\"")
		public void the_user_wants_to_create_the_chat(String string, String string2) {
			myDb.createChat(string, string2);
		}

		@Then("the user \"(.*)\" creates the chat \"(.*)\"")
		public void the_user_creates_the_chat(String string, String string2) {
			assertTrue(myDb.isChatInTheList(string, string2));
		}

		@Then("the user \"(.*)\" is the admin of the chat \"(.*)\"")
		public void the_user_is_the_admin_of_the_chat(String string, String string2) {
			assertTrue(myDb.isAdmin(string, string2));
		}

		@Given("the chat name \"(.*)\" is in  user's list \"(.*)\"")
		public void the_chat_name_is_in_user_s_list(String string, String string2) {
			assertTrue(myDb.isChatInTheList(string2, string));
		}

		@Then("the user \"(.*)\" is asked to use another chatname \"(.*)\"")
		public void the_user_is_asked_to_use_another_chatname(String string, String string2) {
			myDb.createChat(string, string2);
			assertTrue(myDb.isChatInTheList(string, string2));
		}

}
