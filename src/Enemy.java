import javafx.scene.paint.Color;


public class Enemy extends Actor {

    public static final int MAX_LIVES = 3;
    public static final int RADIUS = 40;
    public static final int MAX_HEAT = 130;

    public Enemy (int y) {
        super(Main.WIDTH - 200, y, RADIUS, Color.BLACK, MAX_HEAT);
        setMyLives(MAX_LIVES);
    }

    public void updateColor (int r, int g, int b) {
        setFill(Color.rgb(r / getMyLives(), g / getMyLives(), b / getMyLives()));
    }

}
