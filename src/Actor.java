import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Actor extends Circle {
	public static final int RADIUS = 40;
	public final int MAX_LIVES = 3;
	
	
	private int lives;
	private int heat;
	
	public Actor(int x, int y, Color color, int heat) {
		super(x, y, RADIUS, color);
		this.lives = MAX_LIVES;
		this.heat = heat;
	}
	
	public boolean isDead() {
		return lives==0;
	}
	
	public void updateColor(int r, int g, int b) {
		this.setFill(Color.rgb(r/getLives(), g/getLives(), b/getLives()));
	}
	
	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public int getHeat() {
		return heat;
	}

	public void setHeat(int heat) {
		this.heat = heat;
	}

}
