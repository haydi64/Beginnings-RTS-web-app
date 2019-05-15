package gods;

import gods.Board.Square;

public class Camera
{
	private float x,y;

	public Camera(float xCoor, float yCoor)
	{
		this.x = xCoor;
		this.y = yCoor;
	}

    public void tick(Square tile){
        x += ((tile.getRow() * 50 - x) - Main.WIDTH / 2) * 0.05f;
        y += ((tile.getColumn() * 50 - y) - Main.HEIGHT / 2) * 0.05f;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
