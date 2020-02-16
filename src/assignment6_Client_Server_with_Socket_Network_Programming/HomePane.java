package assignment6_Client_Server_with_Socket_Network_Programming;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

@SuppressWarnings("serial")
public class HomePane extends JPanel implements UserStatusListener {


    private final ClientMain client;
    private JList<String> homeUI;
    private DefaultListModel<String> homeModel;

    JButton logOffButton = new JButton("LogOff");

    public HomePane(ClientMain client) {
        this.client = client;
        this.client.addUserStatusListener(this);

        homeModel = new DefaultListModel<>();
        homeModel.addElement("#room");
        homeUI = new JList<>(homeModel);
        setLayout(new BorderLayout());
        add(new JScrollPane(homeUI), BorderLayout.CENTER);
        add(logOffButton, BorderLayout.SOUTH);
        
        logOffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
					client.logoff();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
        });
        homeUI.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 1) {
                    String login = homeUI.getSelectedValue();
                    
                    MessagePane messagePane = new MessagePane(client, login);

                    JFrame f = new JFrame(login);
                    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    f.setSize(200, 400);
                    f.getContentPane().add(messagePane, BorderLayout.CENTER);
                    f.setVisible(true);
                }
            }
        });
    }

    @Override
    public void online(String login) {
        homeModel.addElement(login);
    }

    @Override
    public void offline(String login) {
        homeModel.removeElement(login);
    }
}
