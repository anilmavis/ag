package ag;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author anil
 */
public class ClientEndpoint extends Endpoint {

    private final String host;

    /**
     * {@inheritDoc}
     */
    public ClientEndpoint(String host, int port) throws IOException {
        super(port);
        this.host = host;
    }

    /**
     *
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onOpen(Session session) {
        super.onOpen(session);
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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void open() throws IOException {
        onOpen(new Session(this, new Socket(host, getPort())));
    }
}
