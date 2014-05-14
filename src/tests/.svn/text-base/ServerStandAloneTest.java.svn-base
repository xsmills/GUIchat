package tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;



import main.Server;

import org.junit.Test;

import backEnd.ConversationModel;



public class ServerStandAloneTest {

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}

	//SERVER STAND ALONE TESTS
	   
	   /*
	    * Tests the server's ability to handling requests and make appropriate changes
	    * to the data (conversation) model
	    */
	
	
	
	   
	   /*
	    * Tests the username request
	    */
	@Test
	public void test1() {
		final String topic = "topic";
		Server server = null;
		ConversationModel convo = null;

		try {
			// server
			server = new Server(4444);
			//server.serve();
			String output = server.handleRequest("username$$$xola");
			String output2 = server.handleRequest("topic$$$topic$$$username$$$xola");
			
			convo = server.getConvoMap().get(topic);

		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		assertEquals("xola", convo.getStringUsers());
	}
	

	  /*
	    * Tests the username request and startTopic, jointopic requests
	    */
	@Test
	public void test2() {
		final String topic = "morning";
		Server server = null;
		ConversationModel convo = null;

		try {
			// server
			server = new Server(4445);
			//server.serve();
			String output = server.handleRequest("username$$$sean");
			String output2 = server.handleRequest("username$$$kevin");
			String output3 = server.handleRequest("topic$$$morning$$$username$$$sean");
			String output4 = server.handleRequest("join$$$morning$$$username$$$kevin");
			//System.out.println(output);
			
			convo = server.getConvoMap().get(topic);

		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		assertEquals("sean$$$kevin", convo.getStringUsers());
	}
	
	  /*
	    * Tests the username, startTopic, joinTopic and messageFromTopic request
	    */
	@Test
	public void test3() {
		final String topic = "morning";
		Server server = null;
		ConversationModel convo = null;

		try {
			// server
			server = new Server(4446);
			//server.serve();
			String output = server.handleRequest("username$$$sean");
			String output2 = server.handleRequest("username$$$kevin");
			String output3 = server.handleRequest("topic$$$morning$$$username$$$sean");
			String output4 = server.handleRequest("join$$$morning$$$username$$$kevin");
			String output5 = server.handleRequest("messageFromTopic$$$morning$$$username$$$kevin$$$hello");
			String output6 = server.handleRequest("messageFromTopic$$$morning$$$username$$$sean$$$hey wassup");
			//System.out.println(output);
			
			convo = server.getConvoMap().get(topic);

		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		assertEquals("kevin: hello\nsean: hey wassup\n", convo.getText());
	}


	  /*
	    * Tests the username, startTopic, joinTopic, leave and messageFromTopic request
	    */
	@Test
	public void test4() {
		final String topic = "morning";
		Server server = null;
		ConversationModel convo = null;

		try {
			// server
			server = new Server(4447);
			//server.serve();
			String output = server.handleRequest("username$$$sean");
			String output2 = server.handleRequest("username$$$kevin");
			String output3 = server.handleRequest("topic$$$morning$$$username$$$sean");
			String output4 = server.handleRequest("join$$$morning$$$username$$$kevin");
			String output5 = server.handleRequest("messageFromTopic$$$morning$$$username$$$kevin$$$hello");
			String output6 = server.handleRequest("leave$$$morning$$$username$$$sean");
			//System.out.println(output);
			
			convo = server.getConvoMap().get(topic);

		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		assertEquals("kevin", convo.getStringUsers());
	}
	

	  /*
	    * Tests the startTopic, joinTopic and leave request
	    */
	@Test
	public void test5() {
		final String topic = "morning";
		Server server = null;
		ConversationModel convo = null;

		try {
			// server
			server = new Server(4448);
			//server.serve();
			String output3 = server.handleRequest("topic$$$morning$$$username$$$sean");
			String output4 = server.handleRequest("join$$$morning$$$username$$$kevin");
			String output7 = server.handleRequest("join$$$morning$$$username$$$nasha");
			String output8 = server.handleRequest("join$$$morning$$$username$$$tanya");
			String output9 = server.handleRequest("join$$$morning$$$username$$$neil");
			String output1 = server.handleRequest("join$$$morning$$$username$$$rob");
			String output6 = server.handleRequest("leave$$$morning$$$username$$$sean");
			String output2 = server.handleRequest("leave$$$morning$$$username$$$nasha");

			//System.out.println(output);
			
			convo = server.getConvoMap().get(topic);

		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		assertEquals("kevin$$$tanya$$$neil$$$rob", convo.getStringUsers());
	
	
}

	
	//invalid user input
	
	@Test
	public void invalidUserTest() {
		Server server = null;
		String output = null;

		try {
			// server
			server = new Server(4449);
			//server.serve();
			output = server.handleRequest("username$$$#@$@$@!!");
			System.out.println(output);


		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		assertEquals("invalid username", output);
	}
	
	@Test
	public void invalidTopictest() {
		Server server = null;
		String output = null;

		try {
			// server
			server = new Server(4443);
			//server.serve();
			output = server.handleRequest("topic$$$aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa$$$username$$$xola");
			System.out.println(output);


		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		assertEquals("username$$$xola$$$invalid topicName", output);
	}
	
	
	//tests non-existent user
	@Test
	public void nonExistentUserTest() {
		final String topic = "morning";
		Server server = null;
		ConversationModel convo = null;
		String output6 = null;

		try {
			// server
			server = new Server(4442);
			//server.serve();
			String output = server.handleRequest("username$$$sean");
			String output2 = server.handleRequest("username$$$kevin");
			String output3 = server.handleRequest("topic$$$morning$$$username$$$sean");
			String output4 = server.handleRequest("join$$$morning$$$username$$$kevin");
			String output5 = server.handleRequest("messageFromTopic$$$morning$$$username$$$kevin$$$hello");
			output6 = server.handleRequest("messageFromTopic$$$morning$$$username$$$xola$$$hey wassup");
			//System.out.println(output);
			
			convo = server.getConvoMap().get(topic);

		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		assertEquals("username$$$xola$$$non-existent user", output6);
	}
	
	
	//tests non-existent topic
	@Test
	public void nonExistentTopicTest() {
		final String topic = "morning";
		Server server = null;
		ConversationModel convo = null;
		String output6 = null;

		try {
			// server
			server = new Server(4441);
			//server.serve();
			String output = server.handleRequest("username$$$sean");
			String output2 = server.handleRequest("username$$$kevin");
			String output3 = server.handleRequest("topic$$$morning$$$username$$$sean");
			String output4 = server.handleRequest("join$$$morning$$$username$$$kevin");
			String output5 = server.handleRequest("messageFromTopic$$$morning$$$username$$$kevin$$$hello");
			output6 = server.handleRequest("messageFromTopic$$$dawn$$$username$$$sean$$$hey wassup");
			//System.out.println(output);
			
			convo = server.getConvoMap().get(topic);

		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		assertEquals("username$$$sean$$$non-existent topic", output6);
	}
	
	
	
	//EXTRA TESTS
	//CASE 1
	   //        Check that the protocol generates the desired outputs following the protocol
	  //CASE 2
	   //    Check that synchronization on handleRequest avoids race conditions

	   
	   Server srv;
	   
	   @Test
	   public void createServerObject() throws IOException{
	       srv= new Server(4440);
	       String output= srv.handleRequest("joinNew$$$argTopic$$$username$$$nameIsMay");
	   }
	    
	   //Tests for test case 1
	    @Test
	    public void joinNewTest() throws IOException {
	        srv= new Server(4244);
	        String jnRegex= "joinedTopic [A-Z a-z]{1,29} username [A-Z_0-9a-z]{1,10} [0-9]* ";
	        
	        //Start Topic
	        srv.handleRequest("topic$$$argTopic$$$username$$$nameIsJune");
	        //Join Message
	        String output= srv.handleRequest("join$$$argTopic$$$username$$$nameIsMay");
	        output= output.replace("$$$", " ");
	        
	        assert(output.matches(jnRegex));
	    }
	    
	    @Test
	    public void joinNewIncorrectMessageTest() throws IOException {
	        srv= new Server(4044);
	        String invJnRegex= "[A-Za-z ]*"; //invalid command message
	        
	        //Incorrect Join Message
	        String output= srv.handleRequest("joinNew$$$argTopic$$$username$$$nameIsMay");
	        output= output.replace("$$$", " ");
	        assert(output.matches(invJnRegex));
	    }
	    
	    @Test
	    public void startTopicTest() throws IOException {
	        srv= new Server(4344);
	        String stRegex= "topicStarted \\p{Alnum}[A-Z 0-9a-z]{1,29} username [A-Z_0-9a-z]{1,10}";
	        
	        //Start Topic Message
	        String output= srv.handleRequest("topic$$$argTopic2$$$username$$$nameIsJune");
	        output= output.replace("$$$", " ");
	        
	        assert(output.matches(stRegex));
	    }
	    
	    
	    @Test
	    public void startTopicTwiceTest() throws IOException {
	        srv= new Server(4744);
	        String stRegex= "topicStarted [A-Z 0-9a-z]{1,29} username [A-Z_0-9a-z]{1,10}";
	        
	        //Start First Topic Message
	        String output= srv.handleRequest("topic$$$argTopic2$$$username$$$nameIsYun");
	        output= output.replace("$$$", " ");

	        //Start Second Topic Message
	        String output2= srv.handleRequest("topic$$$argTopic2$$$username$$$nameIsVerily");
	                 
	        assert(output.matches(stRegex));
	    }
	    
	    @Test
	    public void leaveConversationTest() throws IOException {
	        Server srv= new Server(4844);
	        String lcRegex= "userRemoved [A-Z_0-9a-z]{1,10} username [A-Z_0-9a-z]{1,10}";
	        //Start Conversation
	        String output= srv.handleRequest("topic$$$argTopic$$$username$$$nameIsNor");
	        //Leave Conversation/Topic Message
	        String output2= srv.handleRequest("leave$$$argTopic$$$username$$$nameIsNor");
	        output2= output2.replace("$$$", " ");
	        
	        assert(output2.matches(lcRegex));
	    }

}

