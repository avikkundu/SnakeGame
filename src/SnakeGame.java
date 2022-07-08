/*
 * This project was built in 2018 for my school project.
 * This is a copy of the standard snake game.
 */
package SGame;

/**
 *
 * @author kundu
 */
 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class SnakeGame extends JPanel 
{
    //public static int x=200;
    //public static int y=50;
   
    private static final int[] x=new int[600];
    private static final int[] y=new int[600];
    
    static int  i;
    
    private  static JFrame jfr;
    private static String state="down";
    
    private static  int ballX;
    private static  int ballY;
    
    private long score=0;
    private static int length=3;
    
    private boolean running=true;
    
    public void init(Graphics g)
    {
         g.setColor(Color.DARK_GRAY);
       for(int i=0;i<600;i+=55)
       {
            g.fillRect(0, i,50, 50);     
            g.fillRect(350, i,50, 50);
            g.fillRect(i+10, 0,50, 50);
            g.fillRect(i+10, 550,50, 50);
       }
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        drawSnake(g);
    }
    
    public void locateBall()
    {
        ballX=(int)(Math.random()*200+50);
        ballY=(int)(1*400+50);
        System.out.println("ballX == "+ballX+" ballY= "+ballY);
        repaint();
    }
    public  void drawSnake( Graphics g)
    {
        
        init(g);
        
       updateCoordinate();
           
        g.setColor(Color.MAGENTA);
        for( i=0;i<length;i++)
       {
           g.fillOval(x[i], y[i], 20,20 );
          
       }
      
       g.setColor(Color.YELLOW);
     
       g.fillOval(ballX,ballY, 20,20);//System.out.println("Score = "+score);
       if(collidesAt() )
       {
           
           score+=10;
           System.out.println("New Score="+score);
           increaseLength();
           locateBall();
          /* for(int i=0;i<length;i++)
        System.out.println("x["+i+"]= "+x[i]+"y["+i+"]= "+y[i]);*/
           repaint();
        }
        collideWithWall();
    }
    public void swap()
    {
        for(int j=0;j<length-1;j++)
        {
            x[j]=x[j+1];
            y[j]=y[j+1];
            // System.out.println("x["+j+"]= "+x[j]+" y["+j+"]= "+y[j]);
        }
    }
    
    public void updateCoordinate()
    {
           swap();
           if(state.equals("right") )
           x[length-1]+=20;
           else if(state.equals("left"))
           x[length-1]-=20;
           else if(state.equals("up"))
           y[length-1]-=20;//{System.out.println("X in UP of drawSnake= "+x);}
           else
           y[length-1]+=20;
             
    }
    public boolean collidesAt()
    {
        if((ballX-20<x[length-1] &&x[length-1]<ballX+20) && (ballY-20<y[length-1])&&(y[length-1]<ballY+20))return true;
        else
        return false;
    }
    public void increaseLength()
    {
        if(state.equals("right") )
           {x[length]=x[length-1]+20;y[length]=y[length-1];}
           else if(state.equals("left"))
           {x[length]=x[length-1]-20;y[length]=y[length-1];}
           else if(state.equals("up"))
           {y[length]=y[length-1]-20;x[length]=x[length-1];}//{System.out.println("X in UP of drawSnake= "+x);}
           else
           {y[length]=y[length-1]-20;x[length]=x[length-1];}
           length++;
    }
    public   void collideWithWall()
    {
        if( (y[length-1]<50)  ||  y[length-1]>=540  || x[length-1]>350   || x[length-1]<50     )
        {
            System.out.println("COLLIDED:////////////////////////GAME ENDS");
            System.out.println("x["+(length-1)+"]= "+x[length-1]+" y["+(length-1)+"]= "+y[length-1]);
            System.exit(0);
        }
        //////////////////COLLIDING ITSELF
        /*for(int i=length-2;i>-1;i--)
        {
            if((x[length-1]==x[i]) && (y[length-1]==y[i]))
            {
                //length-=1;
                 System.out.println("COLLIDED  ITSELF:////////////////////////GAME ENDS");
                   System.out.println("x["+(length-1)+"]= "+x[length-1]+" y["+(length-1)+"]= "+y[length-1]);
                 System.exit(0);  
            }
        }*/
    }
   
    public void load()
    {
        
         //setting coordinate
         x[0]=x[1]=x[2]=200;
         y[0]=150;y[1]=170;y[2]=190;
        
         
         jfr=new JFrame("Snake Game");
         jfr.setSize(400,600);
         jfr.setResizable(false);
         SnakeGame ob=new SnakeGame();
         jfr.add(ob);
         ob. locateBall();
         jfr.addKeyListener(new KeyListener()
         {
              public void keyPressed(KeyEvent e)
              {    
                      int pointer;
                     if(i>2)pointer=x[--i];
                     else pointer=x[i];
                     int copyI=i;
                    switch(e.getKeyCode())
                    {
                              case KeyEvent.VK_RIGHT:  state="right";
                                                       x[length-1]+=20;
                                                       jfr.paintComponents(jfr.getGraphics());   
                                                       break;
                                    
                              case KeyEvent.VK_LEFT:  state="left";
                                                      x[length-1]-=20;
                                                      jfr.paintComponents(jfr.getGraphics());
                                                      break;
                                                      
                              case KeyEvent.VK_UP:   
                                                      // System.out.println("X in up= "+x);
                                                       state="up";
                                                       y[length-1]=y[length-1]-20;
                                                      jfr.paintComponents(jfr.getGraphics());
                                                      break;
                               case KeyEvent.VK_DOWN: state= "down";
                                                      jfr.paintComponents(jfr.getGraphics());
                                                      
                    }
                }
                 public void keyReleased(KeyEvent e){}
                 public void keyTyped(KeyEvent e){}
             
            });
         
        
         jfr.setVisible(true);
         
         new Thread() {
             int timer=1000;
             boolean flag=false;
             long copy=0;
			@Override public void run() {
			 
				while (ob.running) {
					try {
						Thread.sleep(timer);
						if(ob.score%100==0 &&  ob.score!=copy){timer-=50;System.out.println("Value of timer= "+timer);copy=ob.score;}
						jfr.repaint();
					} catch ( InterruptedException e ) {e.printStackTrace();}
				}
			}
		}.start();
    }
}
