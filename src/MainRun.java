import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
/** The class runs all the functions of the game.
 * It renders the world and draws graphics on the screen.
 * It also controls all the networking features of the game.
 * @author shimismith
 *@since March 28, 2018
 */
public class MainRun extends JPanel implements WindowListener{

private static JFrame frame;

/**width of screen.*/
  public static final int width = 1080;
  /**height of screen.*/
  public static final int height = 720;
  /**all the pixels that are being drawn.*/
  public static int pixels[];
  /**BufferedImage for the screen.*/
  static BufferedImage image;
  private static BufferedImage reticle;
  private static BufferedImage hands;
  static Player player;
  static Player enemy;
  /**Rectangle that represents the enemy.*/
  static Rectangle er;
  private static Render ren;
  public static LevelBuilder lb;
  static Server server;
  static Client client;
  /**Array of rectangles that contains all the rectangles that make up the world including the enemy.*/
  public static Rectangle[] rects;
  /**True if you won.*/
  private static boolean win;
  /**True if you lost.*/
  private static boolean lose;
  
  private static Point[][] mesh;

  /** Loads all the images necessary for the game, sets up the networking, builds the level, opens a window and starts the game loop. 
   * @param args
   */
 public static void main(String[] args) {
	 
  image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);  //creates a buffered image for the screen

  player = new Player();
   
   player.setServer(new NetworkingGUI().runNetworkingGUI());  //setServer based on the results of the networking gui- which button was pressed
   /* Use this instead to skip the GUI for testing
   player.setServer(true);
   player.setMultiplayer(false);
   */
  
  enemy = new Player();
  enemy.setServer(!player.isServer());

  if (player.isServer() && player.isMultiplayer()) { // server
   try {
    server = new Server();
   } catch (Exception e) {
    e.printStackTrace();
   }
  } else if (player.isMultiplayer()) { // client
   try {
    client = new Client();
   } catch (Exception e) {
    e.printStackTrace();
   }
  }

  ren = new Render();

  lb = new LevelBuilder("Unknown.png");
  lb.buildLevel();
  
  Texture tex = new Texture("enemy1.png", 0);
  try {
   reticle = ImageIO.read(MainRun.class.getResource("reticle.png"));  //creates the buffered image for the reticle
  } catch (IOException e) {
   e.printStackTrace();
  }
  try {
   hands = ImageIO.read(MainRun.class.getResource("Hands.png"));  //creates the bufferedimage for the hands
  } catch (IOException e) {
   e.printStackTrace();
  }

  // creates enemy rectangle and adds it to the array of rectangles
  if (player.isMultiplayer()) {
   er = new Rectangle(new Point(enemy.getX(), enemy.getY() - 10, enemy.getZ()),
     new Point(enemy.getX() + 10, enemy.getY() - 10, enemy.getZ()),
     new Point(enemy.getX(), enemy.getY() + 10, enemy.getZ()),
     new Point(enemy.getX() + 10, enemy.getY() + 10, enemy.getZ()), tex, true);
   rects = Arrays.copyOf(lb.getWorld(), lb.getWorldLength() + 1);
   rects[rects.length - 1] = er; // last index
  } else {
   rects = Arrays.copyOf(lb.getWorld(), lb.getWorldLength());
  }

