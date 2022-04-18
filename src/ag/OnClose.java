package ag;

@FunctionalInterface
public interface OnClose {

    void run(Session session);
}
