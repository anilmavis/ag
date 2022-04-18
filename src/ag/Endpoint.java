package ag;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public abstract class Endpoint {

    private static final int N_THREADS = 4;
    private final int port;
    private final Executor executor;
    private OnOpen onOpen;
    private OnMessage onMessage;
    private OnClose onClose;

    public Endpoint(int port) {
        this.port = port;
        executor = Executors.newFixedThreadPool(N_THREADS);
    }

    public int getPort() {
        return port;
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setOnOpen(OnOpen onOpen) {
        this.onOpen = onOpen;
    }

    public void setOnMessage(OnMessage onMessage) {
        this.onMessage = onMessage;
    }

    public void setOnClose(OnClose onClose) {
        this.onClose = onClose;
    }

    public void onOpen(Session session) {
        onOpen.run(session);
    }

    public void onMessage(Session session, Message message) {
        onMessage.run(session, message);
    }

    public void onClose(Session session) {
        onClose.run(session);
    }

    public abstract void open() throws IOException;

    public void send(Session session, Message message) throws IOException {
        session.getOut().writeObject(message);
    }

    public abstract void send(Message message) throws IOException;

    public abstract void close() throws IOException;
}
