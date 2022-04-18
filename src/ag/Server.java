package ag;

import java.io.IOException;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerEndpoint endpoint = new ServerEndpoint(8080);
        endpoint.setOnOpen((session) -> {
            System.out.println("opened");
        });
        endpoint.setOnMessage((session, message) -> {
            System.out.println(session.getRemoteAddress() + ": " + message.get("hello"));
        });
        endpoint.setOnClose((session) -> {
            System.out.println("closed");
        });
        endpoint.open();
    }
}
