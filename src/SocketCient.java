
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;


public class SocketCient implements Runnable{
		private static final int PORT = 1049;
//		private static String serverAddress = "csclab.tw";
		private static String serverAddress = "196.114.79.96";
		private BufferedReader in;
	    public static PrintWriter out;
	    private GUI_keyboard gui;
    	private Socket socket;
	    
	    private boolean isRunning = true;
	    private Thread t;

	    /**
	     * Constructs the client by laying out the GUI and registering a
	     * listener with the textfield so that pressing Return in the
	     * listener sends the textfield contents to the server.  Note
	     * however that the textfield is initially NOT editable, and
	     * only becomes editable AFTER the client receives the NAMEACCEPTED
	     * message from the server.
	     */
	    
	    public SocketCient() {
	    	t = new Thread(this);
	    	setSocket();
	    	new KeyAsking(this);
        	t.start();
	    }
	    protected void createGUI(){
	        gui = new GUI_keyboard(this);
	    }
	    private void endThisThread(){
	    	try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	this.isRunning = false;
    		t.interrupt();
	    	System.exit(0);
        }
	    private void errorHandler(String s)
        {
	    	System.out.println( "ERROR: "+ s);
	    	out.println("ERROR");
        	endThisThread();
        }

	    /**
	     * Connects to the server then enters the processing loop.
	     */
	    public void run(){
	        // Process the beginning setting
	        try {
				begin();
			} catch (SocketException e) {
				errorHandler("SocketException");
				e.printStackTrace();
			}
	        // Process all messages from server, according to the protocol.
	        /*String cmd = "";
	        while(isRunning) {
		        	System.out.print("Type the command:");
		            cmd  = user_input.next( );
		            if(!cmd.endsWith("\n")) errorHandler("Not end with '\\n'");
		            else if(cmd.equals("ERROR")) errorHandler("Sending ERROR");
		            out.println(cmd);
		            cmd = "";
	        }*/
	    }

	    private void setSocket() {
			socket = new Socket();
			InetSocketAddress isa = new InetSocketAddress(serverAddress, PORT);
			try {
				socket.connect(isa, 10000);
				in = new BufferedReader(new InputStreamReader(
			            socket.getInputStream()));
			    out = new PrintWriter(socket.getOutputStream(), true);
			} catch (UnknownHostException e1) {
				errorHandler("UnknownHostException");
				e1.printStackTrace();
			} catch (IOException e1) {
				errorHandler("IOException");
				e1.printStackTrace();
			}
			
		}

		private void begin() throws SocketException {
	    	while (isRunning) {
				try {
					String line = in.readLine();
					System.out.println(line);
					if(line==null) endThisThread();
					if(line.equals("ERROR")) errorHandler("Server closed");
				} catch (Exception e) {
					errorHandler("Exception");
					e.printStackTrace();
				}
	        }
			
		}
	    
	    public static void main(String[] args) throws Exception {
	    	new SocketCient();
	    }
	    public void send(String s){
	    	System.out.println("Send: "+s);
	    	this.out.println(s);
	    }
	    
	    
	    
}
