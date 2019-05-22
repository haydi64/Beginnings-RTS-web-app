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
        frame.setVisible(true);
	}

}
