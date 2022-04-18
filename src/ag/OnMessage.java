package ag;

/**
 *
 * @author anil
 */
@FunctionalInterface
public interface OnMessage {

    /**
     *
     * @param session the sender
     * @param message the received message
     */
    void run(Session session, Message message);
}
