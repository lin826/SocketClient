import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
 
public class GUI_keyboard implements ActionListener,KeyListener {
	private JFrame frame;
	private JButton button_up,button_left,button_center,button_right;
	private SocketCient socket_client;
	
	public GUI_keyboard(SocketCient c){
		socket_client = c;
		button_up = new JButton("Turn on Light"); // Light up
		button_left = new JButton("Turn left");
		button_center = new JButton("Go straight!"); // Go straight
		button_right = new JButton("Turn right");
		
		setButton(button_center,"3");
		setButton(button_left,"2");
		setButton(button_right,"4");
		setButton(button_up,"1");
		 
		frame = new JFrame();
		frame.setSize(400, 100);
		frame.addWindowListener(new CloseHandler());
		
		frame.getContentPane().add(button_up, BorderLayout.SOUTH);
		frame.getContentPane().add(button_center, BorderLayout.CENTER);
		frame.getContentPane().add(button_left, BorderLayout.WEST);
		frame.getContentPane().add(button_right, BorderLayout.EAST);
		
		frame.setVisible(true);
	}
	public void setButton(JButton b,String s){
		b.setActionCommand(s);
		b.addActionListener(this);
		b.addKeyListener(this);
	}
	public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        System.out.println("cmd: " + cmd);
        send(cmd);
        if(cmd=="1"){
        	switchLightButton();
        }
    }
	private void switchLightButton(){
		if(button_up.getText().equals("Turn on Light"))
    		button_up.setText("Turn off Light");
    	else
    		button_up.setText("Turn on Light");
	}
	private void send(String commend){
		this.socket_client.send(commend);
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		// get the commend
		int k = e.getKeyCode();
		char c = e.getKeyChar();
		switch (k) {
			case KeyEvent.VK_UP:
	            System.out.println("Key UP pressed");
	            send("3");
	            break;
	         
	        case KeyEvent.VK_LEFT:
	            System.out.println("Key LEFT pressed");
	            send("2");
	            break;
	         
	        case KeyEvent.VK_RIGHT:
	            System.out.println("Key RIGHT pressed");
	            send("4");
	            break;
	            
	        case KeyEvent.VK_DOWN:
	            System.out.println("Key DOWN pressed");
	        	switchLightButton();
	            send("1");
	            break;
	        default:
                System.out.println("Unknown pressed key: "+c);
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		// get the commend
		int k = e.getKeyCode();
		char c = e.getKeyChar();
		switch (k) {
			case KeyEvent.VK_UP:
	            System.out.println("Key UP released");
	            send("-3");
	            break;
	         
	        case KeyEvent.VK_LEFT:
	            System.out.println("Key LEFT released");
	            send("-2");
	            break;
	         
	        case KeyEvent.VK_RIGHT:
	            System.out.println("Key RIGHT released");
	            send("-4");
	            break;
	        default:
                System.out.println("Unknown released key: "+c);
		}
		
	}
	protected class CloseHandler extends WindowAdapter{
		public void windowClosing(final WindowEvent event){
			socket_client.send("ERROR");
			frame.dispose();
			System.exit(0);
		}
	}
	public void closeFrame(){
		frame.dispose();
	}
}
