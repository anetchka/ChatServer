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
import database.MyDb;

public class LogInStep {
	
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
}
