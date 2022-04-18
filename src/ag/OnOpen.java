package ag;

@FunctionalInterface
public interface OnOpen {

    void run(Session session);
}
