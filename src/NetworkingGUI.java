import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
/** This is a GUI that lets the user set up a multiplayer game or a single player game.
 * @author shimismith
 * @since May 2, 2016
 */
public class NetworkingGUI implements ActionListener{
	
	private static JFrame frame;
	private JButton serverButton;
	private JButton clientButton;
	private JButton singlePlayerButton;
	/**True if the player is the server.*/
	private boolean server;
	/**True if done using the networking GUI.*/
	private boolean done= false;
	private static NetworkingGUI gui= new NetworkingGUI();
	
	/** Creates a window with buttons that lets the user select if they want to be the server, client or play as single player.
	 * @return a boolean that represents if the player is the client or server
	 */
	public boolean runNetworkingGUI(){
		 frame= new JFrame("Networking");
		 frame.setSize(400, 200);
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.setResizable(false);
		 frame.setLayout(null);
		 
		 serverButton= new JButton("Server");
		 serverButton.setSize(50, 20);
		 serverButton.setLocation(10, 10);
		 serverButton.addActionListener(this);
		 
		 clientButton= new JButton("Client");
		 clientButton.setSize(50, 20);
		 clientButton.setLocation(70, 10);
		 clientButton.addActionListener(this);
		 
		 singlePlayerButton= new JButton("Single Player");
		 singlePlayerButton.setSize(100, 20);
		 singlePlayerButton.setLocation(10, 40);
		 singlePlayerButton.addActionListener(this);
		
		 frame.add(serverButton);
		 frame.add(clientButton);
		 frame.add(singlePlayerButton);
		 
		 frame.setVisible(true);
		 
		 //waits until one of the buttons has been pressed
		 while(!done){
			 try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		 }
		 
		 return server;
	}
	
	/** Adds this server information to be displayed to the client. The client can then choose which server they want to connect to.
	 * @param m the BroadcastMessage to be added to the list of servers which will be displayed to the client
	 */
	public static void addToServerList(BroadcastMessage m){
		Arrays.sort(serverList);
		int index= Arrays.binarySearch(serverList, m);  //checks if the server is in the list

		if(index<0){  //if it's not in the list - add the server to the list
			serverList= Arrays.copyOf(serverList, serverList.length+1);
			serverList[serverList.length-1]= m;
			gui.showServerSelect();
		}
	}
	
	/**Array of BroadcastMessages to display.*/
	private static BroadcastMessage[] serverList= new BroadcastMessage[0];
	private static JButton[] buttons;
	
	/** Displays a JScrollPane that contains buttons for every server on the network.
	 * The list updates when new servers are created.
	 * The client can click the button corresponding to the server that they want to connect to. 
	 */
	private void showServerSelect(){
		frame.getContentPane().removeAll();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);  //has a vertical scrollbar
	    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);  //doesn't have a horizontal scrollbar
		scrollPane.setLocation(0, 0);
		scrollPane.setSize(frame.getWidth(), frame.getHeight()-21);
		frame.add(scrollPane);
		
		JPanel panel= new JPanel();
		BoxLayout bl= new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(bl);
		
		buttons= new JButton[serverList.length];
		for(int i=0; i<serverList.length; i++){
			buttons[i]= new JButton(serverList[i].toString());
			buttons[i].addActionListener(this);
			panel.add(buttons[i]);
		}

		scrollPane.setViewportView(panel);  //puts panel into scrollPane
		frame.repaint();
	}
	
	/** Carries out the proper tasks corresponding to which button is being pressed.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(serverButton)){  //selected server
			server= true;
			MainRun.player.setMultiplayer(true);
			done= true;
			frame.dispose();
		}
		else if(e.getSource().equals(clientButton)){  //selected client
			server= false;
			MainRun.player.setMultiplayer(true);
			done= true;
			showServerSelect();
		}
		else if(e.getSource().equals(singlePlayerButton)){  //selected single player
			MainRun.player.setMultiplayer(false);
			done= true;
			frame.dispose();
		}
		else{
			for(int i=0; i<buttons.length; i++){
				if(e.getSource().equals(buttons[i])){  //selected one of the servers
					Client.setServerInfo(serverList[i].getIP(), serverList[i].getPort());
					frame.dispose();
					break;
				}
			}
		}
		
	}
}
