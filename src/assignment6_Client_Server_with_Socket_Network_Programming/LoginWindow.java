package assignment6_Client_Server_with_Socket_Network_Programming;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

@SuppressWarnings("serial")
public class LoginWindow extends JFrame {
    private final ClientMain client;
    JTextField loginField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("Login");

    public LoginWindow() {
        super("Login");

        ServerMain sm = new ServerMain();
        this.client = new ClientMain("localhost", sm.getPortNumber());
        client.connect();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setSize(200, 50);
        p.add(loginField);
        p.add(passwordField);
        p.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doLogin();
            }
        });

        getContentPane().add(p, BorderLayout.CENTER);

        pack();

        setVisible(true);
    }

    private void doLogin() {
        String login = loginField.getText();
        @SuppressWarnings("deprecation")
		String password = passwordField.getText();

        try {
            if (client.login(login, password)) {
                HomePane homePane = new HomePane(client);
                JFrame frame = new JFrame("Home (" + login + ")");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(200, 300);


                frame.getContentPane().add(homePane, BorderLayout.CENTER);
                frame.setVisible(true);

                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username/password.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    
//    private void doLogOff() {
//    	
//    }

    public static void main(String[] args) {
        LoginWindow loginWin = new LoginWindow();
        loginWin.setVisible(true);
    }
}
