import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Russell Brown
 * @since April 7, 2016
 * 
 * The class that controls player movement and key presses.
 * 
 */

public class Controller extends Component implements KeyListener{

  /**
     * Constructor that attatches the keyListener to the program.
     * 
     * @return has no return type because its a constructor.
     * 
     */
  
  public Controller(){
    addKeyListener(this);
    setFocusable(true);
    setFocusTraversalKeysEnabled(false);
  }
  
  /**
     * This Method checks if a key has been pressed.
     * If a key was pressed, the method responds by changing the players velocity. 
     * 
     * @param e is simply the identifier of the KeyEvent, used to determine which key has been pressed.
     * 
     * @return returns void because the velocity values are directly changed in the player class.
     * 
     */
  
  @Override
  public void keyPressed(KeyEvent e){
    int keyCode= e.getKeyCode();
    
    //setVel methods reffer to the players forward and horizontal velocities
    
    if(keyCode==KeyEvent.VK_A){
      MainRun.player.setVelH(-2);
    }
     if(keyCode==KeyEvent.VK_D){
      MainRun.player.setVelH(2);
    }
      if(keyCode==KeyEvent.VK_W){
        MainRun.player.setVelF(0.5);
    }
       if(keyCode==KeyEvent.VK_S){
        MainRun.player.setVelF(-0.5);
    }
       if(keyCode==KeyEvent.VK_LEFT){
        MainRun.player.setAngularVel(-0.05);
    }
     if(keyCode==KeyEvent.VK_RIGHT){
        MainRun.player.setAngularVel(0.05);
    }
    if(keyCode==KeyEvent.VK_UP){
    	if(MainRun.player.isMultiplayer() && Shooting.shoot()){
    		MainRun.player.setDamageDone(MainRun.player.getDamageDone()+1);
    	}
  
    } 
       
  }
  
  /**
     * This Method checks if a key has been released.
     * If a key was released, the method responds by setting the players velocity to zero. 
     * 
     * @param e is simply the identifier of the KeyEvent, used to determine which key has been released.
     * 
     * @return returns void because the velocity values are directly changed in the player class.
     * 
     */

  @Override
  public void keyReleased(KeyEvent e){
    int keyCode= e.getKeyCode();
    
    if(keyCode==KeyEvent.VK_A){
      MainRun.player.setVelH(0);
    }
     if(keyCode==KeyEvent.VK_D){
      MainRun.player.setVelH(0);
    }
      if(keyCode==KeyEvent.VK_W){
        MainRun.player.setVelF(0);
    }
       if(keyCode==KeyEvent.VK_S){
        MainRun.player.setVelF(0);
    }
      if(keyCode==KeyEvent.VK_LEFT){
        MainRun.player.setAngularVel(0);
    }
     if(keyCode==KeyEvent.VK_RIGHT){
        MainRun.player.setAngularVel(0);
    }
     if(keyCode==KeyEvent.VK_SHIFT){
      MainRun.player.switchAiming();
     }
 
    
  }
  
  /**
     * Method needed just because KeyListener is abstract. 
     * 
     * @param e is simply the identifier of the KeyEvent, used to determine which key has been typed.
     * 
     * @return returns void because nothing happens.
     * 
     */
  
  @Override
  public void keyTyped(KeyEvent e){
    
  }
  
}


