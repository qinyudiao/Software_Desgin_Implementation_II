package assignment6_Client_Server_with_Socket_Network_Programming;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

@SuppressWarnings("serial")
public class MessagePane extends JPanel implements MessageListener {

    private final String login;

    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> messageList = new JList<>(listModel);
    private JTextField inputField = new JTextField();

    public MessagePane(ClientMain client, String login) {
        this.login = login;

        if(!login.equals("#room")) {
        	client.addMessageListener(this);
        }else {
        	client.addMessageListener(this);
        }

        setLayout(new BorderLayout());
        add(new JScrollPane(messageList), BorderLayout.CENTER);
        add(inputField, BorderLayout.SOUTH);

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String text = inputField.getText();
                    
                    if(login != "#room") {
                    	client.msg(login, text);         
                    	listModel.addElement("You: " + text);
                    }
                    else {
                    	client.msg(login, text); 
                    }
                    inputField.setText("");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onMessage(String fromLogin, String msgBody) {
        if (login.equalsIgnoreCase(fromLogin)) {
            String line = fromLogin + ": " + msgBody;
            listModel.addElement(line);
        }        
        else if (login.equalsIgnoreCase("#room")) {
            String line = fromLogin + ": " + msgBody;
            listModel.addElement(line);
        }
        
    }
}
