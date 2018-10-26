package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import Elements.*;

public class GameVisualizer extends JPanel
{
    private final Timer m_timer = initTimer();
    ArrayList<Robot> robots = new ArrayList<>();
    ArrayList<Robot> activeRobots = new ArrayList<>();
    ArrayList<Rectangle> rectangles = new ArrayList<>();
    Point topRect=null;
    Point bottomRect = null;
   
    private final Target target = new Target();
    Robot robot = new Robot();

    public boolean rectangleIsOn;
    public boolean deleteRectangleIsOn;
    public boolean addRobotIsOn;
    public boolean chooseRobotsIsOn;
    public boolean moveIsOn;
    
    private static Timer initTimer() 
    {
        Timer timer = new Timer("events generator", true);
        return timer;
    }
      
    public GameVisualizer() 
    {
    	
    	robot.positionX=90;
    	robot.positionY=90;
    	robots.add(robot);
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                onRedrawEvent();
            }
        }, 0, 50);
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run() {
            	if (moveIsOn) {
                    onModelUpdateEvent();
                }
            }
        }, 0, 10);
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
            	if(rectangleIsOn) {
            		pointOfRectangle(e);
            	}
            	else if (deleteRectangleIsOn) {
            		deleteRectangle(e);
            	}
            	else if (chooseRobotsIsOn) {
            		robotContainsPoint(e);
            	}
            	else {
            		target.setTargetPosition(e.getPoint());
            	}
                repaint();
            }
        });
        setDoubleBuffered(true);
    }

    public void pointOfRectangle(MouseEvent e) {
    	if(topRect==null) {
			topRect = new Point(e.getX(), e.getY());
		}
		else {
			bottomRect = new Point(e.getX(), e.getY());
			Rectangle rect = new Rectangle(Math.min((int)topRect.getX(), (int)bottomRect.getX()),Math.min((int)topRect.getY(), (int)bottomRect.getY()),(int)Math.abs(topRect.getX()-bottomRect.getX()),(int)Math.abs(topRect.getY()-bottomRect.getY()));
			rectangles.add(rect);
			topRect = null;
			}
    }
    public void deleteRectangle(MouseEvent e) {
    	List<Rectangle> qw=rectangles;
    	Collections.reverse(qw);
    	for(Rectangle rect : qw)
    	{
    		if(rect.contains(e.getX(),e.getY())) {
    			rectangles.remove(rect);
    			return;
    		}
    	}
    }
    public void robotContainsPoint(MouseEvent e) {	
    	for(Robot rob : robots)
    	{
    		if(rob.contains(e.getX(),e.getY())) {
    			this.robot = rob;
    			break;
    		}
    	}
    }
    protected void onRedrawEvent()
    {
        EventQueue.invokeLater(this::repaint);
    }

    private static double distance(double x1, double y1, double x2, double y2)
    {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }
    
    private static double angleTo(double fromX, double fromY, double toX, double toY)
    {
        double diffX = toX - fromX;
        double diffY = toY - fromY;
        
        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }
    
    protected void onModelUpdateEvent()
    {
	        double distance = distance(target.positionX, target.positionY, 
	            robot.positionX, robot.positionY);
	        if (distance < 0.5)
	        {
	            return;
	        }
	        double velocity = robot.maxVelocity; 
	        double angleToTarget = angleTo(robot.positionX, robot.positionY, target.positionX, target.positionY);
	        double angularVelocity = 0;
	        if (angleToTarget > robot.direction)
	        {
	            angularVelocity = robot.maxAngularVelocity;
	        }
	        if (angleToTarget < robot.direction)
	        {
	            angularVelocity = -robot.maxAngularVelocity;
	        }

	        if (Math.abs(angleToTarget - robot.direction) < 0.01)
	            robot.moveRobot(velocity, angularVelocity, 10);
	        else
	            robot.moveRobot(0, angularVelocity, 10);
    }
    
    public static double applyLimits(double value, double min, double max)
    {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    public static double asNormalizedRadians(double angle)
    {
        while (angle < 0)
        {
            angle += 2*Math.PI;
        }
        while (angle >= 2*Math.PI)
        {
            angle -= 2*Math.PI;
        }
        return angle;
    }
    
    private static int round(double value)
    {
        return (int)(value + 0.5);
    }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g; 
      for (Robot robot : robots) {
      	drawRobot(g2d, round(robot.positionX), round(robot.positionY), robot.direction);
      }
        drawTarget(g2d, target.positionX, target.positionY);
        for(Rectangle rect : rectangles) {
        	drawRectangle(g2d,rect.x,rect.y,rect.width,rect.height);
        }
    }
    
    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2)
    {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }
    
    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2)
    {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }
    
    private void drawRobot(Graphics2D g, int x, int y, double direction)
    {
        int robotCenterX = x; 
        int robotCenterY = y;
        AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY);
        g.setTransform(t);
        g.setColor(Color.BLUE);
        fillOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX  + 10, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX  + 10, robotCenterY, 5, 5);
    }
    
    private void drawTarget(Graphics2D g, int x, int y)
    {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0); 
        g.setTransform(t);
        g.setColor(Color.RED);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
        
    }
    private void drawRectangle(Graphics2D g, int x,int y,int width,int height)
    {
        g.setColor(Color.BLACK);
        g.fillRect(x,y,width,height);
        g.setColor(Color.CYAN);
        g.drawRect(x, y, width, height);
        
    }
}
