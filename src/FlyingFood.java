import java.util.Random;
import javafx.scene.shape.Circle;

public class FlyingFood extends Food {

	public FlyingFood(int x) {
		super(x, (new Random()).nextInt(Main.HEIGHT-50)+25);
		xDirection = -5;
	}

	public void respawn(boolean enemy) {
		if(enemy) {
			setX(FoodFight.FOOD_DOCK);
		}
		else {
			setFill(ranColor());
			setX(Main.WIDTH+getX());
			setY(ranNum((int) Main.HEIGHT-50) + 25);
		}
	}

	public boolean isOffScreen() {
		return (getX() <= -15 || getY() < -5 || getY() > Main.HEIGHT+5);
	}

	public void act(boolean enemy) {
		if(isOffScreen()) {
			respawn(enemy);
		}
		else {
			move();
		}
	}

	public void aim(Circle player, Circle enemy) {
		double opp = -player.getCenterY()+enemy.getCenterY();
		double adj = enemy.getCenterX()-player.getCenterX();
		double angle = Math.atan(opp/adj);
		xDirection = -3*Math.cos(angle);
		yDirection = -3*Math.sin(angle);
		setX(enemy.getCenterX()-enemy.getRadius());
		setY(enemy.getCenterY());
	}

}
