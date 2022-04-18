package ag;

import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anil
 */
public class ServerEndpoint extends Endpoint {

    private final ServerSocket socket;

    /**
     * {@inheritDoc}
     */
    public ServerEndpoint(int port) throws IOException {
        super(port);
        socket = new ServerSocket(port);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onOpen(Session session) {
        super.onOpen(session);
        addSession(session);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onMessage(Session session, Message message) {
        super.onMessage(session, message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClose(Session session) {
        super.onClose(session);
        removeSession(session);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void open() {
        getExecutor().execute(() -> {
            while (true) {
                try {
                    Session session = new Session(ServerEndpoint.this, socket.accept());
                    new Thread(() -> {
                        session.getEndpoint().onOpen(session);

                        while (true) {
                            try {
                                session.getEndpoint().onMessage(session, (Message) session.getIn().readObject());
                            } catch (EOFException e) {
                                session.getEndpoint().onClose(session);
                                break;
                            } catch (IOException | ClassNotFoundException e) {
                                Logger.getLogger(ServerEndpoint.class.getName()).log(Level.SEVERE, null, e);
                            }
                        }
                    }).start();
                } catch (IOException e) {
                    Logger.getLogger(ServerEndpoint.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        });
    }

    /**
     * {@inheritDoc} closes the socket
     */
    @Override
    public void close() throws IOException {
        super.close();
        socket.close();
    }
}
