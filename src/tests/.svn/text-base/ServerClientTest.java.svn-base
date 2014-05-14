package tests;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;




import main.Server;

import org.junit.Test;





public class ServerClientTest {

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}

	//SERVER-CLIENT TESTS
	   
	   /*
	    * Tests communication between the server and a test client, adding a user
	    */
	@Test
	public void test1() {		
		
		
    	Thread t = new Thread(new Runnable() {
    		public void run() {
    		
    		try {
    			Server server = new Server(4444);
    			server.serve();
    		}
    		catch (Exception e) {
    			System.out.println(e);
    		}
    		}
    		});
    	
    	
    	try {
    		t.start();
			Thread.sleep(4000);
		} catch (InterruptedException e1) {
	
			e1.printStackTrace();
		}
    	
    	Thread client = new Thread(new Runnable() {
    		public void run() {

    		try {
    			Socket chatSocket = new Socket("localhost",4444);
    		PrintWriter out = null;
    		BufferedReader in = null;
    			out = new PrintWriter(chatSocket.getOutputStream(), true);
    			in = new BufferedReader(new InputStreamReader(
    					chatSocket.getInputStream()));
    			System.out.println("Ready to connect...");
    			
    			out.println("username$$$xola");
    			out.flush();
    			String output = in.readLine();
    			//System.out.println("Here is it: " + output);

    			assertEquals("userAdded$$$username$$$xola", output);	
    		} catch (UnknownHostException e) {
    			System.err.println("Don't know about host: localhost.");
    			System.exit(1);
    		} catch (IOException e) {
    			System.err.println("Couldn't get I/O for "
    					+ "the connection to: localhost.");
    			System.exit(1);
    		}}});
    	
    	client.start();
    	

    	
    }
	
	
	 /*
	    * Tests communication between the server and a test client, adding an invalid username
	    */
	@Test
	public void invalidTest1() {		
		
		
    	Thread t = new Thread(new Runnable() {
    		public void run() {
    		
    		try {
    			Server server = new Server(4443);
    			server.serve();
    		}
    		catch (Exception e) {
    			System.out.println(e);
    		}
    		}
    		});
    
    	
    	try {
    		t.start();
			Thread.sleep(4000);
		} catch (InterruptedException e1) {
	
			e1.printStackTrace();
		}
    	
    	Thread client = new Thread(new Runnable() {
    		public void run() {

    		try {
    			Socket chatSocket = new Socket("localhost",4443);
    		PrintWriter out = null;
    		BufferedReader in = null;
    			out = new PrintWriter(chatSocket.getOutputStream(), true);
    			in = new BufferedReader(new InputStreamReader(
    					chatSocket.getInputStream()));
    			System.out.println("Ready to connect...");
    			
    			out.println("username$$$xola");
    			out.flush();
    			String output = in.readLine();
    			out.println("username$$$xola");
    			output += " " + in.readLine();
    			System.out.println("Here is it: " + output);

    			assertEquals("userAdded$$$username$$$xola invalid username", output);	
    		} catch (UnknownHostException e) {
    			System.err.println("Don't know about host: localhost.");
    			System.exit(1);
    		} catch (IOException e) {
    			System.err.println("Couldn't get I/O for "
    					+ "the connection to: localhost.");
    			System.exit(1);
    		}}});
    	
    	client.start();

    	
    }
	
	

	
	 /*
	    * Tests communication between the server and a test client, adding a user and starting a topic
	    */
	@Test
	public void test3() {		

		
    	Thread t = new Thread(new Runnable() {
    		public void run() {
    		
    		try {
    			Server server = new Server(4446);
    			server.serve();
    		}
    		catch (Exception e) {
    			System.out.println(e);
    		}
    		}
    		});
    	
    	
    	try {
    		t.start();
			Thread.sleep(4000);
		} catch (InterruptedException e1) {
	
			e1.printStackTrace();
		}
    	
    	
		Thread client1 = new Thread(new Runnable() {
    		public void run() {

    		try {
    			Socket chatSocket = new Socket("localhost",4446);
    		PrintWriter out = null;
    		BufferedReader in = null;
    			out = new PrintWriter(chatSocket.getOutputStream(), true);
    			in = new BufferedReader(new InputStreamReader(
    					chatSocket.getInputStream()));
    			System.out.println("Ready to connect...");
    			
    			String output = "";

    			out.println("username$$$sean");
    			out.flush();
    			output = in.readLine() + "/n";
    			out.println("topic$$$games$$$username$$$sean");
    			out.flush();
    			output += in.readLine();
    			
    			
    			//System.out.println("Here is it: " + output);

    			assertEquals("userAdded$$$username$$$sean/ntopicStarted$$$games$$$username$$$sean", output);	
    		} catch (UnknownHostException e) {
    			System.err.println("Don't know about host: localhost.");
    			System.exit(1);
    		} catch (IOException e) {
    			System.err.println("Couldn't get I/O for "
    					+ "the connection to: localhost.");
    			System.exit(1);
    		}}});
		
		client1.start();

		
			
    }
	
	
	 /*
	    * Tests communication between the server and two test clients, adding two users
	    */
	@Test
	public void test4() {		

		
    	Thread t = new Thread(new Runnable() {
    		public void run() {
    		
    		try {
    			Server server = new Server(4447);
    			server.serve();
    		}
    		catch (Exception e) {
    			System.out.println(e);
    		}
    		}
    		});
    	
    	
    	try {
    		t.start();
			Thread.sleep(4000);
		} catch (InterruptedException e1) {
	
			e1.printStackTrace();
		}
    	
    	
		Thread client1 = new Thread(new Runnable() {
    		public void run() {

    		try {
    			Socket chatSocket = new Socket("localhost",4447);
    		PrintWriter out = null;
    		BufferedReader in = null;
    			out = new PrintWriter(chatSocket.getOutputStream(), true);
    			in = new BufferedReader(new InputStreamReader(
    					chatSocket.getInputStream()));
    			System.out.println("Ready to connect...");
    			
    			String output = "";
    			out.println("username$$$xola");
    			out.flush();
    			output += in.readLine() + "/n";
    			//out.println("topic$$$MIT$$$username$$$xola");
    			//out.flush();
    			//output += in.readLine();
    			
    			
    			
    			System.out.println("Here is it: " + output);
    			//topicStarted$$$MIT$$$username$$$xola

    			assert(output.contains("userAdded$$$username$$$xola/n"));	
    		} catch (UnknownHostException e) {
    			System.err.println("Don't know about host: localhost.");
    			System.exit(1);
    		} catch (IOException e) {
    			System.err.println("Couldn't get I/O for "
    					+ "the connection to: localhost.");
    			System.exit(1);
    		}}});
		
		client1.start();
		
		Thread client2 = new Thread(new Runnable() {
    		public void run() {

    		try {
    			Socket chatSocket = new Socket("localhost",4447);
    		PrintWriter out = null;
    		BufferedReader in = null;
    			out = new PrintWriter(chatSocket.getOutputStream(), true);
    			in = new BufferedReader(new InputStreamReader(
    					chatSocket.getInputStream()));
    			System.out.println("Ready to connect...");
    			
    			String output = "";
    			out.println("username$$$sean");
    			out.flush();
    			output += in.readLine() + "/n";
    			output += in.readLine() + "/n";
    			//out.println("topic$$$MIT$$$username$$$xola");
    			//out.flush();
    			//output += in.readLine();
    			
    			
    			
    			System.out.println("Here is it: " + output);
    			///ntopicStarted$$$MIT$$$username$$$xola

    			assert(output.contains("userAdded$$$username$$$sean/n"));	
    		} catch (UnknownHostException e) {
    			System.err.println("Don't know about host: localhost.");
    			System.exit(1);
    		} catch (IOException e) {
    			System.err.println("Couldn't get I/O for "
    					+ "the connection to: localhost.");
    			System.exit(1);
    		}}});
		
		client2.start();
		
    }

}

