import javafx.scene.shape.Circle;

public class PlayerFood extends Food {

	public PlayerFood(double x, double y) {
		super(x, y);
		xDirection = 5;
	}

	public void respawn() {
		setFill(ranColor());
		setX(FoodFight.FOOD_DOCK);
	}
	
	public boolean isOffScreen() {
		return (getX() > Main.WIDTH + 50);
	}

	public void shoot(Circle player) {
		setX(player.getCenterX()+player.getRadius());
		setY(player.getCenterY());
		setFill(ranColor());
	}

	public void act() {
		if(isOffScreen()) {
			respawn();
		}
		else if(getX() != FoodFight.FOOD_DOCK) {
			move();
		}
	}

}
