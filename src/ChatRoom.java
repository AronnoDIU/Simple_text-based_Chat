import java.util.HashMap;
import java.util.Map;

class ChatRoom {
    private final Map<String, MessageQueue> users = new HashMap<>();

    public ChatRoom(String name) {
        users.put(name, new MessageQueue());
    }

    public void addUser(String userName) {
        users.put(userName, new MessageQueue());
    }

    public void removeUser(String userName) {
        users.remove(userName);
    }

    public void sendMessage(String sender, String message) {
        for (Map.Entry<String, MessageQueue> entry : users.entrySet()) {
            String receiver = entry.getKey();
            if (!sender.equals(receiver)) {
                entry.getValue().enqueue(sender + ": " + message);
            }
        }
    }

    public String receiveMessage(String userName) {
        return users.get(userName).dequeue();
    }
}