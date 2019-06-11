package gods;

import static java.awt.Color.black;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import gods.Board.BufferedImageLoader;
import gods.Board.RenderObject;
import gods.Board.Square;
import gods.Board.Terrain;
import gods.Entities.Actions;
import gods.Entities.Building;
import gods.Entities.GameObject;
import gods.Entities.Unit;
import gods.Game.Game;
import gods.View.Camera;
import gods.View.Direction;
import gods.View.KeyInput;
import gods.View.StartMenu;
import gods.save.Save;

/**
 * This class creates the loop that runs the game
 * It is also a canvas object that renders the game to the window
 */
public class GameLoop extends Canvas implements Runnable
{

    private static final long serialVersionUID = 1550691097823471818L;
    public static final int WIDTH = 950 * 2, HEIGHT = WIDTH / 20 * 9 ;
    private Thread thread;
    private boolean running = false;

    private Camera camera;
    private Game game;
    private Scene currentScene;
    private KeyListener keyListener;
    private StartMenu menu;
    
    public GameLoop() {
    	RenderObject.initializeIcons();
    	camera = new Camera(0, 0);
//    	game = new Game();
        BufferedImageLoader loader = new BufferedImageLoader();
        BufferedImage map = loader.loadImage("/resources/maps/map1.png");
    	game = new Game(loadMap(map));
    	menu = new StartMenu();
//    	this.addKeyListener(new KeyInput(game));
    	currentScene = Scene.START;
    	keyListener = new MenuKeyInput(this);
    	this.addKeyListener(keyListener);
    }
	
    /**
     * Starts the game thread that updates and renders all the game objects
     */
    public synchronized void start() {
    	running = true;
    	thread = new Thread(this);
    	thread.start();
    }
    
    /**
     * Closes the thread when the game is closed
     */
    public synchronized void stop() {
    	running = false;
    	try {
    		thread.join();
    	} catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    /**
     * This is the game loop that runs in the separate thread
     */
	@Override
	public void run()
	{
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 30.0;
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
	
	/**
	 * Updates every frame to update game objects
	 */
	private void tick() {
        try{
            camera.tick(game.getSelectedSquare());
        }catch (Exception e){
            e.printStackTrace();
        }
	}
	
	/**
	 * Renders each game object
	 */
	private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs==null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        g.setColor(black);
        g.fillRect(0,0,WIDTH,HEIGHT);
        
        if(currentScene == Scene.GAME)
        	renderGame(g);
        else // current scene == Scene.Start
        	menu.render(g);
        	
        g.dispose();
        bs.show();
	}
	
	private void renderGame(Graphics g)
	{
        Graphics2D g2d = (Graphics2D) g;
        int offset = 150;

        //First translate everything to the origin
        g2d.translate(-camera.getX() - offset,-camera.getY() - offset);

        //Render all the game objects
        game.render(g);

        //Translate everything back to the camera's position
        g2d.translate(camera.getX() + offset,camera.getY() + offset);
        
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, WIDTH, 50);
        game.renderInfo(g);
        game.renderMessage(g);
        
		
	}
	
	public void cycleButtons(Direction dir)
	{
		menu.cycleActions(dir);
	}
	
	public void selectButton()
	{
		this.currentScene = menu.selectButton();
		if(currentScene == Scene.GAME)
		{
			this.removeKeyListener(keyListener);
			keyListener = new KeyInput(game);
			this.addKeyListener(keyListener);
		}
		else if(currentScene == Scene.LOAD)
		{
			System.out.println("Load game here");
			Game g = null;
			game = Save.restore(g, "src/resources/saves/save.ser");
			currentScene = Scene.GAME;
			//Game g = new game();
			//game = Save.restore(g, filepath);
		}
	}
	
	private Map<Square, Terrain> loadMap(BufferedImage img)
	{
		Map<Square, Terrain> terrainMap = new HashMap<Square, Terrain>();

        int w = img.getWidth();
        int h = img.getHeight();
        for(int xx = 0; xx < w; xx++){
            for(int yy = 00; yy < h; yy++){
                Color color = new Color(img.getRGB(xx,yy));
                Terrain terrain = null;
                for(Terrain item: Terrain.values()){
                    if(color.equals(item.getColor()))
                    	terrain = item;
                }
                terrainMap.put(new Square(xx, yy), terrain);
            }
        }
		return terrainMap;
	}

}
