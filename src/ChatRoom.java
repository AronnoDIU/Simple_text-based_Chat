import java.util.HashMap;
import java.util.Map;

class ChatRoom {
    // Map of users and their message queues for each chat room
    private final Map<String, MessageQueue> users = new HashMap<>();

    public ChatRoom(String name) {
        users.put(name, new MessageQueue()); // Create a new message queue
    }

    // Methods for adding and removing users and sending messages
    public void addUser(String userName) {
        users.put(userName, new MessageQueue()); // Create a new message queue
    }

    // Remove the user from the chat room and delete their message queue
    public void removeUser(String userName) {
        users.remove(userName); // Remove the user from the chat room
    }

    // Send a message to all users in the chat room except the sender
    public void sendMessage(String sender, String message) {

        // Send the message to all users in the chat room except the sender
        for (Map.Entry<String, MessageQueue> entry : users.entrySet()) {
            String receiver = entry.getKey(); // Get the receiver's name from the entry

            // If the receiver is not the sender, send the message to the receiver
            if (!sender.equals(receiver)) {

                // Enqueue the message for the receiver
                entry.getValue().enqueue(sender + ": " + message);
            }
        }
    }

    // Receive a message from the user and return it
    public String receiveMessage(String userName) {
        return users.get(userName).dequeue(); // Get the message from the user and return it
    }
}