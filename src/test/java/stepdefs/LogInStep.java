package stepdefs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.bson.Document;

import com.mongodb.client.MongoCursor;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import serverChat.MyServer;

public class LogInStep {
	
	MyServer myDb = new MyServer();
	
	@Before("@First")
	@After("@Last")
	public void cleanDB() {
		
		MongoCursor<Document> cursor = MyServer.collection.find().iterator();
		try {
		    while (cursor.hasNext()) {
		        Document doc = cursor.next();
		        System.out.println(doc.toString());
		        MyServer.collection.deleteOne(doc);
		    }
		} finally {
		    cursor.close();
		}
	}
	
	@Given("the username \"(.*)\" exists in db")
	public void the_username_exists_in_db(String username) {
		myDb.saveUser(username, "1234");
		assertNotNull(myDb.findUserByName(username));
	}

	@When("the username \"(.*)\" and password \"(.*)\" match")
	public void the_username_and_password_match(String username, String password) {
		assertTrue(myDb.validateLogin(username, password));
	}
	
	
	@Then("the user \"(.*)\" is online")
	public void the_user_is_online(String username) {
		assertEquals(myDb.getUserStatus(username), 1);
	}
	
	@When("the username \"(.*)\" and password \"(.*)\" don't match")
	public void the_username_and_password_don_t_match(String username, String password) {
		assertFalse(myDb.validateLogin(username, password));
	}
	
	@Then("the user \"(.*)\" is offline")
	public void the_user_is_offline(String username) {
		assertEquals(myDb.getUserStatus(username), 0);
	}
}
