import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class ScreenText extends Text {

    public ScreenText () {
        super();
    }

    public Text title () {
        Text title = new Text("Food Fight!");
        title.setX(Main.WIDTH / 2 - title.getBoundsInLocal().getWidth() * 2.2);
        title.setY(Main.HEIGHT / 4);
        title.setFill(Color.DARKMAGENTA);
        title.setFont(Font.font("Comic Sans MS", 55));
        return title;
    }

    public Text instructions () {
        Text instruct =
                new Text("Press Up and Down to move, Space to shoot\nHit 10 foods to move on to the Boss level\nYou have 3 lives");
        instruct.setX(Main.WIDTH / 2 - instruct.getBoundsInLocal().getWidth());
        instruct.setY(Main.HEIGHT / 4 + 75);
        instruct.setFill(Color.BLACK);
        instruct.setTextAlignment(TextAlignment.CENTER);
        instruct.setFont(Font.font("Comic Sans MS", 30));
        return instruct;
    }

    public Text start () {
        Text t = new Text("Click anywhere to start");
        t.setX(Main.WIDTH / 2 - t.getBoundsInLocal().getWidth() * 1.6);
        t.setY(Main.HEIGHT / 2 + 100);
        t.setFill(Color.DARKGREEN);
        t.setFont(Font.font("Comic Sans MS", 40));
        return t;
    }

    public Text bigWords (String message, String score) {
        Text t = new Text(message + "\n" + score);
        t.setX(Main.WIDTH / 2 - t.getBoundsInLocal().getWidth() * 2.2);
        t.setY(Main.HEIGHT / 2);
        t.setFill(Color.WHITE);
        t.setTextAlignment(TextAlignment.CENTER);
        t.setFont(Font.font("Comic Sans MS", 60));
        return t;
    }

    public Text counter (String text, int x) {
        Text t = new Text(text);
        t.setX(x);
        t.setY(50);
        t.setFill(Color.BLACK);
        t.setFont(Font.font("Comic Sans MS", 20));
        return t;
    }

}
