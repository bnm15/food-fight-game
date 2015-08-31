import javafx.scene.shape.Circle;

public class FlyingFood extends Food {

	public FlyingFood(int x, int y) {
		super(x, y);
	}

	public void offScreen() {
		if(this.getX() < -10) {
			this.setFill(ranColor());
			this.setX(Main.WIDTH+50);
			this.setY(ranNum((int) Main.HEIGHT-50) + 25);
		}
	}

	public void respawn() {
		this.setFill(ranColor());
		this.setX(Main.WIDTH+this.getX());
		this.setY(ranNum((int) Main.HEIGHT-50) + 25);
	}
	
	public void aim(Circle player) {
		double opposite = player.getCenterY()-this.getY();
		double adjacent = player.getCenterX()-this.getX();
		double rotation = Math.atan(opposite/adjacent);
		this.setRotate(rotation);
		double scale = 5/Math.hypot(opposite, adjacent);
		this.setX(this.getX()+adjacent*scale);
		this.setY(this.getY()+adjacent*scale);
	}

}
