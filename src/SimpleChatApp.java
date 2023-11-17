import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class SimpleChatApp {
    private static final Logger logger = Logger.getLogger(SimpleChatApp.class.getName());
    private static final Map<String, ChatRoom> chatRooms = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        logger.info("Welcome to the Simple Chat App!");

        while (true) {
            logger.info("1. Create a Chat Room");
            logger.info("2. Join a Chat Room");
            logger.info("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    createChatRoom();
                    break;
                case 2:
                    joinChatRoom();
                    break;
                case 3:
                    logger.info("Exiting the Chat App. Goodbye!");
                    System.exit(0);
                default:
                    logger.warning("Invalid choice. Please try again.");
            }
        }
    }

    private static void createChatRoom() {
        logger.info("Enter the name of the new Chat Room:");
        String roomName = scanner.nextLine();

        if (!chatRooms.containsKey(roomName)) {
            ChatRoom chatRoom = new ChatRoom(roomName);
            chatRooms.put(roomName, chatRoom);
            logger.info("Chat Room '" + roomName + "' created successfully!");
        } else {
            logger.warning("Chat Room '" + roomName + "' already exists. Please choose a different name.");
        }
    }

    private static void joinChatRoom() {
        logger.info("Enter your name:");
        String userName = scanner.nextLine();

        logger.info("Enter the name of the Chat Room you want to join:");
        String roomName = scanner.nextLine();

        if (chatRooms.containsKey(roomName)) {
            ChatRoom chatRoom = chatRooms.get(roomName);
            chatRoom.addUser(userName);

            new Thread(() -> {
                while (true) {
                    String message = chatRoom.receiveMessage(userName);
                    logger.info(roomName + " - " + userName + ": " + message);
                }
            }).start();

            logger.info("Joined the Chat Room '" + roomName + "'.");
            sendMessageLoop(userName, chatRoom);
        } else {
            logger.warning("Chat Room '" + roomName + "' does not exist. Please enter a valid room name.");
        }
    }

    private static void sendMessageLoop(String userName, ChatRoom chatRoom) {
        while (true) {
            logger.info("Type your message (or type 'exit' to leave the Chat Room):");
            String message = scanner.nextLine();

            if (message.equalsIgnoreCase("exit")) {
                chatRoom.removeUser(userName);
                logger.info("You have left the Chat Room.");
                break;
            }

            chatRoom.sendMessage(userName, message);
        }
    }
}