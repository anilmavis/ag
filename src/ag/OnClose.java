package ag;

/**
 *
 * @author anil
 */
@FunctionalInterface
public interface OnClose {

    /**
     *
     * @param session the closed session
     */
    void run(Session session);
}
