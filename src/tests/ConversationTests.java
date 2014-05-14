package tests;

import org.junit.Test;

import backEnd.*;

public class ConversationTests{
   
   
   @Test(expected=AssertionError.class)
   public void testAssert(){
       assert false;
   }
   
   //CONVERSATION MODEL TESTS
   
   /*
    * Tests all of the basic methods of the conversation model
    */
   @Test
   public void basicCMTest() {
       ConversationModel cnvM= new ConversationModel("newConv1");
       //Join
       cnvM.joinConversation("Adwoa");
       assert(cnvM.getStringUsers().contains("Adwoa"));
       //Leave
       cnvM.leaveConversation("Adwoa");
       assert(cnvM.getStringUsers().equals(""));
       //Set Text and Get Text
       cnvM.setText("Adwoa: Messagemessagemessage\n");
       assert(cnvM.getText().equals("Adwoa: Messagemessagemessage\n"));
   }

   /*
    * Ensures that multiple conversation models do not mutate each other's state.
    */
   @Test
   public void multipleCMTest() {
       ConversationModel cnvM1= new ConversationModel("newConv1");
       ConversationModel cnvM2= new ConversationModel("newConv2");
       ConversationModel cnvM3= new ConversationModel("newConv3");

       cnvM1.joinConversation("Adwoa");
       cnvM2.joinConversation("Adwoa");
       cnvM3.joinConversation("Adwoa");

       //Send messages to CMs
       cnvM1.setText("Adwoa: First conv1 message\n");
       cnvM2.setText("Adwoa: First conv2 message\n");
       cnvM3.setText("Adwoa: First conv3 message\n");

       cnvM2.setText("Adwoa: Second conv2 message\n");

       assert(cnvM2.getText().equals("Adwoa: First conv2 message\nAdwoa: Second conv2 message\n") &&
               cnvM1.getText().equals("Adwoa: First conv1 message\n"));
   }

   /*
    * Shows that the same user can join multiple conversation models. Shows that
    * multiple users can be members of one conversation
    */
   @Test
   public void multipleUsersCMTest() {
       ConversationModel cnvM1= new ConversationModel("newConv1");
       ConversationModel cnvM2= new ConversationModel("newConv2");
       ConversationModel cnvM3= new ConversationModel("newConv3");

       cnvM1.joinConversation("Adwoa");
       cnvM1.joinConversation("Randy1");
       cnvM2.joinConversation("Adwoa");
       cnvM3.joinConversation("Adwoa");
       cnvM1.joinConversation("Randy2");

       assert(cnvM1.getStringUsers().equals("Adwoa$$$Randy1$$$Randy2"));
   }

   /*
    * Shows that multiple users can add to the conversation text
    */
   @Test
   public void multipleUsersConvCMTest() {
       ConversationModel cnvM1= new ConversationModel("newConv1");

       cnvM1.joinConversation("Adwoa");
       cnvM1.joinConversation("Randy1");
       cnvM1.joinConversation("Randy2");

       cnvM1.setText("Adwoa: blah blah blah\n");
       cnvM1.setText("Randy1: blah blah blah\n");
       cnvM1.setText("Adwoa: blah blah blah\n");
       cnvM1.setText("Randy2: blah blah blah\n");
       cnvM1.setText("Randy2: blah blah blah\n");
       
       assert(cnvM1.getText().equals("Adwoa: blah blah blah\nRandy1: blah blah blah\nAdwoa: blah blah blah\n" +
               "Randy2: blah blah blah\nRandy2: blah blah blah\n"));
   }

   //USER MODEL TESTS
   
   /*
    * Tests the basic functions of the User Model
    */
   @Test
   public void basicUMTest() {
       UserModel randy1= new UserModel("Randy1");
       ConversationModel cnvM1= new ConversationModel("newConv1");        
       cnvM1.joinConversation("Randy1");

       
       randy1.update("newConv1", "Randy1: Randy Neuman, amazing singer!");

       assert(randy1.getText("newConv1").equals("Randy1: Randy Neuman, amazing singer!"));
   }

   //COMPLETE CONVERSATION: COMBINED MODELS
   
   /*
    * Simulates how multiple users update their own UserModel text during a conversation
    */
   @Test
   public void basicMultipleUMTest() {
       UserModel adwoa= new UserModel("Adwoa");
       UserModel xola= new UserModel("Xola");
       UserModel sean= new UserModel("Sean");
       
       ConversationModel cnvM1= new ConversationModel("newConv1");
       ConversationModel cnvM2= new ConversationModel("newConv2");
       

       cnvM1.joinConversation("Adwoa");
       cnvM1.joinConversation("Xola");
       cnvM1.joinConversation("Sean");
       
       cnvM1.setText("Xola: hey what's up!\n");
       
       //Because we normally run the Usermodel in one thread the shared state of
       //the UM, the conversationText Map is not revealed.
       //To simulate a full blown conversation we will access the UserModel class
       //ansd update it instead of individual users.
       UserModel.update("cnvM1", "Xola: hey what's up!\n");
       
       cnvM1.setText("Adwoa: Not much :) \n");
       UserModel.update("cnvM1", "Adwoa: Not much :) \n");
       
       cnvM2.joinConversation("Sean");
       
       
       //Leaving a Conversation while remaining a member of another
       sean.delete("newConv2");
       cnvM2.leaveConversation("Sean");
       assert(cnvM2.getStringUsers().equals(""));

       //Checking cnvM1 membership
       assert(cnvM1.getStringUsers().contains("Adwoa") && cnvM1.getStringUsers().contains("Xola")
               && cnvM1.getStringUsers().contains("Sean"));
       
       //Checking text stored in User Models
       assert(adwoa.getText("cnvM1").equals("Xola: hey what's up!\nAdwoa: Not much :) \n"));
   }
}