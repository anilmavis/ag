package ag.example;

import ag.ClientEndpoint;
import ag.Message;
import java.io.IOException;

/**
 *
 * @author anil
 */
public class Client {

    /**
     *
     * @param args the arguments
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ClientEndpoint endpoint = new ClientEndpoint("127.0.0.1", 8080);
        endpoint.open();
        Message message = new Message();
        message.put("hello", "world");
        endpoint.send(message);
        endpoint.close();
    }
}
