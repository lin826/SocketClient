import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
 
public class GUI_keyboard implements KeyListener {
	private JFrame frame;
	private JButton button_up,button_left,button_center,button_right;
	private SocketCient socket_client;
	private int Status = 0;
	
	public GUI_keyboard(SocketCient c){
		socket_client = c;
		button_up = new JButton("Turn on Ligh0.t"); // Light up
		button_left = new JButton("Turn left");
		button_center = new JButton("Go straight!"); // Go straight
		button_right = new JButton("Turn right");
		
		setButton(button_center);
		setButton(button_left);
		setButton(button_right);
		setButton(button_up);
		 
		frame = new JFrame();
		frame.setSize(400, 100);
		frame.addWindowListener(new CloseHandler());
		
		frame.getContentPane().add(button_up, BorderLayout.SOUTH);
		frame.getContentPane().add(button_center, BorderLayout.CENTER);
		frame.getContentPane().add(button_left, BorderLayout.WEST);
		frame.getContentPane().add(button_right, BorderLayout.EAST);
		
		frame.setVisible(true);
	}
	public void setButton(JButton b){
		b.addKeyListener(this);
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
	            Status = 3;
	            makeChange();
	            break;
	         
	        case KeyEvent.VK_LEFT:
	            System.out.println("Key LEFT pressed");
	            Status = 2;
	            makeChange();
	            break;
	         
	        case KeyEvent.VK_RIGHT:
	            System.out.println("Key RIGHT pressed");
	            Status = 4;
	            makeChange();
	            break;
	            
	        case KeyEvent.VK_DOWN:
	            System.out.println("Key DOWN pressed");
	        	switchLightButton();
	            Status = 1;
	            makeChange();
	            break;
	        default:
                System.out.println("Unknown pressed key: "+c);
		}
	}
	private void makeChange() {
		if(Status<0){
			send(Integer.toString(Status));
		}
		send(Integer.toString(Status));
		Status = 0;
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
	            Status = -3;
	            makeChange();
	            break;
	         
	        case KeyEvent.VK_LEFT:
	            System.out.println("Key LEFT released");
	            Status = -2;
	            makeChange();
	            break;
	         
	        case KeyEvent.VK_RIGHT:
	            System.out.println("Key RIGHT released");
	            Status = -4;
	            makeChange();
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
