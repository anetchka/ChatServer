package stepdefs;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.bson.Document;

import com.mongodb.client.MongoCursor;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import database.MyDb;

public class InsertUserStep {

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
}
