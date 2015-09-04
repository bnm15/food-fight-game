import javafx.scene.shape.Circle;


public class FlyingFood extends Food {

    public static final int LEVEL1_VELOCITY = -5;
    public static final int BOSS_VELOCITY = -3;

    public FlyingFood (int x) {
        super(x, 0);
        setY(ranNum(Main.HEIGHT - 2 * Player.RADIUS) + Player.RADIUS);
        setMyXDirection(LEVEL1_VELOCITY);
    }

    public void respawn (boolean bossLevel) {
        if (bossLevel) {
            setX(DOCK);
        }
        else {
            setFill(ranColor());
            setX(Main.WIDTH + getX());
            setY(ranNum(Main.HEIGHT - 2 * Player.RADIUS) + Player.RADIUS);
        }
    }

    private boolean isOffScreen () {
        return (getX() <= -BOUNDS || getY() < -BOUNDS || getY() > Main.HEIGHT + BOUNDS);
    }

    public void act (boolean enemy) {
        if (isOffScreen()) {
            respawn(enemy);
        }
        else {
            move();
        }
    }

    public void aim (Circle player, Circle enemy) {
        double opp = enemy.getCenterY() - player.getCenterY();
        double adj = enemy.getCenterX() - player.getCenterX();
        double angle = Math.atan(opp / adj);
        setMyXDirection(BOSS_VELOCITY * Math.cos(angle));
        setMyYDirection(BOSS_VELOCITY * Math.sin(angle));
        setX(enemy.getCenterX() - enemy.getRadius());
        setY(enemy.getCenterY());
    }

}
