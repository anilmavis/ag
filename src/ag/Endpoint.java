package ag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anil
 */
public abstract class Endpoint {

    private static final int N_THREADS = 4;
    private final int port;
    private final Executor executor;
    private final List<Session> sessions;
    private OnOpen onOpen;
    private OnMessage onMessage;
    private OnClose onClose;

    /**
     *
     * @param port the port
     */
    public Endpoint(int port) {
        this.port = port;
        executor = Executors.newFixedThreadPool(N_THREADS);
        sessions = Collections.synchronizedList(new ArrayList<>());
    }

    /**
     *
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     *
     * @return the executor
     */
    public Executor getExecutor() {
        return executor;
    }

    /**
     *
     * @return the sessions
     */
    public List<Session> getSessions() {
        return sessions;
    }

    /**
     *
     * @param session the session to be added
     */
    public synchronized void addSession(Session session) {
        getSessions().add(session);
    }

    /**
     *
     * @param session the session to be removed
     */
    public synchronized void removeSession(Session session) {
        getSessions().remove(session);
    }

    /**
     *
     * @param onOpen the method to be called
     */
    public void setOnOpen(OnOpen onOpen) {
        this.onOpen = onOpen;
    }

    /**
     *
     * @param onMessage the method to be called
     */
    public void setOnMessage(OnMessage onMessage) {
        this.onMessage = onMessage;
    }

    /**
     *
     * @param onClose the method to be called
     */
    public void setOnClose(OnClose onClose) {
        this.onClose = onClose;
    }

    /**
     *
     * @param session the opened session
     */
    public void onOpen(Session session) {
        Logger.getLogger(Endpoint.class.getName()).log(Level.INFO, "{0}", session.getRemoteAddress());
        onOpen.run(session);
    }

    /**
     *
     * @param session the sender
     * @param message the received message
     */
    public void onMessage(Session session, Message message) {
        Logger.getLogger(Endpoint.class.getName()).log(Level.INFO, "{0}: {1}", new Object[]{session.getRemoteAddress(), message});
        onMessage.run(session, message);
    }

    /**
     *
     * @param session the closed session
     */
    public void onClose(Session session) {
        Logger.getLogger(Endpoint.class.getName()).log(Level.INFO, "{0}", session.getRemoteAddress());
        onClose.run(session);
    }

    /**
     * opens this endpoint
     *
     * @throws IOException
     */
    public abstract void open() throws IOException;

    /**
     *
     * @param session the receiver
     * @param message the message to be sent
     * @throws IOException
     */
    public void send(Session session, Message message) throws IOException {
        session.getOut().writeObject(message);
    }

    /**
     *
     * @param message the message to be sent
     * @throws IOException
     */
    public synchronized void send(Message message) throws IOException {
        for (Session session : getSessions()) {
            send(session, message);
        }
    }

    ;

    /**
     * closes the sessions
     * @throws IOException
     */
    public synchronized void close() throws IOException {
        for (Session session : getSessions()) {
            session.close();
        }
    }
;
}
