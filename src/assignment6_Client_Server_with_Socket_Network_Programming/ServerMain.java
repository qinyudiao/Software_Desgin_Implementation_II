package assignment6_Client_Server_with_Socket_Network_Programming;

public class ServerMain {
	
	private int port;
	ServerMain(){
		port = 6188;
	}
    public static void main(String[] args) {
    	ServerMain sm = new ServerMain();
        Server server = new Server(sm.getPortNumber());
        server.start();
    }
    public int getPortNumber() {
    	return port;
    }
}
