package gods.Board;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Used to load and store an image
 */
public class BufferedImageLoader
{

    private BufferedImage image;
    
    /**
     * Load an image from a file path
     * @param path: file path
     * @return a BufferedImage
     */
    public BufferedImage loadImage(String path){
        try{
            image = ImageIO.read(getClass().getResource(path));
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

}
