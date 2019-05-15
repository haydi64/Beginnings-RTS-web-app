package gods;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import gods.Board.Board;
import gods.Game.Game;

import static java.awt.Color.black;

public class Main extends Canvas implements Runnable {

    private static final long serialVersionUID = 1550691097823471818L;
    public static final int WIDTH = 950, HEIGHT = WIDTH / 12 * 9 ;
    private Thread thread;
    private boolean running = false;

//    private Map map;
//    private BufferedImage level = null;
    private Camera camera;
    private Game game;
    
    public Main() {
    	new Window(WIDTH, HEIGHT, "Game", this);
    	camera = new Camera(0, 0);
    	game = new Game();
    	this.addKeyListener(new KeyInput(game));
    	start();
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

        g2d.translate(-camera.getX(),-camera.getY());

        game.render(g);
        //map.selectedTile.setSelected(true,g);

        g2d.translate(camera.getX(),camera.getY());

        g.dispose();
        bs.show();
	}
	
	
	public static void main(String args[]) { new Main(); }

}
