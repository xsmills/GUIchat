package GUI;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.table.DefaultTableModel;

import backEnd.UserModel;

public class ChatGui extends JFrame{
	//Declares all GUI components and class variables. 
	private static JLabel chatTopicLabel;
	private JButton joinTopicButton;
	private JButton startTopicButton;
	private static JTextField newTopicField;
	private JList availableTopics;
	private static DefaultListModel availableTopicsModel;
	private JList conversationUsers;
	private static DefaultListModel conversationUsersModel;
	private JEditorPane conversationMessage;
	private static JEditorPane conversationWindow;
	private JOptionPane setUserName;
	private JButton leaveTopicButton;
	private JButton helpButton;
	DefaultTableModel table;
	static String tempname;
	public static String username;
	public static boolean validUser = false;
	static String currentTopic = "None";
	//A hashmap of all topics mapping to a list of all the users in each topic
	public static HashMap<String, ArrayList<String>> allTopics = new HashMap<String, ArrayList<String>>();
	//List of the topics you are currently a part of. 
	static List<String> myTopics = new ArrayList<String>();
	public static Socket chatSocket;
	//UserModel that keeps track of all the text for topics the user is a part of.
	public static UserModel myModel;
	private JLabel userLabel;
	final Timer timer;
	String userState = "none";
	static String input;
	//////////////////////////////////////////////////////
	public ChatGui() {
		//Initializes all the GUI components
		////////////////////////////////////
		chatTopicLabel = new JLabel("Chatroom: " + currentTopic);
		chatTopicLabel.setName("chatTopicLabel");
		userLabel = new JLabel("Current Users: ");
		userLabel.setName("userLabel");
		joinTopicButton = new JButton("Join Topic");
		joinTopicButton.setName("joinTopicButton");
		startTopicButton = new JButton("Start Topic");
		startTopicButton.setName("startTopicButton");
		newTopicField = new JTextField();
		newTopicField.setName("newTopicField");
		availableTopicsModel = new DefaultListModel();
		availableTopics = new JList(availableTopicsModel);
		availableTopics.setName("avaliableTopics");
		conversationUsersModel = new DefaultListModel();
		conversationUsers = new JList(conversationUsersModel);
		conversationUsers.setName("conversationUsers");
		conversationMessage = new JEditorPane();
		conversationMessage.setName("conversationMessage");
		conversationWindow = new JEditorPane();
		conversationWindow.setName("conversationWindow");
		setUserName = new JOptionPane();
		setUserName.setName("setUserName");
		leaveTopicButton = new JButton("Leave Topic");
		leaveTopicButton.setName("leaveTopicButton");
		helpButton = new JButton("Help");
		helpButton.setName("helpButton");
		/////////////////////////////////////////////////////////////
		//This adds all the scrollpanes making everything in the GUI scrollable if necessary.
		//////////////////////////////////////////////////////////
		
		JScrollPane editorScrollPane = new JScrollPane(conversationWindow);
		editorScrollPane.setVerticalScrollBarPolicy(
		                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		editorScrollPane.setPreferredSize(new Dimension(250, 145));
		editorScrollPane.setMinimumSize(new Dimension(10, 10));
		
		
		JScrollPane userScrollPane = new JScrollPane(conversationUsers);
		userScrollPane.setVerticalScrollBarPolicy(
		                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		userScrollPane.setPreferredSize(new Dimension(250, 145));
		userScrollPane.setMinimumSize(new Dimension(10, 10));
		
		JScrollPane topicScrollPane = new JScrollPane(availableTopics);
		topicScrollPane.setVerticalScrollBarPolicy(
		                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		topicScrollPane.setPreferredSize(new Dimension(250, 145));
		topicScrollPane.setMinimumSize(new Dimension(10, 10));
		/////////////////////////////////////////////////////////
		
		//This makes sure that when a user closes his window, all other users are aware 
		//of him/her not being in any of the topics anymore. 
		addWindowListener(   
			      new java.awt.event.WindowAdapter()   
			      {  
			    	//Send leave events to the server for all my topics. 
			        public void windowClosing( java.awt.event.WindowEvent e )   
			        {  
			        	PrintWriter out = null;
				    	try {
							out = new PrintWriter(chatSocket.getOutputStream(), true);
						} catch (IOException ex) {
							ex.printStackTrace();
						}
				        String topic;
				        String SPACE = "$$$";
			          for(int i = 0; i < myTopics.size(); i++){
			        	  topic = myTopics.get(i);
			        	  String userInput;	        
					        
				        	userInput = "leave" + SPACE + topic + SPACE
				                    + "username" + SPACE + username;
				        	out.println(userInput);
					        out.flush();
			          }
			          out.println("disconnect" + SPACE + username);
			          System.exit( 0 );  
			        }  
			      }  
			    );  

		//Use the Group Layout model for the Gui and set Defaults
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		this.setSize(750, 500);
		
		//Set the horizontal layout
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup()
							.addGroup(layout.createSequentialGroup()
									.addGroup(layout.createParallelGroup(Alignment.LEADING)
											.addComponent(chatTopicLabel, 300,300,300))
									.addGroup(layout.createParallelGroup(Alignment.TRAILING)
											.addComponent(leaveTopicButton)))
							.addComponent(editorScrollPane, 425, 425, 425)
							.addComponent(conversationMessage, 425, 425, 425))
					.addGroup(layout.createParallelGroup()
							.addComponent(userLabel, 130, 130, 130)
							.addComponent(userScrollPane, 130, 130, 130))
					.addGroup(layout.createParallelGroup()
							.addComponent(startTopicButton, 130, 130 , 130)
							.addComponent(newTopicField, 130, 130, 130)
							.addComponent(joinTopicButton, 130 , 130 ,130)
							.addComponent(topicScrollPane, 130 , 130 ,130)
							.addComponent(helpButton, 130, 130, 130))
		);
		//Set the vertical layout
		layout.setVerticalGroup(
				layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(Alignment.CENTER)
							.addComponent(chatTopicLabel)
							.addComponent(leaveTopicButton)
							.addComponent(userLabel)
							.addComponent(startTopicButton))
					.addGroup(layout.createParallelGroup()
							.addComponent(editorScrollPane)
							.addComponent(userScrollPane)
							.addGroup(layout.createSequentialGroup()
									.addComponent(newTopicField, 30 , 30, 30)
									.addComponent(joinTopicButton)
									.addComponent(topicScrollPane)))
					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
							.addComponent(conversationMessage, 75, 75, 75)
							.addComponent(helpButton))
							
		);
		
