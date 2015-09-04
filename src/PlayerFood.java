import javafx.scene.shape.Circle;


public class PlayerFood extends Food {

    public static final int VELOCITY = 5;

    public PlayerFood () {
        super(DOCK, 0);
        setMyXDirection(VELOCITY);
    }

    public void respawn () {
        setX(DOCK);
    }

    private boolean isOffScreen () {
        return (getX() > Main.WIDTH + BOUNDS);
    }

    public void shoot (Circle player) {
        setFill(ranColor());
        setX(player.getCenterX() + player.getRadius());
        setY(player.getCenterY());
    }

    public void act () {
        if (isOffScreen()) {
            respawn();
        }
        else if (getX() != DOCK) {
            move();
        }
    }

}
