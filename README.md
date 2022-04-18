# ag
networking library
## server
`ServerEndpoint endpoint = new ServerEndpoint(8080);
endpoint.setOnOpen((session) -> {
    System.out.println("opened");
});
endpoint.setOnMessage((session, message) -> {
    System.out.println(session.getRemoteAddress() + ": " + message.get("hello"));
});
endpoint.setOnClose((session) -> {
    System.out.println("closed");
});
endpoint.open();`
## client
`ClientEndpoint endpoint = new ClientEndpoint(8080);
endpoint.open();
Message message = new Message("hello", "world");
endpoint.send(message);
endpoint.close();`
