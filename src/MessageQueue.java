import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level; // For logging exceptions and warnings
import java.util.logging.Logger;

class MessageQueue {
    private final Object lock = new Object(); // Lock object for synchronizing access to the queue
    private static final Logger logger = Logger.getLogger(MessageQueue.class.getName());
    private final Queue<String> messages = new LinkedList<>(); // Queue of messages to be sent

    public void enqueue(String message) {
        synchronized (lock) { // Synchronize access to the queue using the lock object
            messages.add(message); // Add the message to the queue
            lock.notify(); // Notify any waiting threads
        }
    }

    // Dequeue a message from the queue and return it
    public String dequeue() {
        synchronized (lock) { // Synchronize access to the queue using the lock object

            // Wait until there are messages in the queue or an exception is thrown
            while (messages.isEmpty()) {
                try {
                    lock.wait(); // Wait for a notification or an exception
                } catch (InterruptedException e) { // If an exception is thrown
                    logger.log(Level.SEVERE, "Exception occurred while waiting", e);
                }
            }
            return messages.poll(); // Dequeue a message from the queue and return it
        }
    }
}