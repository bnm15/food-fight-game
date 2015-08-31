import javafx.scene.shape.Circle;

public class PlayerFood extends Food {

	public PlayerFood(double x, double y) {
		super(x, y);
	}

	public void offScreen() {
		if(this.getX() > 950) {
			this.setFill(ranColor());
			this.setX(-50);
		}
	}
	
	public void shoot(Circle player) {
		this.setX(player.getCenterX()+player.getRadius());
		this.setY(player.getCenterY());
		this.setFill(ranColor());
	}

}
