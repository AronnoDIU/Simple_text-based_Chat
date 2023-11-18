import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class SimpleChatApp {
    private static final Logger logger = Logger.getLogger(SimpleChatApp.class.getName());
    private static final Map<String, ChatRoom> chatRooms = new HashMap<>(); // Map of chat rooms
    private static final Scanner userInput = new Scanner(System.in);

    public static void main(String[] args) {
        logger.info("Welcome to the Simple Chat App!"); // Print welcome message

        // Start a new thread to receive messages from the server and print them
        while (true) {
            logger.info("1. Create a Chat Room"); // Print menu options
            logger.info("2. Join a Chat Room");
            logger.info("3. Exit");

            int choice = userInput.nextInt();
            userInput.nextLine(); // Consume the newline character

            switch (choice) {
                case 1: // For Create a new chat room
                    createChatRoom();
                    break;
                case 2: // For Join an existing chat room
                    joinChatRoom();
                    break;
                case 3: // For Exit the application
                    logger.info("Exiting the Chat App. Goodbye!");
                    System.exit(0);
                default: // For Invalid choice
                    logger.warning("Invalid choice. Please try again.");
            }
        }
    }

    // Methods for creating and joining chat rooms
    private static void createChatRoom() {
        logger.info("Enter the name of the new Chat Room:");
        String roomName = userInput.nextLine(); // Get the name of the chat room

        // Check if the chat room already exists and warn the user if it does
        if (!chatRooms.containsKey(roomName)) {
            ChatRoom chatRoom = new ChatRoom(roomName); // Create a new chat room
            chatRooms.put(roomName, chatRoom); // Add the chat room to the map

            logger.info("Chat Room '" + roomName + "' created successfully!");

        } else { // If the chat room already exists
            logger.warning("Chat Room '" + roomName
                    + "' already exists. Please choose a different name.");
        }
    }

    // Methods for joining chat rooms and sending messages
    private static void joinChatRoom() {
        logger.info("Enter your name:");
        String userName = userInput.nextLine();

        logger.info("Enter the name of the Chat Room you want to join:");
        String roomName = userInput.nextLine();

        // Check if the chat room exists and warn the user if it does
        if (chatRooms.containsKey(roomName)) {
            ChatRoom chatRoom = chatRooms.get(roomName); // Get the chat room from the map
            chatRoom.addUser(userName); // Add the user to the chat room

            // Start a new thread to receive messages from the chat room and print them
            new Thread(() -> { // Start a new thread
                while (true) {
                    // Get the message from the chat room
                    String message = chatRoom.receiveMessage(userName);
                    logger.info(roomName + " - " + userName + ": " + message);
                }
            }).start(); // Start the thread

            // Print that the user joined the chat room
            logger.info("Joined the Chat Room '" + roomName + "'.");
            sendMessageLoop(userName, chatRoom); // Start the scent message loop
        } else {
            // If the chat room does not exist and warn the user
            logger.warning("Chat Room '" + roomName
                    + "' does not exist. Please enter a valid room name.");
        }
    }

    // Methods for sending and receiving messages in a chat room
    private static void sendMessageLoop(String userName, ChatRoom chatRoom) {
        while (true) {
            logger.info("Type your message (or type 'exit' to leave the Chat Room):");
            String message = userInput.nextLine(); // Get the message from the user

            // Check if the user wants to exit the chat room or send the message
            if (message.equalsIgnoreCase("exit")) {
                chatRoom.removeUser(userName); // Remove the user from the chat room
                logger.info("You have left the Chat Room.");
                break;
            }

            // Send the message to the chat room and print it
            chatRoom.sendMessage(userName, message);
        }
    }
}