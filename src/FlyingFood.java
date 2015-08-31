import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;

public class FlyingFood extends Food {
	public double xDirection;
	public double yDirection;

	public FlyingFood(int x, int y) {
		super(x, y);
		xDirection = -5;
		yDirection = 0;
	}

	public void move() {
		this.setX(this.getX()+xDirection);
		this.setY(this.getY()+yDirection);
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

	public void enemyRespawn() {
		if(this.getX() < -50 || this.getY() < -50 || this.getY() > Main.HEIGHT+50) {
			this.setX(-50);
		}
	}

	public void aim(Circle player, Circle enemy) {

		double opp = -player.getCenterY()+enemy.getCenterY();
		double adj = enemy.getCenterX()-player.getCenterX();
		double angle = Math.atan(opp/adj);
		xDirection = -2*Math.cos(angle);
		yDirection = -2*Math.sin(angle);
		this.setX(enemy.getCenterX()-enemy.getRadius());
		this.setY(enemy.getCenterY());
		this.getTransforms().add(new Rotate(angle*180/Math.PI, this.getX() + this.getWidth()/2, this.getY() + this.getHeight()/2));
	}

}
