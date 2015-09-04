import javafx.scene.paint.Color;


public class Shield extends Actor {

    public static final int RADIUS = Player.RADIUS * 4 / 3;
    public static final int MAX_HEAT = Main.FRAMES_PER_SECOND * 10;

    private Player myPlayer;

    public Shield (Player player) {
        super((int) player.getCenterX(), (int) player.getCenterY(), RADIUS,
              Color.rgb(20, 20, 100, .3), 0);
        myPlayer = player;
    }

    private void setVisibility () {
        if (getMyHeat() > 0) {
            setVisible(true);
        }
        else {
            setVisible(false);
        }
    }

    private void move () {
        setCenterX(myPlayer.getCenterX());
        setCenterY(myPlayer.getCenterY());
    }

    public void act () {
        move();
        setVisibility();
        checkHeat();
    }

}