  frame = new JFrame("Window");
  frame.setSize(width, height);
  frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);  //there are custom close operations
  frame.setResizable(false);

  MainRun jp = new MainRun();
  frame.add(jp);

  frame.setVisible(true);

  frame.add(new Controller());
  frame.addWindowListener(new MainRun());  //used for custom close operations

  jp.mainGameLoop();  //start the game loop

 }
  
  /** This method has a loop that loops continuously throughout the game with a 16 millisecond delay.
   * It carries out game play functions.
   * This includes moving the player and moving the rectangles in the world relative to the player.
   * It also adds the damage done to the other player and sends and receives the most up to date player object to the other computer.
   */
 public void mainGameLoop() {

  for(;;){
   // LevelBuilder.box.setProperLocations((int)
   // MainRun.player.getX(),(int) MainRun.player.getZ()); //set position before collision detection

   if (player.isMultiplayer() && !enemy.isMultiplayer()) { // the other player quit- you're to good for him
    player.setMultiplayer(false);
   }

   if (player.isMultiplayer()) {
    double eInitialX = enemy.getX(); // initial x of enemy
    double eInitialZ = enemy.getZ(); // initial z of enemy
    if (player.isServer()) { // server
     try {
      server.sendData();
      enemy = server.receiveData();
     } catch (IOException e) {
      e.printStackTrace();
     } catch (Exception e) {
      e.printStackTrace();
     }
    } else { // client
     try {
      client.sendData();
      enemy = client.receiveData();
     } catch (ClassNotFoundException e) {
      e.printStackTrace();
     } catch (IOException e) {
      e.printStackTrace();
     }
    }
    // change in enemies's location due to movement- he is probably running away from you in fear
    double eChangeInX = enemy.getX() - eInitialX;
    double eChangeInZ = enemy.getZ() - eInitialZ;

    //adujust the location and orientation of the rectangle that represents the enemy
    er.setBLeft(new Point(er.getBLeft().getX() + eChangeInX, er.getBLeft().getY(),
      er.getBLeft().getZ() + eChangeInZ));
    er.setBRight(new Point(er.getBRight().getX() + eChangeInX, er.getBRight().getY(),
      er.getBRight().getZ() + eChangeInZ));
    er.setTLeft(new Point(er.getTLeft().getX() + eChangeInX, er.getTLeft().getY(),
      er.getTLeft().getZ() + eChangeInZ));
    er.setTRight(new Point(er.getTRight().getX() + eChangeInX, er.getTRight().getY(),
      er.getTRight().getZ() + eChangeInZ));

    er.rotateToFacePlayer();

    // shooting
    player.setHealth(player.getHealth() - enemy.getDamageDone());  //subtract the damage the enemy did from your health
    player.setDamageDone(0); // reset the damage done back to zero

    //check if game is over- win or lose
    if (player.getHealth() <= 0) {
     lose = true;
    }
    if (enemy.getHealth() <= 0) {
     win = true;
    }
   }

      //move player and adjust location of rectangles
   if (player.getVelH() != 0 || player.getVelF() != 0 || player.getAngularVel() != 0) {
    player.move();
    for (int i = 0; i < rects.length; i++) {
     rects[i].adjustLocationRelativeToPlayer();
    }
    player.resetRotation();
   }
   
   
   // LevelBuilder.box.checkForCollision();

   repaint();

   try {
    Thread.sleep(16);
   } catch (Exception e) {
   }

  }

 }
 		 
  /**Draws all the graphics to the screen.
   */
  @Override
  public void paintComponent(Graphics g){
	
    super.paintComponent(g);
    
    //win screen
    if(win){
     Image img= new ImageIcon(MainRun.class.getResource("win.gif")).getImage();
     g.drawImage(img, 0, 0, null);
     
      g.setFont(new Font("Courier New", 1, 120));
         g.setColor(Color.GREEN); 
         g.drawString("You Win", (width-g.getFontMetrics().stringWidth("You Win"))/2, (height-g.getFontMetrics().getHeight())/2);
         return;
    }
    //lose screen
    if(lose){
     g.setFont(new Font("Courier New", 1, 120));
        g.setColor(Color.GREEN); 
        g.drawString("You Lose", (width-g.getFontMetrics().stringWidth("You Lose"))/2, (height-g.getFontMetrics().getHeight())/2);

        return;
    }
    
      pixels= new int[MainRun.width*MainRun.height];
      ren.resetZBuffer();
      
      //clears the buffered image in an efficient way
      Graphics2D g2d = image.createGraphics();
      g2d.setBackground(new Color(0, 0, 0, 0));
      g2d.clearRect(0, 0, image.getWidth(), image.getHeight());
    
      //renders the rectangles
       for(int i = 0; i < rects.length; i++){
          ren.renderRectangle(rects[i]);
        }

      
        image.setRGB(0, 0, width, height, pixels, 0, width);
        g.drawImage(image, 0, 0, this);  //draws screen

        //draws reticle
        if(player.isAiming()){
           g.drawImage(reticle, (width-reticle.getWidth())/2, (height-reticle.getHeight())/2, this);
        }
            
        //draws health bar
        g.setColor(Color.GRAY);
        g.fillRect(5, 5, 125, 25);
        g.setColor(Color.RED);
        g.fillRect(5, 5, (int)(125*(player.getHealth()/100.0)), 25);
        g.setColor(Color.WHITE);
        g.drawRect(5, 5, 125, 25);  //border
        g.setFont(new Font("Courier New", 1, 12));
        g.setColor(Color.BLACK);
        String health= player.getHealth() + " / " + 100;
        g.drawString(health, (int)(5+(125/2)-(g.getFontMetrics().stringWidth(health)/2)), (int)(5+(25/2)+(g.getFontMetrics().getHeight()/2)));  //centers text in health bar

        g.drawImage(hands, 0, 0, this);
        
  }
  
  @Override
public void windowOpened(WindowEvent e) {
 // TODO Auto-generated method stub
 
}

  /** This is a custom close operation. When the JFrame is closed it sends to the other player that you have quit the game and then ends the program.
   * @param e WindowEvent
   */
@Override
public void windowClosing(WindowEvent e) {
  if (player.isMultiplayer()) {
   player.setMultiplayer(false);

   // sends data one last time so that the other player will no that they left
   if (player.isServer()) { // server
    try {
     server.sendData();
     enemy = server.receiveData();
    } catch (IOException ex) {
     ex.printStackTrace();
    } catch (Exception ex) {
     ex.printStackTrace();
    }
   } else { // client
    try {
     client.sendData();
     enemy = client.receiveData();
    } catch (ClassNotFoundException ex) {
     ex.printStackTrace();
    } catch (IOException ex) {
     ex.printStackTrace();
    }
   }
  }
 
 System.exit(0);
}

@Override
public void windowClosed(WindowEvent e) {
 // TODO Auto-generated method stub
 
}

@Override
public void windowIconified(WindowEvent e) {
 // TODO Auto-generated method stub
 
}

@Override
public void windowDeiconified(WindowEvent e) {
 // TODO Auto-generated method stub
 
}

@Override
public void windowActivated(WindowEvent e) {
 // TODO Auto-generated method stub
 
}

@Override
public void windowDeactivated(WindowEvent e) {
 // TODO Auto-generated method stub
 
}
  
  
}