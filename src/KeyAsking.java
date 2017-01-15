
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class KeyAsking implements ActionListener{
	private String key;
	private JTextField key_input;
	private JFrame frame;
	private SocketCient main_socket_client;
	
	KeyAsking(SocketCient socket_client){
		this.main_socket_client = socket_client;
		frame = new JFrame();
		frame.setSize(200, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container cp=frame.getContentPane();
	    cp.setLayout(new BorderLayout(5,5));
		
		JLabel label = new JLabel("Type the Matching Key", SwingConstants.CENTER);
    	key_input = new JTextField("");
    	JButton btnConfirm = new JButton("Submit");
    	
    	btnConfirm.setActionCommand("");
    	btnConfirm.addActionListener(this);
    	
    	cp.add(label,BorderLayout.NORTH);
    	cp.add(key_input,BorderLayout.CENTER);
    	cp.add(btnConfirm,BorderLayout.SOUTH);
    	frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.key = key_input.getText();
		System.out.println(key);
		if(key.length()>0){
			main_socket_client.send(key);
			this.closeWindow();
		}
	}
	
	public String getKey(){
		return key;
	}
	
	public void closeWindow(){
		main_socket_client.createGUI();
		frame.dispose();
	}
}
