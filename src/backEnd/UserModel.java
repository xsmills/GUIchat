
package backEnd;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * User datatype to store the name of the user and other details
 * which might be necessary. Receives all messages added to a conversation
 * //TODO talk about synchronizing this
 * @author xolantumy
 *
 */

public class UserModel {
    
    private String userName;
    private static HashMap<String, StringBuilder> conversationText= new HashMap<String, StringBuilder>();
    
    public UserModel(String username){
        setUserName(username);
    }
    
    public String getUserName() {
        return userName;
    }
    
    
    public void setUserName(String u) {
        userName = u;
    }
    
    

    /**
     * Update the user model with messages in the order in which they are received
     * Append specific message to respective Conversation Model
     * 
     * @param topic     a string that is the topic name of the topic the user is currently a member of
     * TODO  make sure there is error checking within the conv model so illegal calls to topics never occur
     * @param message   the text that a new user has added. In the format "username: messageText"
     */
     public static void update (String topic, String message){
         if (conversationText.containsKey(topic)){
             conversationText.get(topic).append(message);
         }else{
        	 StringBuilder conversationMessage = new StringBuilder(message);
             conversationText.put(topic, conversationMessage);
         }
     }


    
    /**
    * After a conversation is rejoined, this will spit out the conversation text
    * 
    * @param    topic       topic for which the UM conversation text is desired
    * @return   convText    conversation text stored in the UM for topic, topic
    */
    public static String getText (String topic){
        return conversationText.get(topic).toString();
    }


    /**
    * If a user leaves a conversation the UserModel will delete the stringBuilder that 
    * contained all of the user 
     * @return 
    */
    public static void delete(String topic){
        conversationText.remove(topic);
    }
    
    
}
