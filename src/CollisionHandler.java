import java.util.Collection;
import javafx.scene.shape.Shape;

public class CollisionHandler {

	public boolean enemyHit(Collection<Actor> enemies, Collection<PlayerFood> playerFood) {
		for(Actor enemy : enemies) {
			for(PlayerFood playersFood : playerFood) {
				Shape intersect = Shape.intersect(enemy, playersFood);
				if (intersect.getBoundsInLocal().getWidth() != -1 && enemy.getLives() > 0) {
					enemy.updateColor(250, 250, 250);
					enemy.setLives(enemy.getLives()-1);
					playersFood.respawn();
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Keep's track of the player's three lives and
	 * the shield cheat
	 */
	public boolean playerHit(Actor player, Collection<FlyingFood> flyingFoods) {
		for(FlyingFood flyingFood : flyingFoods) {
			Shape intersect = Shape.intersect(player, flyingFood);
			if (intersect.getBoundsInLocal().getWidth() != -1) {
				player.setLives(player.getLives()-1);
				flyingFood.setX(flyingFood.getX()+Main.WIDTH);
				return true;
			}
		}
		return false;
	}

	public boolean foodHit(Collection<PlayerFood> playerFoods, Collection<FlyingFood> flyingFoods) {
		for (PlayerFood playerFood : playerFoods) {
			for(FlyingFood flyingFood : flyingFoods) {
				Shape intersect = Shape.intersect(playerFood, flyingFood);
				if (intersect.getBoundsInLocal().getWidth() != -1 && playerFood.getX() > 0) {
					playerFood.respawn();
					flyingFood.respawn();
					return true;
				}
			}
		}
		return false;
	}

}
