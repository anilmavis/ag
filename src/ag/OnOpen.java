package ag;

/**
 *
 * @author anil
 */
@FunctionalInterface
public interface OnOpen {

    /**
     *
     * @param session the opened session
     */
    void run(Session session);
}
