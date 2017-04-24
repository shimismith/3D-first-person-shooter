import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
/**This class sets up a client that can connect with a server.
 * The client can send and receive data.
 * @author shimismith
 * @since May 2, 2016
 */
public class Client{

	private Socket soc;
	
	Server s;
	/**The ip address of the server that the client is connecting to.*/
	private static String ip;
	/**The port that the server, that the client is connecting to, is connected to.*/
	private static int port;
	
	/** Sets the information of the server that the client is going to connect to 
	 * @param ip the ip address of the server
	 * @param port the port that the server is connected to
	 */
	public static void setServerInfo(String ip, int port){
		Client.ip= ip;
		Client.port= port;
	}
	
	MulticastSocket socket = new MulticastSocket(4446);  //this is used when data is sent to a group of clients
	InetAddress group = InetAddress.getByName("224.0.0.1");  //the range should be between 224.0.0.1 and 239.255.255.254
															//group identifier
	/** Searches for a server to connect to.
	 * @throws Exception
	 */
	private void searchForServers() throws Exception{	
		socket.joinGroup(group);

		while(ip==null && port==0){  //waits until setServerInfo has been called from NetworkingGUI
			//recieves BroadcastMessage in the form of a byte array
			byte[] buf= new byte[6400];
		    DatagramPacket packet= new DatagramPacket(buf, buf.length);
		    socket.receive(packet);
		    
		    ByteArrayInputStream in = new ByteArrayInputStream(buf);
		    ObjectInputStream is= new ObjectInputStream(in);

		    BroadcastMessage message= (BroadcastMessage)is.readObject();
		    NetworkingGUI.addToServerList(message);  //adds to the list of servers to display to the client
		    
		}
        socket.leaveGroup(group);
        socket.close();
		
	}
	
	ObjectOutputStream out;
	ObjectInputStream in;
	/** Finds a server and connects to it.
	 * @throws Exception
	 */
	public Client() throws Exception{
		
		searchForServers();
		
        soc= new Socket(ip, port);  //ip is the ip address of the server
        
       out= new ObjectOutputStream(soc.getOutputStream());
       in= new ObjectInputStream(soc.getInputStream());
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
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public Player receiveData() throws ClassNotFoundException, IOException{
		return (Player)in.readObject();
	}

}
