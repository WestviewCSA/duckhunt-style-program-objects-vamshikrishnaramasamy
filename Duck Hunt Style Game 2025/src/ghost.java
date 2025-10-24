import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;
public class ghost {
	
	// The ghost class represents a picture of a ghost that can be drawn on the screen.
	    // Instance variables (data that belongs to each ghost object)
	    private Image img;               // Stores the picture of the ghost
	   
	    private Image normal; //normal look
	    private Image dead;
	   
	   
	    private long time = 1220;
	    private boolean isDead = false;
	    
	   
	   
	   
	   
	    private AffineTransform tx;      // Used to move (translate) and resize (scale) the image
	    // Variables to control the size (scale) of the ghost image
	    private double scaleX;          
	    private double scaleY;          
	    // Variables to control the location (x and y position) of the ghost
	    private double x;               
	    private double y;       
	   
	    //variables for speed
	    private int vx;
	    private int vy;
	   
	    //debugging variable
	    public boolean debugging = true;
	    // Constructor: runs when you make a new ghost object
	    public ghost() {
	       
	    	normal = getImage("/imgs/BlueGhost.gif"); // Load the image file
	        dead = getImage("/imgs/BlueExplode.gif");
	       
	        //img will point to current state object for image
	        img = normal;
	       
	       
	       
	       
	       
	       
	       
	        tx = AffineTransform.getTranslateInstance(0, 0); // Start with image at (0,0)
	       
	        // Default values
	        scaleX = 0.65;
	        scaleY = 0.65;
	        x = 120;
	        y = 350;
	       
	        vx = 6;
	        vy = 6;
	        init(x, y); // Set up the starting location and size
	    }
	   
	    //2nd constructor to initialize location and scale!
	    public ghost(int x, int y, int scaleX, int scaleY) {
	    	this();
	    	this.x 		= x;
	    	this.y 		= y;
	    	this.scaleX = scaleX;
	    	this.scaleY = scaleY;
	    	init(x,y);
	    }
	   
	    //2nd constructor to initialize location and scale!
	    public ghost(int x, int y, int scaleX, int scaleY, int vx, int vy) {
	    	this();
	    	this.x 		= x;
	    	this.y 		= y;
	    	this.scaleX = scaleX;
	    	this.scaleY = scaleY;
	    	this.vx 	= vx;
	    	this.vy 	= vy;
	    	init(x,y);
	    }
	   
	    public void setVelocityVariables(int vx, int vy) {
	    	this.vx = vx;
	    	this.vy = vy;
	    }
	   
	   
	    // Changes the picture to a new image file
	    public void changePicture(String imageFileName) {
	        img = getImage("/imgs/"+imageFileName);
	        init(x, y); // keep same location when changing image
	    }
	   
	    //update any variables for the object such as x, y, vx, vy
	    public void update() {
	    	
	    	
	    	//respawn logic
	    	if(this.isDead) {
	    		time -= 16;
	    		
	    		if(time <= 0) {
	    			isDead = false;
	    			time = 1220;
	    			//(int)(Math.random()*(range+1)+min);
	    			vx = (int)(Math.random()*5+6);
	    			vy = (int)(Math.random()*5+6);
	    			img = normal;
	    		}
	    	}
	    	
	    	
	    		
	    	//x position changes based off vx
	    	x += vx;
	    	y += vy;
	    	
	    	if(x >= 800) {
	    		vx *= -1;
	    	}
	    	
	    	if(x <= 0) {
	    		vx *= -1;
	    	}
	    
	    	
	    	if(y <= 30) {
	    		vy *= -1;
	    	}
//	    		//respawn - if falling from sky
//	    		if(vy >= 2 && vx == 0 && y > 850) { //<--- it's falling
//	    			
//	    			vx = (int)(Math.random()*(5)+2);
//	    			
//	    			//50% chance of going left
//	    			if(Math.random()<0.5);{
//	    				vx *= -1;
//	    			}
//	    			
//	    			vy = - (int)(Math.random()*3+2);
//	    			
//	    		}
	    		
//	    		if(y >= 450 && vy > 1 && vy < 40) {
//		    		vy *= -1;
//		    	}
//	
	    		
	    		if(y >= 720 && vy > 1 && vy < 40) vy *= -1;
	    		
	    		//if the object is falling from the sky - no vx and vy is positive
	    		if(vx == 0 && vy > 1) {
	    			if(y >= 750) {}
	    			vy = -(int)(Math.random()*8+3);
	    			vx = (int)(Math.random()*8+3);
	    			
	    			//50% of the time, vx is negative
	    			if(Math.random()<0.5) {
	    				vx *= -1;
	    			}
	    		}
	    	
	    	//regular behavior - regular bouncing from bottom
	    	if(y >= 550 && vx != 0) vy*= -1;
	    	
	    }
	   
	   
	   
	    // Draws the ghost on the screen
	    public void paint(Graphics g) {
	        Graphics2D g2 = (Graphics2D) g;   // Graphics2D lets us draw images
	        g2.drawImage(img, tx, null);      // Actually draw the ghost image	
	        update();
	        init(x,y);
	       
	        if(debugging) {
	            //create a green hitbox
	            g.setColor(Color.green);
	            g.drawRect((int) x,  (int) y,  100,  100); //hit box
	            }
	       
	    }
	   
	    // Setup method: places the ghost at (a, b) and scales it
	    private void init(double a, double b) {
	        tx.setToTranslation(a, b);        // Move the image to position (a, b)
	        tx.scale(scaleX, scaleY);         // Resize the image using the scale variables
	    }
	    // Loads an image from the given file path
	    private Image getImage(String path) {
	        Image tempImage = null;
	        try {
	            URL imageURL = ghost.class.getResource(path);
	            tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return tempImage;
	    }
	    // NEW: Method to set scale
	    public void setScale(double sx, double sy) {
	        scaleX = sx;
	        scaleY = sy;
	        init(x, y);  // Keep current location
	    }
	    // NEW: Method to set location
	    public void setLocation(double newX, double newY) {
	        x = newX;
	        y = newY;
	        init(x, y);  // Keep current scale
	    }
	   
	  //Collision and collision logic
	    public boolean checkCollision(int mX, int mY) {
	    	
	    	//represent the mouse as a rectangle
	    	Rectangle mouse = new Rectangle(mX, mY, 50, 50);
	    	
	    	//represent this object as a rectangle
	    	Rectangle thisObject = new Rectangle((int) x, (int) y, 100, 100);
	    	
	    	if(mouse.intersects(thisObject)) {
	    		
	    		//logic if colliding
	    		vx = 0; //turn off vx to fall from the sky
	    		vy = 0; // all y - gravity
	    		
	    		//change sprite to the alternate
	    		img = dead;
	    		isDead = true;
	    		return true;
	    	}else {
	    		
	    		//logic if not colliding
	    		
	    		
	    		
	    		return false;
	    	}
	    }
}
	   
	


