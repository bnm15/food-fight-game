import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class Actor extends Circle {

    private int myLives;
    private int myHeat;

    public Actor (int x, int y, int radius, Color color, int heat) {
        super(x, y, radius, color);
        myHeat = heat;
    }

    public int getMyLives () {
        return myLives;
    }

    public void setMyLives (int lives) {
        myLives = lives;
    }

    public int getMyHeat () {
        return myHeat;
    }

    public void setMyHeat (int heat) {
        myHeat = heat;
    }

    public boolean isDead () {
        return myLives == 0;
    }

    public void checkHeat () {
        if (myHeat > 0) {
            myHeat--;
        }
    }

}
