package assignment6_Client_Server_with_Socket_Network_Programming;

public interface UserStatusListener {
    public void online(String login);
    public void offline(String login);
}
