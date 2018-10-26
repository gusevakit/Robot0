package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import Elements.Robot;
import log.Logger;

public class GameWindow extends JInternalFrame
{
    private final GameVisualizer m_visualizer;
    public void chageColorOfButtom(JButton buttom,boolean isOn) {
    	if(isOn) {
    		buttom.setBackground(Color.DARK_GRAY);
    	}
    	else {
    		buttom.setBackground(Color.LIGHT_GRAY);
    	}
    }
    public GameWindow() 
    {
        super("������� ����",true,true,true,true);
        m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        
        JMenuBar menuBar = new JMenuBar();
        
        JButton addRobot = new JButton("�������� ������");
        JButton addRect = new JButton("�������� �������������");
        JButton deleteRect = new JButton("������� �������������");
        JButton chooseRobots = new JButton("������� ������");
        JButton moveToTarget = new JButton("������ ��������");
        addRobot.addActionListener((event) -> {
        	m_visualizer.deleteRectangleIsOn=false;
        	m_visualizer.rectangleIsOn=false;
        	m_visualizer.chooseRobotsIsOn=false;
        	m_visualizer.moveIsOn=false;
        	chageColorOfButtom(deleteRect,m_visualizer.deleteRectangleIsOn);
        	chageColorOfButtom(addRect,m_visualizer.rectangleIsOn);
        	chageColorOfButtom(chooseRobots,m_visualizer.chooseRobotsIsOn);
        	chageColorOfButtom(moveToTarget,m_visualizer.moveIsOn);
        	Robot nextRobot = new Robot();
        	m_visualizer.robots.add(nextRobot);
        	Logger.debug("�������� ������");
        	
        });
        
        addRect.addActionListener((event) -> {
        	Logger.debug("������ ������ *�������� �������������*");
        	m_visualizer.deleteRectangleIsOn=false;
        	m_visualizer.chooseRobotsIsOn=false;
        	m_visualizer.moveIsOn=false;
        	chageColorOfButtom(deleteRect,m_visualizer.deleteRectangleIsOn);
        	chageColorOfButtom(moveToTarget,m_visualizer.moveIsOn);
        	m_visualizer.rectangleIsOn=!m_visualizer.rectangleIsOn;
        	chageColorOfButtom(addRect,m_visualizer.rectangleIsOn);
        	Rectangle nextRectangle = new Rectangle();
        	m_visualizer.rectangles.add(nextRectangle);      	
        });
        
        deleteRect.addActionListener((event) -> {
        	Logger.debug("������ ������ *������� �������������*");
        	m_visualizer.rectangleIsOn=false;
        	m_visualizer.chooseRobotsIsOn=false;
        	m_visualizer.moveIsOn=false;
        	chageColorOfButtom(addRect,m_visualizer.rectangleIsOn);
        	chageColorOfButtom(chooseRobots,m_visualizer.chooseRobotsIsOn);
        	chageColorOfButtom(moveToTarget,m_visualizer.moveIsOn);
        	m_visualizer.deleteRectangleIsOn=!m_visualizer.deleteRectangleIsOn;
        	chageColorOfButtom(deleteRect,m_visualizer.deleteRectangleIsOn);
        });
        
        chooseRobots.addActionListener((event) -> {
        	Logger.debug("������ ������ *������� ������*");
        	m_visualizer.rectangleIsOn=false;
        	m_visualizer.deleteRectangleIsOn=false;
        	m_visualizer.moveIsOn=false;
        	chageColorOfButtom(addRect,m_visualizer.rectangleIsOn);
        	chageColorOfButtom(deleteRect,m_visualizer.deleteRectangleIsOn);	
        	chageColorOfButtom(moveToTarget,m_visualizer.moveIsOn);
        	m_visualizer.chooseRobotsIsOn=!m_visualizer.chooseRobotsIsOn;
        	chageColorOfButtom(chooseRobots,m_visualizer.chooseRobotsIsOn);
        });
        moveToTarget.addActionListener((event) -> {
        	Logger.debug("������ ������ *�������� � ����*");
        	m_visualizer.rectangleIsOn=false;
        	m_visualizer.deleteRectangleIsOn=false;
        	m_visualizer.chooseRobotsIsOn=false;
        	chageColorOfButtom(addRect,m_visualizer.rectangleIsOn);
        	chageColorOfButtom(deleteRect,m_visualizer.deleteRectangleIsOn);	
        	chageColorOfButtom(chooseRobots,m_visualizer.chooseRobotsIsOn);
        	m_visualizer.moveIsOn=!m_visualizer.moveIsOn;
        	chageColorOfButtom(moveToTarget,m_visualizer.moveIsOn);
        });
        
        menuBar.add(addRobot);
        menuBar.add(addRect);
        menuBar.add(deleteRect);
        menuBar.add(chooseRobots);
        menuBar.add(moveToTarget);
        setJMenuBar(menuBar);
        pack();
        
    }
}
