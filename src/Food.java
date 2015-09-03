import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Food extends Rectangle {
	public double xDirection;
	public double yDirection;
	
	// Constructor
	public Food(double x, double y) {
		super(x, y, 30, 15);
		setFill(ranColor());
		yDirection = 0;
	}
	
	protected Color ranColor() {
		Color color = Color.rgb(ranNum(151)+100, ranNum(151+100), ranNum(151)+100);
		return color;
	}
	
	protected int ranNum(int limit) {
		Random randomGenerator = new Random();
		int result = randomGenerator.nextInt(limit);
		return result;
	}
	
	public void move() {
		setX(getX()+xDirection);
		setY(getY()+yDirection);
	}

}
