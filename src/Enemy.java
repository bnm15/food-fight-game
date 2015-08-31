import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Enemy extends Circle {
	public int lives;
	
	public Enemy(int y) {
		super(Main.WIDTH-200, y, 40, Color.DARKGREY);
		lives = 3;
	}

}
