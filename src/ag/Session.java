package ag;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Session {

    private final Endpoint endpoint;
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    public Session(Endpoint endpoint, Socket socket) throws IOException {
        this.endpoint = endpoint;
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public String getRemoteAddress() {
        return socket.getRemoteSocketAddress().toString();
    }

    public void close() throws IOException {
        socket.close();
    }
}
