package ag;

import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerEndpoint extends Endpoint {

    private final ServerSocket socket;
    private final List<Session> sessions;

    public ServerEndpoint(int port) throws IOException {
        super(port);
        socket = new ServerSocket(port);
        sessions = Collections.synchronizedList(new ArrayList<>());
        Logger.getLogger(ServerEndpoint.class.getName()).log(Level.INFO, "{0}", socket.getLocalSocketAddress());
    }

    @Override
    public synchronized void onOpen(Session session) {
        super.onOpen(session);
        sessions.add(session);
        Logger.getLogger(ServerEndpoint.class.getName()).log(Level.INFO, "{0}", session.getRemoteAddress());
    }

    @Override
    public void onMessage(Session session, Message message) {
        super.onMessage(session, message);
        Logger.getLogger(ServerEndpoint.class.getName()).log(Level.INFO, "{0}: {1}", new Object[]{session.getRemoteAddress(), message});
    }

    @Override
    public synchronized void onClose(Session session) {
        super.onClose(session);
        sessions.remove(session);
        Logger.getLogger(ServerEndpoint.class.getName()).log(Level.INFO, "{0}", session.getRemoteAddress());
    }

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

    @Override
    public synchronized void send(Message message) throws IOException {
        for (Session session : sessions) {
            send(session, message);
        }
    }

    @Override
    public synchronized void close() throws IOException {
        try (socket) {
            for (Session session : sessions) {
                session.close();
            }
        }
    }
}
