package ag;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

/**
 *
 * @author anil
 */
public class Session {

    private final UUID id;
    private final Endpoint endpoint;
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    /**
     *
     * @param endpoint the endpoint
     * @param socket the socket
     * @throws IOException
     */
    public Session(Endpoint endpoint, Socket socket) throws IOException {
        id = UUID.randomUUID();
        this.endpoint = endpoint;
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    /**
     *
     * @return the id
     */
    public UUID getId() {
        return id;
    }

    /**
     *
     * @return the endpoint
     */
    public Endpoint getEndpoint() {
        return endpoint;
    }

    /**
     *
     * @return the socket
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     *
     * @return the output
     */
    public ObjectOutputStream getOut() {
        return out;
    }

    /**
     *
     * @return the input
     */
    public ObjectInputStream getIn() {
        return in;
    }

    /**
     *
     * @return the remote address
     */
    public String getRemoteAddress() {
        return socket.getRemoteSocketAddress().toString();
    }

    /**
     * closes the socket
     *
     * @throws IOException
     */
    public void close() throws IOException {
        socket.close();
    }
}
