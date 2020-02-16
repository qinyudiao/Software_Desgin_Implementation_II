package assignment6_Client_Server_with_Socket_Network_Programming;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ServerWorker extends Thread {

    private final Socket clientSocket;
    private final Server server;
    private String login = null;
    private OutputStream outputStream;

    public ServerWorker(Server server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            handleClientSocket();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleClientSocket() throws IOException, InterruptedException {
        InputStream inputStream = clientSocket.getInputStream();
        this.outputStream = clientSocket.getOutputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ( (line = reader.readLine()) != null) {
            String[] tokens = StringUtils.split(line);
            if (tokens != null && tokens.length > 0) {
                String cmd = tokens[0];
                if ("logoff".equals(cmd) || "quit".equalsIgnoreCase(cmd)) {
                    handleLogoff();
                    break;
                } else if ("login".equals(cmd)) {
                    handleLogin(outputStream, tokens);
                } else if ("msg".equals(cmd)) {
                    String[] tokensMsg = StringUtils.split(line, null, 3);
                    handleMessage(tokensMsg);
                } else {
                    String msg = "unknown " + cmd + "\n";
                    outputStream.write(msg.getBytes());
                }
            }
        }

        clientSocket.close();
    }


    private void handleMessage(String[] tokens) throws IOException {
    	String thisLogin = tokens[0];
        String sendTo = tokens[1];
        String body = tokens[2];
        
        List<ServerWorker> workerList = server.getWorkerList();
        if(sendTo.equals("#room")) {  	
	        for(ServerWorker worker : workerList) {
	        	if (!thisLogin.equalsIgnoreCase(worker.getLogin())) {
	        		//body = body;
			        String outMsg = "msg " + login + " " + body + "\n";
			        worker.send(outMsg);
	        	}
	        }
        }
        else {
	        for(ServerWorker worker : workerList) {
	
	        	if (sendTo.equalsIgnoreCase(worker.getLogin())) {
	        		String outMsg = "msg " + login + " " + body + "\n";
	                worker.send(outMsg);
	            }
	
	        }
        }
    }

    private void handleLogoff() throws IOException {
        server.removeWorker(this);
        List<ServerWorker> workerList = server.getWorkerList();

        //send other online users current user's status
        String onlineMsg = "offline " + login + "\n";
        for(ServerWorker worker : workerList) {
            if (!login.equals(worker.getLogin())) {
                worker.send(onlineMsg);
            }
        }
        clientSocket.close();
    }

    public String getLogin() {
        return login;
    }

    private void handleLogin(OutputStream outputStream, String[] tokens) throws IOException {
        if (tokens.length == 3) {
            String login = tokens[1];
            String password = tokens[2];

            if ((login.equals("jolin") && password.equals("jolin")) || (login.equals("jay") && password.equals("jay")) || (login.equals("jj") && password.equals("jj")) ) {
                String msg = "ok login\n";
                outputStream.write(msg.getBytes());
                this.login = login;
                System.out.println("Login succeeded");

                List<ServerWorker> workerList = server.getWorkerList();

                //send current user all other online logins
                for(ServerWorker worker : workerList) {
                    if (worker.getLogin() != null) {
                        if (!login.equals(worker.getLogin())) {
                            String msg2 = "online " + worker.getLogin() + "\n";
                            send(msg2);
                        }
                    }
                }

                //send other online users current user's status
                String onlineMsg = "online " + login + "\n";
                for(ServerWorker worker : workerList) {
                    if (!login.equals(worker.getLogin())) {
                        worker.send(onlineMsg);
                    }
                }
            } else {
                String msg = "error login\n";
                outputStream.write(msg.getBytes());
                System.err.println("Login failed");
            }
        }
    }

    private void send(String msg) throws IOException {
        if (login != null) {
            try {
                outputStream.write(msg.getBytes());
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
