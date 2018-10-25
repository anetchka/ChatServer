package stepdefs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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

public class StepDefinitions {
	
	MyDb myDb = new MyDb();
	
	@Before("@First")
	@After("@Last")
	public void cleanDB() {
		
		MongoCursor<Document> cursor = MyDb.collection.find().iterator();
		try {
		    while (cursor.hasNext()) {
		        Document doc = cursor.next();
		        System.out.println(doc.toString());
		        MyDb.collection.deleteOne(doc);
		    }
		} finally {
		    cursor.close();
		}
	}
	
//	----------------Register a User--------------------
	
	@Given("the username \"(.*)\" doesn't exist in the chat")
	public void the_username_doesn_t_exist_in_the_chat(String string) {
	    assertNull(myDb.findUserByName(string));
	}

	@When("the user registers with username \"(.*)\" and password \"(.*)\"")
	public void the_user_registers_with_username_and_password(String string, String string2) {
	    myDb.registerUser(string, string2);
	}

	@Then("the server conformation with username \"(.*)\" and password \"(.*)\"")
	public void the_server_conformation_with_username_and_password(String string, String string2) {
	    myDb.saveUser(string, string2);
	    assertNotNull(myDb.findUserByName(string));
	}
	
	@Given("the username \"(.*)\" exists in the chat")
	public void the_username_exists_in_the_chat(String string) {
		assertNotNull(myDb.findUserByName(string));
	}

	@Then("the server asks to enter a new username \"(.*)\" and  password \"(.*)\"")
	public void the_server_asks_to_enter_a_new_username_and_password(String string, String string2) {
		assertFalse(myDb.registerUser(string, string2));
	}
	
	// -----------------LogIn-----------------
	

	@Given("the username \"(.*)\" exists in db")
	public void the_username_exists_in_db(String string) {
		myDb.saveUser(string, "1234");
		assertNotNull(myDb.findUserByName(string));
	}

	@When("the username \"(.*)\" and password \"(.*)\" match")
	public void the_username_and_password_match(String string, String string2) {
		assertTrue(myDb.validateLogin(string, string2));
	}
	
	
	@Then("the user \"(.*)\" is online")
	public void the_user_is_online(String string) {
		assertEquals(myDb.getUserStatus(string), 1);
	}
	
	@When("the username \"(.*)\" and password \"(.*)\" don't match")
	public void the_username_and_password_don_t_match(String string, String string2) {
		assertFalse(myDb.validateLogin(string, string2));
	}
	
	@Then("the user \"(.*)\" is offline")
	public void the_user_is_offline(String string) {
		assertEquals(myDb.getUserStatus(string), 0);
	}
	
	//--------------------Create Chat -------------------------
	@Given("the chat name \"(.*)\" doesn't exist")
	public void the_chat_name_doesn_t_exist(String string) {
	}

	@When("the user \"(.*)\" wants to create the chat \"(.*)\"")
	public void the_user_wants_to_create_the_chat(String string, String string2) {
	}

	@Then("the user \"(.*)\" creates the chat \"(.*)\"")
	public void the_user_creates_the_chat(String string, String string2) {
	}

	@Then("the user \"(.*)\" is the admin of the chat \"(.*)\"")
	public void the_user_is_the_admin_of_the_chat(String string, String string2) {
	}

	@Given("the chat name \"(.*)\" exists")
	public void the_chat_name_exists(String string) {
	}

	@Then("the user \"(.*)\" gets message \"(.*)\"")
	public void the_user_gets_message(String string, String string2) {
	}
}
