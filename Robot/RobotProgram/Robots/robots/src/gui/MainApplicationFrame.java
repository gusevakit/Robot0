package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Elements.Robot;
import log.Logger;

/**
 * Что требуется сделать:
 * 1. Метод создания меню перегружен функционалом и трудно читается. 
 * Следует разделить его на серию более простых методов (или вообще выделить отдельный класс).
 *
 */
public class MainApplicationFrame extends JFrame 
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    ArrayList<LogWindow> logWindows = new ArrayList<>();

    ArrayList<GameWindow> gameWindows = new ArrayList<>();
    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setContentPane(desktopPane);
      
        
        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        
        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(800,  500);
        addWindow(gameWindow);

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    this.addWindowListener(new WindowListener() { 
		@Override
		public void windowOpened(WindowEvent e) {
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
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void windowClosing(WindowEvent e) {
			Object[] options = { "��", "���" };
            int n = JOptionPane
                    .showOptionDialog(e.getWindow(), "������� ����?",
                            "�������������", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, options,
                            options[0]);
            if (n == JOptionPane.YES_OPTION ) {
                e.getWindow().setVisible(false);
            }
		}
		
		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
	});
    
    
}
    protected LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10,10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("�������� ��������");
        return logWindow;
    }
    
    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
    
//    protected JMenuBar createMenuBar() {
//        JMenuBar menuBar = new JMenuBar();
// 
//        //Set up the lone menu.
//        JMenu menu = new JMenu("Document");
//        menu.setMnemonic(KeyEvent.VK_D);
//        menuBar.add(menu);
// 
//        //Set up the first menu item.
//        JMenuItem menuItem = new JMenuItem("New");
//        menuItem.setMnemonic(KeyEvent.VK_N);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_N, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("new");
////        menuItem.addActionListener(this);
//        menu.add(menuItem);
// 
//        //Set up the second menu item.
//        menuItem = new JMenuItem("Quit");
//        menuItem.setMnemonic(KeyEvent.VK_Q);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("quit");
////        menuItem.addActionListener(this);
//        menu.add(menuItem);
// 
//        return menuBar;
//    }
    
    private JMenuBar generateMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu lookAndFeelMenu = new JMenu("�����������");
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
        		"��������� ������������������� �����");
        
        {
            JMenuItem systemLookAndFeel = new JMenuItem("��������� �����", KeyEvent.VK_S);
            systemLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                this.invalidate();
            });
            lookAndFeelMenu.add(systemLookAndFeel);
        }

        {
            JMenuItem crossplatformLookAndFeel = new JMenuItem("������������� �����", KeyEvent.VK_S);
            crossplatformLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                this.invalidate();
            });
            lookAndFeelMenu.add(crossplatformLookAndFeel);
        }

        JMenu testMenu = new JMenu("�����");
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                "��������� � ���");
        
        {
            JMenuItem addLogMessageItem = new JMenuItem("��������� � ���", KeyEvent.VK_S);
            addLogMessageItem.addActionListener((event) -> {
                Logger.debug("����� ������");
            });
            testMenu.add(addLogMessageItem);
        }
        JMenuItem exitButton = new JMenuItem("�����", KeyEvent.VK_S);
        exitButton.addActionListener((event) -> {
      	int n=JOptionPane.showConfirmDialog(
      			exitButton,
      			"Are you sure you want to close the window?",
      			"Dialog window",
      			JOptionPane.YES_NO_OPTION,
      			JOptionPane.QUESTION_MESSAGE);
          if (n==JOptionPane.YES_OPTION)
      		{
          		this.dispose();
      		}
        });

        JMenuItem addLogWindow = new JMenuItem("�������� log ����", KeyEvent.VK_A);
        addLogWindow.addActionListener((event) -> {
        	LogWindow nextLogWindow = createLogWindow();
            addWindow(nextLogWindow);
            logWindows.add(nextLogWindow);
            Logger.debug("�������� log ����");
        });
        JMenuItem addGameWindow = new JMenuItem("�������� ������� ����", KeyEvent.VK_A);
        addGameWindow.addActionListener((event) -> {
        	GameWindow nextGameWindow = new GameWindow();
        	nextGameWindow.setSize( 500,500);
            addWindow(nextGameWindow);
            gameWindows.add(nextGameWindow);
            Logger.debug("�������� game ����");
        });
        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
        
        menuBar.add(exitButton);
        menuBar.add(addGameWindow);
        menuBar.add(addLogWindow);
      
        
        return menuBar;
    }
    
    private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (ClassNotFoundException | InstantiationException
            | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            // just ignore
        }
    }
}
