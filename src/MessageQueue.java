import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

class MessageQueue {
    private final Object lock = new Object();
    private static final Logger logger = Logger.getLogger(MessageQueue.class.getName());
    private final Queue<String> messages = new LinkedList<>();

    public void enqueue(String message) {
        synchronized (lock) {
            messages.add(message);
            lock.notify();
        }
    }

    public String dequeue() {
        synchronized (lock) {
            while (messages.isEmpty()) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    logger.log(Level.SEVERE, "Exception occurred while waiting", e);
                }
            }
            return messages.poll();
        }
    }
}