		//This chunk of code implements a user's typing state.
		//This is called when a user has entered text but not entered
		//Any for 15 seconds. 
		ActionListener setState = new ActionListener() {
			public void actionPerformed(ActionEvent evt){
				userState = "entered text";
				PrintWriter out = null;
				try {
					out = new PrintWriter(chatSocket.getOutputStream(), true);
				} catch (IOException e) {
					e.printStackTrace();
				}
				String userInput;     
		        String SPACE = "$$$";
		        //Sends the updated userState to the server to alert users. 
		        userInput = "userAction" + SPACE + currentTopic + SPACE
		                + "username" + SPACE + username + SPACE + " has entered text";
		        out.println(userInput);
		        out.flush();
		        timer.stop();
			}
		};
		//Initializes the Timer to keep track of when users are no longer typing.
		timer = new Timer(15000, setState);
		
		// add listeners for user input
		//This listener allows the user to double click a topic.
		MouseAdapter mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                // respond only on double-click
                if (event.getClickCount() == 2) {
                	//Sets the topic and chatTopic Label and calls the joinTopic method
                	//which joins the clicked topic. 
                	currentTopic = (String) availableTopicsModel.get(availableTopics.locationToIndex(event.getPoint()));
                	chatTopicLabel.setText("Chatroom: " + currentTopic);
                	joinTopic(currentTopic);
                }
            }
        };
        //Initializes the mouse listener. 
        availableTopics.addMouseListener(mouseListener);
        
    //Listener for the join topic button. 
    joinTopicButton.addActionListener(new ActionListener() {
    	@SuppressWarnings("deprecation")
    	public void actionPerformed(ActionEvent event){
    		if(availableTopics.getSelectedIndex() != -1){
    			//Sets the topic and chatTopic Label and calls the joinTopic method
            	//which joins the clicked topic. Same as the joinTopic mouse listener
    			currentTopic = (String) availableTopicsModel.get(availableTopics.getSelectedIndex());
    			chatTopicLabel.setText("Chatroom: " + currentTopic);
    			joinTopic(currentTopic);
    		}
    	}
    });
    
    //Listener for start topic button. 
	startTopicButton.addActionListener(new ActionListener() {
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent event){
			//Calls the startNewTopic method which starts the entered topic.
			String text = newTopicField.getText();
			startNewTopic(text);
		}
	});
	//Key listener for the start topic field. 
	newTopicField.addKeyListener(new KeyAdapter() {
		public void keyPressed(KeyEvent event) {
			if(event.getKeyCode() == KeyEvent.VK_ENTER) {
				//Calls the startNewTopic method which starts the entered topic.
				String text = newTopicField.getText();
				startNewTopic(text);
			}
		}
	});
	//This adds the listener for the help button. 
	helpButton.addActionListener(new ActionListener() {
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent event){
			//Adds a help message. Not actually helpful.
			JOptionPane.showMessageDialog(null, "Start or Join a Topic for a conversation. \nSend Messages by entering text into the message box." );
		}
	});
	//This adds the listener for leaving a topic.
	leaveTopicButton.addActionListener(new ActionListener() {
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent event) {
			//Resets the conversation window, clears all users, and removes the topic from the users topics.
			conversationWindow.setText("");
			myTopics.remove(currentTopic);
			conversationUsersModel.clear();
			//Sends the leave message to the server, so all users know this user is no longer in the topic.
			PrintWriter out = null;
	    	try {
				out = new PrintWriter(chatSocket.getOutputStream(), true);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	String userInput;	        
	        String SPACE = "$$$";
        	userInput = "leave" + SPACE + currentTopic + SPACE
                    + "username" + SPACE + username;
        	//Remove the topic from the userModel, and reset the current topic to None
        	myModel.delete(currentTopic);
        	currentTopic = "None";
			chatTopicLabel.setText("Chatroom: " + currentTopic);
	        out.println(userInput);
	        out.flush();
			
		}
	});
	
	//This adds a listener for keys pressed in the message box. Sends message with the enter key
	//The other function is to change the user's typing state. 
	conversationMessage.addKeyListener(new KeyAdapter() {
		public void keyPressed(KeyEvent event) {
			if (event.getKeyCode() == KeyEvent.VK_ENTER) {
				//The action for sending a message. 
				sendMessage(conversationMessage.getText());
				userState = "none";
				//Emulate backspace event because the enter key would reset the message box incorrectly. 
				Robot robot;
				try {
					robot = new Robot();
					robot.keyPress(KeyEvent.VK_BACK_SPACE);
				} catch (AWTException e) {
					e.printStackTrace();
				}
				//Stop the timer because the user has no longer entered any text. 
				timer.stop();
			}else if(event.getKeyCode() != KeyEvent.VK_BACK_SPACE || userState.equals("entered text")){
				//This will set the users state to typing and send the message to the server
				//to alert all users. 
				if(!userState.equals("typing")){
					PrintWriter out = null;
					try {
						out = new PrintWriter(chatSocket.getOutputStream(), true);
					} catch (IOException e) {
						e.printStackTrace();
					}
					String userInput;     
			        String SPACE = "$$$";
			        userInput = "userAction" + SPACE + currentTopic + SPACE
			                + "username" + SPACE + username + SPACE + " is typing";
			        out.println(userInput);
			        out.flush();      
			        userState = "typing";
				}
				timer.stop();
				timer.start();
			}
		}
	});		
	}
	
	//Function called when a topic is joined. Sends a message to the server. 
    public void joinTopic(String current) {
    	PrintWriter out = null;
    	try {
			out = new PrintWriter(chatSocket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	String userInput;
        String SPACE = "$$$";
        //If the topic is already in your list of topics, No need to send a message to the server
        //Just retrieve all the information from the users model and the allTopics hashmap for
        //the users in the topic. 
        if(myTopics.contains(current)){
        	conversationUsersModel.clear();
            for(int i = 0; i < allTopics.get(current).size(); i++){
            	conversationUsersModel.addElement(allTopics.get(current).get(i));
            }
            try{
            	conversationWindow.setText(myModel.getText(current));
            }catch(NullPointerException e){
            // Do Nothing. This is here just in case no text exists for
            //the current topic. for instance a topic was just started and
            //someone else joined.
            }   
        }else{
        	//get the topic from the server. Have never joined this topic for so the 
        	//text is not saved in the user model. 
        	userInput = "join" + SPACE + current + SPACE
                    + "username" + SPACE + username;
            out.println(userInput);
            out.flush();
        }
    }
    //This method updates a user who on what topics are available when initially joining the chat room.
    public static void updateUser(String[] tokens, String tempTopic){
    	for(int i = 3; i < tokens.length; i++){
    		if(tokens[i].equals("topic")){
    			i++;
    			String temp = tokens[i];
    			availableTopicsModel.addElement(temp);
    			ArrayList<String> tempList = new ArrayList<String>();
    			i++;
    			if(i < tokens.length){
	    			while(!tokens[i].equals("topic") && !tokens[i].equals("###%%%")){
	    				tempList.add(tokens[i]);
	    				i++;
	    			}
    			}
    			i--;
    			allTopics.put(temp, tempList);
    		}
    	}
    }
    
    //Method that communicates with the server when a new topic is started. 
	public void startNewTopic(String s){
		PrintWriter out = null;
		try {
			out = new PrintWriter(chatSocket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		conversationWindow.setText("");
		String userInput;
        
        String SPACE = "$$$";
        userInput = "topic" + SPACE + s + SPACE
                + "username" + SPACE + username;
        out.println(userInput);
        out.flush();
       
	}
	//Method called that sends a message to the server to be shown to all users. 
	public void sendMessage(String s){
		PrintWriter out = null;
		try {
			out = new PrintWriter(chatSocket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String userInput;     
        String SPACE = "$$$";
        userInput = "messageFromTopic" + SPACE + currentTopic + SPACE
                + "username" + SPACE + username + SPACE + s;
        out.println(userInput);
        out.flush();
        conversationMessage.setText("");
	}
	//Method for connecting to a socket on initialization. 
	public static Socket connect(String hostname, int port){
		try{

	    	chatSocket = new Socket(hostname, port);

	    }catch (UnknownHostException e) {
	    	System.err.println("Don't know about host: " + hostname.toString() + ".");
	    	System.exit(1);
	    }catch  (IOException e){
	    	System.err.println("Couldn't get I/O for "
	                + "the connection to: " + hostname.toString() + ".");
	    	System.exit(1);
	    }
		return chatSocket;
	}	
	//Main method which starts the client and handles input from the server. 
	public static void main(final String[] args) throws Exception {
		//Parses through the args and finds the port and hostname for the connection. 
	    StringBuilder hostname = new StringBuilder();
	    int port;
	    int state = 0;
		if(args.length == 1){
			StringBuilder num = new StringBuilder();
			for(int i = 0; i < args[0].length(); i++){
				char c = args[0].charAt(i);
				if( c == ':'){
					state = 1;
				}else if(state == 0){
					hostname.append(c);
				}else if(state == 1){
					num.append(c);
				}	
			}
			String numS = num.toString();
			port = Integer.parseInt(numS);
		}else{
			throw new Exception();
		}
		chatSocket = null;
	    PrintWriter out = null;
	    BufferedReader in = null;
	    //the hostname and port are passed on to connect to connect to the server. 
	    chatSocket = connect(hostname.toString(), port);
	    out = new PrintWriter(chatSocket.getOutputStream(), true);
    	in = new BufferedReader(new InputStreamReader(chatSocket.getInputStream()));
    	//This method runs on startup for the user to enter his/her username
		while(validUser == false){	
			tempname = JOptionPane.showInputDialog(null, "Enter User Name: \n(must be 10 alphanumeric characters or less)");
			out.print("username$$$" +  tempname + "\n");
			out.flush();
			input = in.readLine();
			//This is the error message in case the username was invalid. 
			if(input.equals("invalid username") || input.equals("invalid command")){
				JOptionPane.showMessageDialog(null, "Error: Invalid Username\nUsername Might Already Exist", "Inane error", JOptionPane.ERROR_MESSAGE );
			//This is when the name was valid. 
			}else{
				username = tempname;
				validUser = true;
			}
		}
		//Now run the main GUI. 
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				ChatGui main = new ChatGui();
				main.setVisible(true);
			}
		});	
		//Start Listening to messages. 
		try {
            String message;
            String output = ""; 
            out.print("username$$$" +  tempname + "\n");
			out.flush();            
            while ((message = in.readLine()) != null) {
            	//Split the input from the server into tokens.
                String[] tokens = message.split("[$]{3}");
                //The server sends out a topic started message. 
                if(tokens[0].equals("topicStarted")){
                	//If it was you who started the topic. 
                	//Update everything necessary for your new topic. 
                	//Similar to joining a topic if your the one starting it. 
                	if(tokens[3].equals(username)){
                		currentTopic = tokens[1];
                		myTopics.add(currentTopic);
                		chatTopicLabel.setText("Chatroom: " + currentTopic);
                		newTopicField.setText("");
                		conversationUsersModel.clear();
                		conversationUsersModel.addElement(username);
                		//add the topic to the usermodel
                		myModel.update(currentTopic , "");
                	}
                	//Every topic needs to be added to the available topics model and the allTopics hashmap
                	ArrayList<String> newTopic = new ArrayList<String>();
                	newTopic.add(tokens[3]);
                	availableTopicsModel.addElement(tokens[1]);
                	allTopics.put(tokens[1], newTopic); 	
                //The server sent a joined Topic message
                }else if(tokens[0].equals("joinedTopic")){
                	//If you were the one who sent the joined topic message meaning you joined a 
                	//new topic and need to update everything. 
                	if(tokens[3].equals(username)){
                		currentTopic = tokens[1];
                		//Check to see if there was any text in the topic when user joined. 
                		if(tokens.length == 5){
                			output = "";
                		}else{
                			output = tokens[5] + "\n";
                		}
                		String text;
                		String line;
                		//Multiple lines can be sent in from a new conversation. 
                		int numLines = Integer.parseInt(tokens[4]) -1;
                		conversationWindow.setText(output);
                		myModel.update(currentTopic, output);
                		//Iterate through each line sent in for the new topic and add to the window
                		//and the userModel.
                		for(int i = 0; i < numLines; i++){
                			line = in.readLine();
                			text = conversationWindow.getText();
                			text += line + "\n";
                			conversationWindow.setText(text);
                			myModel.update(currentTopic, line + "\n");
                		}
                		//Add the topic to your list of topics.
                		myTopics.add(currentTopic);
                		//Check to make sure your username isn't in the allTopics hashmap
                		//This fixed a weird bug I was having, not sure why it works the way it does.
                		if(!allTopics.get(currentTopic).contains(username)){
                			allTopics.get(currentTopic).add(username);
                		}
                		//Update the Users list to display the users in the newly joined topic.
                		conversationUsersModel.clear();
                		for(String sx: allTopics.get(currentTopic)){
                			conversationUsersModel.addElement(sx);
                		}
                	}else{
                		//Someone else joined a topic. Add them to the allTopics hashmap
                		allTopics.get(tokens[1]).add(tokens[3]);
                		//If the user joined your current topic. update the users list to display them.
                		if(tokens[1].equals(currentTopic)){
                			conversationUsersModel.addElement(tokens[3]);
                		}
                	}
                //This is the initial method for a user added. 
                }else if(tokens[0].equals("userAdded")){
                	boolean continuex = false;
                	//Add this to the end of the message to signify the end because this token
                	//list can be endless. 
                	message = message + "$$$###%%%";
                	tokens = message.split("[$]{3}");
                	//Only need to update if the username is for your client
                	if(tokens[2].equals(username)){
                		myModel = new UserModel(username);
	            		if(tokens.length > 4){
	            			//This fixed a weird but when tokens was giving a null pointer exception for
	            			//some reason. 
	            			while(continuex == false){
	            				try{
	            					updateUser(tokens, tokens[4]);
	            					continuex = true;
	            				}catch(NullPointerException e){
	            					//This has caused some weird errors where this code tries to 
	            					//run before the tokens are fully split
	            				} 
	            			}
	    	        		
	            		}
                	}
                //This is for if a user left a message. 
                }else if(tokens[0].equals("userRemoved")){
                	allTopics.get(tokens[1]).remove(tokens[3]);
                	//If the user left your conversation, remove them from the users list. 
                	if(tokens[1].equals(currentTopic)){       		
                		conversationUsersModel.removeElement(tokens[3]);
                	}
                	//If the user who left was the only user in that topic, the topic is deleted.
                	if(allTopics.get(tokens[1]).size() == 0){
                		availableTopicsModel.removeElement(tokens[1]);
                	}
                //A message has been sent by some user
                }else if(tokens[0].equals("messageFromTopic")){
                	output = tokens[4] + "\n";
                	String topic = tokens[1];  
                	//Check if this message was sent to your current topic. 
                	if(topic.equals(currentTopic)){
                        String text = conversationWindow.getText();
                        conversationWindow.setText(text + output);
                        String user = tokens[3];
                        //Show the new users state in the users list. 
                		conversationUsersModel.removeElement(user);
                		conversationUsersModel.removeElement(user + " is typing");
                		conversationUsersModel.removeElement(user + " has entered text");
                		conversationUsersModel.addElement(user);
                	}
                	//add output to the user model for topics that are in myTopics
                	if(myTopics.contains(topic)){
                		myModel.update(topic, output);
                	}
                	//This makes sure that when a new message is added below the window capacity
                	//that the scrollbar goes to the bottom. 
                	conversationWindow.setCaretPosition(conversationWindow.getCaretPosition()+conversationWindow.getText().length());
                //This checks for an invalid topic name. This will give you the invalid topic name dialog box. 
                }else if(tokens[0].equals("username") && tokens[1].equals(username) && tokens[2].equals("invalid topicName")){
                	JOptionPane.showMessageDialog(null, "Error: Invalid Topic Name\nTopic must be between 1 and 29 alpha-numeric characters\nor Topic may already exist", "Inane error", JOptionPane.ERROR_MESSAGE );
                //This updates the user typing state when a message is received fromt the server.
                }else if(tokens[0].equals("userAction")){
                	String user = tokens[3];
                	//Display the user's state in the conversation users list. 
                	if(tokens[1].equals(currentTopic)){
                		conversationUsersModel.removeElement(user);
                		conversationUsersModel.removeElement(user + " is typing");
                		conversationUsersModel.removeElement(user + " has entered text");
                		conversationUsersModel.addElement(user + tokens[4]);
                	}
                }
                else{
                	//Should only get here for invalid messages
                	//That don't actually affect the GUI. Sending a message
                	//With no text is an example. 
                }  
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	

}
