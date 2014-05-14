
	   
	package backEnd;

import java.util.ArrayList;

/*
 * Concurrency Strategy for Conv. Model: 
 * Synchronize on the mutable object of this class: users
 */

/**
 * datatype for each conversation to store topic and all messages in the conversation
 * as well as the users if this is necessary
 * @author xolantumy
 *
 */
public class ConversationModel {

    //data members: String topic, String text, List<String> users //A list of the users
    private String topic;
    private String convText= "";
    private ArrayList<String> users= new ArrayList<String>();
    private int numLines = 0;
    
    /**
     * Starts a new conversation by assigning a value to topic.
     * 
     * @param topic, a string of alphanumeric characters the conversation topic in the 
     *               form "Topic: alphanumeric"
     *               
     * @param username, the name of the user
     * 
     * @throws FormatException
     *  
     */
    public ConversationModel (String topic) {
        this.topic = topic;
    }


    /**
     * Joins user to the specified conversation, adds username to list users if topic exists
     * @param username, the name of the user 
     */
    public void joinConversation(String username) {
        users.add(username);
    }
    
    
    /**
     * edits the text of the conversation to include the user’s message
     * 
     * @param text, the whole text of the conversation including all messages that have been sent
     */
    public void setText(String textInput) {
            this.convText+=textInput;
    }
    
    /**
     * edits the number of lines in the text of the conversation
     * 
     * 
     */
    public void increaseLines() {
    	this.numLines++;
    }
    
    /**
     * returns the number of lines in the text of the conversation
     * 
     * @return numLines
     * 
     */
    public int getNumLines() {
    	return numLines;
    }

    
    /**
     * Provides a copy of the conversation text. 
     * 
     * @return clientText The entire conversation text
     */
    public String getText(){
            return convText;
    }
    
    /**
     * Checks if user is in conversation 
     * 
     * @return true if username is a user, false if not.
     */
    public boolean inConversation(String username) {
        for (String u: users) {
            if (username.equals(u)) {
                return true;
            }
        }
        
        return false;
    }

    /**
     * Checks if there are no users in the conversation
     * 
     * @return true if user size is zero, false if not.
     */
    public boolean noConversation() {
    	if (users.size()==0) {
    		return true;
    	}
    	return false;	
    }
    
    /**
     * Leaves the current conversation a user is in. Returns the user to the home screen.
     * @param username, the username of the client.
     * @return the main view window with the list of available conversations.
     */
    public void leaveConversation(String username) {
        users.remove(username);
    }

    
    /**
     * returns conversation topic
     * 
     * @returns topic
     */
    public String getTopic() {
        return topic;
    }
    
    


    /**
     * Method which returns a String containing all of the usernames of users within a
     * specific conversation in the format user1$$$user2$$$user3...
     * 
     * @return stringOfUsers   a string containing all users that are currently
     *                         members of a conversation separated by enter spaces, $$$
     */
    public String getStringUsers(){
            String stringOfUsers="";
            
            for (int usr=0; usr<users.size(); usr++){
            	if (usr != users.size()-1) {
                stringOfUsers += users.get(usr)+ "$$$"; 
            	}
            	else {
            		stringOfUsers += users.get(usr);
            	}
            }
            return stringOfUsers;
    }
    
    /**
     * Method which returns the arraylist users
     * 
     * @return users   arraylist
     */
    public ArrayList<String> getUsers() {
    	return users;
    }
    
    
    
    
    
    

    
}
