package gods;

import static java.awt.Color.black;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;
import gods.Board.RenderObject;
import gods.Board.Square;
import gods.Entities.Actions;
import gods.Entities.Building;
import gods.Entities.GameObject;
import gods.Entities.Unit;
import gods.Game.Game;
import gods.View.Camera;

public class GameLoop extends Canvas implements Runnable
{

    private static final long serialVersionUID = 1550691097823471818L;
    public static final int WIDTH = 950 * 2, HEIGHT = WIDTH / 20 * 9 ;
    private Thread thread;
    private boolean running = false;

    private Camera camera;
    private Game game;
    
    public GameLoop() {
    	camera = new Camera(0, 0);
    	game = new Game();
    	this.addKeyListener(new KeyInput(game));
    }
	
    public synchronized void start() {
    	running = true;
    	thread = new Thread(this);
    	thread.start();
    }
    
    public synchronized void stop() {
    	running = false;
    	try {
    		thread.join();
    	} catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
	@Override
	public void run()
	{
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now -lastTime)/ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                delta--;
            }
            if(running)
                render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                frames = 0;
            }
        }
        stop();
	}
	
	private void tick() {
        try{
            camera.tick(game.getSelectedSquare());
        }catch (Exception e){
            e.printStackTrace();
        }
	}
	
	private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs==null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;

        g.setColor(black);
        g.fillRect(0,0,WIDTH,HEIGHT);
        
        int offset = 150;

        g2d.translate(-camera.getX() - offset,-camera.getY() - offset);

        game.render(g);

        g2d.translate(camera.getX() + offset,camera.getY() + offset);
        
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, WIDTH, 50);
        
        game.renderInfo(g);
        

        g.dispose();
        bs.show();
	}

}
