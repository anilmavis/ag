package ag;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientEndpoint extends Endpoint {

    private final String host;
    private Session session;

    public ClientEndpoint(String host, int port) throws IOException {
        super(port);
        this.host = host;
    }

    public ClientEndpoint(int port) throws IOException {
        this("127.0.0.1", port);
    }

    public String getHost() {
        return host;
    }

    public Session getSession() {
        return session;
    }

    @Override
    public void onOpen(Session session) {
        this.session = session;
        Logger.getLogger(ClientEndpoint.class.getName()).log(Level.INFO, "{0}", session.getRemoteAddress());
    }

    @Override
    public void onMessage(Session session, Message message) {
        Logger.getLogger(ClientEndpoint.class.getName()).log(Level.INFO, "{0}: {1}", new Object[]{session.getRemoteAddress(), message});
    }

    @Override
    public void onClose(Session session) {
        Logger.getLogger(ClientEndpoint.class.getName()).log(Level.INFO, "{0}", session.getRemoteAddress());
    }

    @Override
    public void open() throws IOException {
        onOpen(new Session(this, new Socket(host, getPort())));
    }

    @Override
    public void send(Message message) throws IOException {
        send(session, message);
    }

    @Override
    public void close() throws IOException {
        session.close();
    }
}
