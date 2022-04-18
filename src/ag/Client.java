package ag;

import java.io.IOException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        ClientEndpoint endpoint = new ClientEndpoint(8080);
        endpoint.open();
        Scanner scanner = new Scanner(System.in);
        String line;

        while (!(line = scanner.nextLine()).equals("close")) {
            Message message = new Message();
            message.put("hello", line);
            endpoint.send(message);
        }
        endpoint.close();
    }
}
