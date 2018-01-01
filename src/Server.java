import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
/**This class sets up a server that can connect with a client.
 * The server can send and receive data.
 * The server can also broadcast information about itself in the form of a BroadcastMessage
 * @author shimismith
 * @since May 2, 2016
 */
public class Server implements Runnable{

	private ObjectOutputStream out;
	private ObjectInputStream in;
	
    private BroadcastMessage message;
    
	InetAddress group = InetAddress.getByName("224.0.0.1");  //group identifier
    MulticastSocket socket= new MulticastSocket();  //this is used to send data to a group of clients
    /**  Sends a BroadcastMessage, which has the ip and port of the server.
     * It uses UDP to send the BroadcastMessage to a group identifier. All clients with this group identifier will receive the message.
     * @throws IOException
     */
	private void broadcastServer() throws IOException{	
		//sends BroadcastMessage in the form of a byte array
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();    //converts object to byte array
		ObjectOutputStream os = new ObjectOutputStream(byteStream);
	   os.reset();
	   os.writeObject(message);
	   byte[] buf= byteStream.toByteArray();
	    
	    DatagramPacket packet= new DatagramPacket(buf, buf.length, group, 4446);
        socket.send(packet);
	}

	/**The thread used for broadcasting the BroadcastMessage.*/
	Thread broadcasting;
	ServerSocket serverSocket;
	Socket clientSocket;
	
	/**
	 * Starts a server. Creates a BroacastMessage and a thread to send out the BroadcastMessage. The method waits until the server connects with a client.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public Server() throws IOException, InterruptedException{

		serverSocket= new ServerSocket(0);  //0 makes the socket on any available port
				
		message= new BroadcastMessage(InetAddress.getLocalHost().getHostAddress(), serverSocket.getLocalPort());	

		running= true;
		broadcasting= new Thread(this);
		broadcasting.start();  //this thread broadcasts the server
		
		clientSocket= serverSocket.accept();  //connection is made
		
		//stops broadcasting after connected
		running= false;
		broadcasting.join();
		
		out= new ObjectOutputStream(clientSocket.getOutputStream());
        in= new ObjectInputStream(clientSocket.getInputStream());
	}
	
	/** Sends the player object.
	 * @throws IOException
	 */
	public void sendData() throws IOException{
		out.reset();  //resets so the same object isn't sent every time
		out.writeObject(MainRun.player); 
	}
	
	/** Receives a player object.
	 * @return the player object that was received
	 */
	public Player receiveData() throws Exception{
		return (Player)in.readObject();
	}

	/**True if the broadcast thread is running.*/
	boolean running;
	/** Runs the thread that sends out the BroadcastMessage every 50 milliseconds 
	 */
	@Override
	public void run() {
		try {
			socket.joinGroup(group);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(running){
			try {
				broadcastServer();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try{
				Thread.sleep(50);
			}
			catch(Exception e){	}
		}
	}

}
