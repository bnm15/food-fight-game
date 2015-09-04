import javafx.scene.paint.Color;


public class Player extends Actor {

    public static final int MAX_HEAT = 40;
    public static final int X_COORD = 100;
    public static final int RADIUS = 40;
    public static final int MAX_LIVES = 3;
    public static final int PLAYER_MOVE = 10;

    private int myScore;

    public Player () {
        super(X_COORD, Main.HEIGHT / 2, RADIUS, Color.rgb(200, 100, 100), 0);
        myScore = -1;
        setMyLives(MAX_LIVES);
    }

    public int getMyScore () {
        return myScore;
    }

    public void setMyScore (int myScore) {
        this.myScore = myScore;
    }

}
