import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Enemy extends Circle {
	public int lives;
	public int heat;
	
	public Enemy(int y) {
		super(Main.WIDTH-200, y, 40, Color.BLACK);
		lives = 3;
		heat = 180;
	}
	
	public void updateColor() {
		this.setFill(Color.rgb(250/lives, 250/lives, 250/lives));
	}

}
