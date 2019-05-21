package gods.View;

import java.awt.*;
import javax.swing.*;
//import java.awt.Canvas;
//import java.awt.Container;
//import java.awt.Dimension;
//import java.awt.GridLayout;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
import gods.GameLoop;

public class Window extends Canvas
{
    private static final long serialVersionUID = -240840600533728354L;

//    public Window(int width, int height, String title, GameLoop gameLoop)
//	{
//      JFrame frame = new JFrame(title);
//      frame.setPreferredSize(new Dimension(width*2,height));
//      frame.setMaximumSize(new Dimension(width*2,height));
//      frame.setMinimumSize(new Dimension(width*2,height));
//      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//      frame.setResizable(false);
//      frame.setLocationRelativeTo(null);
//      
//      Container panel = frame.getContentPane();
//      panel.setLayout(new GridBagLayout());
//      GridBagConstraints c = new GridBagConstraints();
//
//      c.weightx = 0.5;
//      c.fill = GridBagConstraints.BOTH;
//      c.gridx = 0;
//      c.gridy = 0;
//      c.gridheight = 2;
//      panel.add(gameLoop, c);
//      frame.setVisible(true);
//      
//	}
    
	public Window(int width, int height, String title, GameLoop gameLoop)
	{
        JFrame frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(width,height));
        frame.setMaximumSize(new Dimension(width,height));
        frame.setMinimumSize(new Dimension(width,height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel(new GridLayout(0,1)); //Default layout manager is FlowLayout
        //You could change the layout here with panel.setLayout(new ..Layout);
        frame.getContentPane().add(panel);
        panel.add(gameLoop);
//        Canvas canvas = new Canvas();
//        panel.add(canvas);
        //frame.add(game);
        frame.setVisible(true);
	}
	
	
	
//	public Window(int width, int height, String title, Main main)
//	{
//        JFrame frame = new JFrame(title);
//        frame.setPreferredSize(new Dimension(width*2,height));
//        frame.setMaximumSize(new Dimension(width*2,height));
//        frame.setMinimumSize(new Dimension(width*2,height));
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setResizable(false);
//        frame.setLocationRelativeTo(null);
//        JPanel panel = new JPanel(new GridLayout(0,2)); //Default layout manager is FlowLayout
//        //You could change the layout here with panel.setLayout(new ..Layout);
//        frame.getContentPane().add(panel);
//        panel.add(main);
//        Canvas canvas = new Canvas();
//        panel.add(canvas);
//        //frame.add(game);
//        frame.setVisible(true);
//	}

}
