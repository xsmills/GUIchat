package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import backEnd.ConversationModel;

/**
 * Chat server runner.
 * 
 */
public class Server {

	/**
	 * Start a chat server.
	 */


	private final static int PORT = 4444;
	private ServerSocket serverSocket;
	private HashMap<String, ConversationModel> conversationMap = new HashMap<String, ConversationModel>();
	public String conversationText = "";
	private ArrayList<Socket> socketList = new ArrayList<Socket>();
	private ArrayList<String> allUsers = new ArrayList<String>();

	/**
	 * Make a server that listens for connections on port.
	 * 
	 * @param port
	 *            port number, requires 0 <= port <= 65535.
	 * @throws IOException 
	 */
	public Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}
	
	/**
	 * returns the conversationMap
	 * 
	 * @return conversationMap
	 * 
	 */
	public HashMap<String, ConversationModel> getConvoMap() {
		return conversationMap;
	}
	

	/**
	 * starts a single thread which will handle input arriving from all clients
	 * 
	 * @param the
	 *            socket object returned from serverSocket.accept()
	 * 
	 */
	public void spawnThread(final Socket socket) {

		Thread t = new Thread(new Runnable() {
			public void run() {

				String output = null;
				try {

					BufferedReader in = new BufferedReader(
							new InputStreamReader(socket.getInputStream()));

					//read input from clients and handle various requests
					for (String line = in.readLine(); line != null; line = in
							.readLine()) {
						output = handleRequest(line);

						//send output to all clients only if they are connected
						for (Socket s : socketList) {
							if (s.isConnected()) {
								if (output != null) {
									PrintWriter outS = new PrintWriter(s
											.getOutputStream(), true);
									outS.println(output);
								}
							}
							
						
						}
					}

				} catch (Exception e) {
					System.out.println(e);
				}

			}
		});

		t.start();
	}

	/**
	 * Run the server, listening for multiple client connections and handling
	 * them. Never returns unless an exception is thrown.
	 * Add all sockets which connect to socketList
	 * 
	 * @throws IOException
	 *             if the main server socket is broken (IOExceptions from
	 *             individual clients do *not* terminate serve()).
	 */
	public void serve() throws IOException {

		boolean listening = true;

		while (listening) {
			Socket s = serverSocket.accept();
			socketList.add(s);
			spawnThread(s);
		}


		serverSocket.close();

	}



	/**
	 * Handler for client input
	 * 
	 * Make requested mutations on game state if applicable. Returns appropriate
	 * message to the user
	 * 
	 * @param input
	 *            a username, topic, join, leave, help, or conversation message
	 *            with the needed username attached. If the type is format is
	 *            incorrect, a message is sent to the client
	 * 
	 *           
	 * 
	 * @return message a string telling the client if an invalid
	 */
	public synchronized String handleRequest(String input) {

		String output = null;
		String SPACE = "$$$";
		String[] tokens = input.split("[$]{3}");


		if (tokens[0].equals("topic") && tokens.length == 4) {
			String topicName = "[A-Z 0-9a-z]{1,29}";
			if (!tokens[1].matches(topicName) || conversationMap.containsKey(tokens[1])) {
				output = "username" + SPACE + tokens[3] + SPACE
						+ "invalid topicName";
			} else {
				ConversationModel convo = new ConversationModel(tokens[1]);
				conversationMap.put(convo.getTopic(), convo);
				convo.joinConversation(tokens[3]);
				output = "topicStarted" + SPACE + tokens[1] + SPACE
						+ "username" + SPACE + tokens[3];
			}
			return output;
		}

		else if (tokens[0].equals("join") && tokens.length == 4) {
			
			if (conversationMap.containsKey(tokens[1])) {
					ConversationModel convo = conversationMap.get(tokens[1]);
					convo.joinConversation(tokens[3]);
					int numLines = convo.getNumLines();
					output = "joinedTopic" + SPACE + tokens[1] + SPACE
							+ "username" + SPACE + tokens[3] + SPACE + numLines +SPACE 
							+ convo.getText();
					return output;
				}

			else {
			output = "username" + SPACE + tokens[3] + SPACE
					+ "non-existent topic";
			return output;
			}
		}

		else if (tokens[0].equals("username") && tokens.length == 2) {
			String userName = "[A-Z_0-9a-z]{1,10}";
			if (tokens[1].matches(userName) && !allUsers.contains(tokens[1])) {
				allUsers.add(tokens[1]);
				output = "userAdded" + SPACE + "username" + SPACE + tokens[1];
				Iterator<String> keys = conversationMap.keySet().iterator();
				String topicList = "";
				while (keys.hasNext()) {
					String topic = keys.next();
					if (!conversationMap.get(topic).noConversation())
						topicList += SPACE + "topic" + SPACE + topic + SPACE
							+ conversationMap.get(topic).getStringUsers();
				}
				output += topicList;
			}

			else {
				output = "invalid username";
			}
			return output;
		}

		else if (tokens[0].equals("messageFromTopic") && tokens.length == 5) {
				if (conversationMap.containsKey(tokens[1])) {
					ConversationModel convo = conversationMap.get(tokens[1]);
					if (convo.inConversation(tokens[3])) {
						String text = tokens[3] + ": " + tokens[4] + "\n";
						convo.increaseLines();
						convo.setText(text);
						output = "messageFromTopic" + SPACE + tokens[1] + SPACE
								+ "username" + SPACE + tokens[3] + SPACE
								+ tokens[3] + ": " + tokens[4];
						
	
						return output;
					} else {
						output = "username" + SPACE + tokens[3] + SPACE
								+ "non-existent user";
						return output;
					}
				}

				else {

			output = "username" + SPACE + tokens[3] + SPACE
					+ "non-existent topic";

			return output;
				}
		}

		else if (tokens[0].equals("leave") && tokens.length == 4) {
				if (conversationMap.containsKey(tokens[1])) {
					ConversationModel convo = conversationMap.get(tokens[1]);
					if (convo.inConversation(tokens[3])) {
						convo.leaveConversation(tokens[3]);
						output = "userRemoved" + SPACE + tokens[1] + SPACE
								+ "username" + SPACE + tokens[3];
						return output;
					} else {
						output = "username" + SPACE + tokens[3] + SPACE
								+ "non-existent user";
						return output;
					}
				}

				else {
			output = "username" + SPACE + tokens[3] + SPACE
					+ "non-existent topic";
			return output;
				}
		}

		else if (tokens[0].equals("userAction") && tokens.length == 5) {
				if (conversationMap.containsKey(tokens[1])) {
					ConversationModel convo = conversationMap.get(tokens[1]);
					if (convo.inConversation(tokens[3])) {
						output = "userAction" + SPACE + tokens[1] + SPACE
								+ "username" + SPACE + tokens[3] + SPACE
								+ tokens[4];
						return output;
					} else {
						output = "username" + SPACE + tokens[3] + SPACE
								+ "non-existent user";
						return output;
					}
				}

				else {
			output = "username" + SPACE + tokens[3] + SPACE
					+ "non-existent topic";
			return output;
				}
		}
		
		else if (tokens[0].equals("disconnect") && tokens.length == 2) {
			allUsers.remove(tokens[1]);
			output = "";
			return output;
		}
		

		else {
			output = "invalid command";
			return output;
		}

	}

	/**
	 * Start server running on the default port.
	 * 
	 */
	public static void main(String[] args) {

		try {
			Server server = new Server(PORT);
			server.serve();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
