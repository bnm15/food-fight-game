import java.util.Collection;
import javafx.scene.shape.Shape;


public class CollisionHandler {

    public static final int ENEMY_POINTS = 3;
    public static final int FOOD_POINTS = 1;

    private Player player;

    public CollisionHandler (Player player) {
        this.player = player;
    }

    public boolean enemyHit (Collection<Enemy> enemies, Collection<PlayerFood> playerFood) {
        if (player.getMyScore() >= FoodFight.LEVEL1_MAX_SCORE) {
            for (Enemy enemy : enemies) {
                for (PlayerFood playersFood : playerFood) {
                    Shape intersect = Shape.intersect(enemy, playersFood);
                    if (intersect.getBoundsInLocal().getWidth() != -1 && enemy.getMyLives() > 0) {
                        enemy.updateColor(250, 250, 250);
                        enemy.setMyLives(enemy.getMyLives() - 1);
                        playersFood.respawn();
                        player.setMyScore(player.getMyScore() + ENEMY_POINTS);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean playerHit (Collection<FlyingFood> flyingFoods) {
        for (FlyingFood flyingFood : flyingFoods) {
            Shape intersect = Shape.intersect(player, flyingFood);
            if (intersect.getBoundsInLocal().getWidth() != -1) {
                player.setMyLives(player.getMyLives() - 1);
                flyingFood.setX(flyingFood.getX() + Main.WIDTH);
                return true;
            }
        }
        return false;
    }

    public boolean foodHit (Collection<PlayerFood> playerFoods,
                            Collection<FlyingFood> flyingFoods) {
        for (PlayerFood playerFood : playerFoods) {
            for (FlyingFood flyingFood : flyingFoods) {
                Shape intersect = Shape.intersect(playerFood, flyingFood);
                if (intersect.getBoundsInLocal().getWidth() != -1 && playerFood.getX() > 0) {
                    playerFood.respawn();
                    flyingFood.respawn(flyingFoods.size() == FoodFight.BOSS_FOOD_NUM);
                    player.setMyScore(player.getMyScore() + FOOD_POINTS);
                    return true;
                }
            }
        }
        return false;
    }

}
