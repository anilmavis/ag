package ag;

@FunctionalInterface
public interface OnMessage {

    void run(Session session, Message message);
}
