import java.io.Serializable;

/** This class packages the ip address and the port of a server into a message so that it can easily be sent and displayed.
 * @author shimismith
 * @since   May 9, 2016
 */

public class BroadcastMessage implements Comparable<BroadcastMessage>, Serializable{

	/**The port that the server sending this message is connected to.*/
	private int port;
	/**The ip address of the server sending this message.*/
	private String ip;
	
	/** @param ip the ip address of the server
	 * @param port the port that the server is connected to
	 */
	public BroadcastMessage(String ip, int port){
		this.ip= ip;
		this.port= port;
	}
	
	/** @return the port that the server is connected to
	 */
	public int getPort(){
		return port;
	}
	
	/** @return the ip address of the server
	 */
	public String getIP(){
		return ip;
	}
	
	/** Converts the BroadcastMessage into a string so that it can be displayed on a JButton in NetworkingGUI
	 */
	@Override
	public String toString(){
		return ip + "    " + port;
	}
	
	/**Compares the messages by port.
	 * @return a positive number if the port is higher, a negative number if the port is lower and zero if the port is the same.
	 */
	@Override
	public int compareTo(BroadcastMessage bm) {
		return port-bm.port;
	}
	
}
