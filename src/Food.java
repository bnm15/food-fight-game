import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Food extends Rectangle {

    public static final int WIDTH = 30;
    public static final int HEIGHT = 15;
    public static final int COLOR_RANGE = 151;
    public static final int COLOR_MIN = 251 - COLOR_RANGE;
    public static final int DOCK = -50;
    public static final int BOUNDS = 15;

    private double myXDirection;
    private double myYDirection;

    public Food (double x, double y) {
        super(x, y, WIDTH, HEIGHT);
        setFill(ranColor());
        myYDirection = 0;
    }

    public void setMyXDirection (double myXDirection) {
        this.myXDirection = myXDirection;
    }

    public void setMyYDirection (double myYDirection) {
        this.myYDirection = myYDirection;
    }

    protected Color ranColor () {
        Color color =
                Color.rgb(ranNum(COLOR_RANGE) + COLOR_MIN, ranNum(COLOR_RANGE) + COLOR_MIN,
                          ranNum(COLOR_RANGE) + COLOR_MIN);
        return color;
    }

    protected int ranNum (int limit) {
        Random randomGenerator = new Random();
        int result = randomGenerator.nextInt(limit);
        return result;
    }

    public void move () {
        setX(getX() + myXDirection);
        setY(getY() + myYDirection);
    }

}
