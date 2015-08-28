import java.util.Random;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


/**
 * This is the code for the design and controls of Food Fight
 * 
 * @author Brenna Milligan
 */
class FoodFight {
	public static final String TITLE = "Food Fight!";

	private Scene myScene;
	private Circle myPlayer;
	private ImageView window1;
	private ImageView window2;
	private Rectangle myFood1;
	private Rectangle myFood2;
	private Rectangle myFood3;
	private Group root;


	/**
	 * Returns name of the game.
	 */
	public String getTitle () {
		return TITLE;
	}

	/**
	 * Create the game's scene
	 */
	public Scene init (int width, int height) {
		// Create a scene graph to organize the scene
		root = new Group();
		// Create a place to see the shapes
		myScene = new Scene(root, width, height, Color.BISQUE);
		// Create objects
		myPlayer = new Circle(100, myScene.getHeight()/2, 40, Color.BROWN);
		myFood1 = new Rectangle(myScene.getWidth()+50, ranNum((int) myScene.getHeight()-50) + 25, 30, 15);
		myFood1.setFill(ranColor());
		myFood2 = new Rectangle(myScene.getWidth()*4/3+50, ranNum((int) myScene.getHeight()-50) + 25, 30, 15);
		myFood2.setFill(ranColor());
		myFood3 = new Rectangle(myScene.getWidth()*5/3+80, ranNum((int) myScene.getHeight()-50) + 25, 30, 15);
		myFood3.setFill(ranColor());
		// Add to scene
		root.getChildren().add(myPlayer);
		root.getChildren().add(myFood1);
		root.getChildren().add(myFood2);
		root.getChildren().add(myFood3);
		// Respond to input
		myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		myScene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
		return myScene;
	}
	
	private void updateFood(Rectangle r) {
		if(r.getX() < -10) {
			int height = ranNum((int) myScene.getHeight()-50) + 25;
			r.setFill(ranColor());
			r.setX(myScene.getWidth()+50);
			r.setY(height);
		}
	}
	
	private Color ranColor() {
		Color color = Color.rgb(ranNum(151)+100, ranNum(151+100), ranNum(151)+100);
		return color;
	}
	
	private int ranNum(int limit) {
		Random randomGenerator = new Random();
		int result = randomGenerator.nextInt(limit);
		return result;
	}
	
	private void makeWindows() {
		Image image = new Image(getClass().getClassLoader().getResourceAsStream("duke.gif"));
		window1 = new ImageView(image);
		window2 = new ImageView(image);
		window1.setX(myScene.getWidth()/3);
		window1.setY(myScene.getHeight()-50);
		window2.setX(myScene.getWidth()*2/3);
		window2.setY(myScene.getHeight()-50);
		root.getChildren().add(window1);
		root.getChildren().add(window2);
	}
	
	private void moveFood(Rectangle f) {
		f.setX(f.getX()-5);
	}

	/**
	 * Change properties of shapes to animate them
	 * 
	 * Note, there are more sophisticated ways to animate shapes,
	 * but these simple ways work too.
	 */
	public void step (double elapsedTime) {
		moveFood(myFood1);
		updateFood(myFood1);
		moveFood(myFood2);
		updateFood(myFood2);
		moveFood(myFood3);
		updateFood(myFood3);

	}


	// What to do each time a key is pressed
	private void handleKeyInput (KeyCode code) {
		switch (code) {
		case RIGHT:
			break;
		case LEFT:
			break;
		case UP:
			break;
		case DOWN:
			break;
		default:
			// do nothing
		}
	}

	// What to do each time a key is pressed
	private void handleMouseInput (double x, double y) {
	}
}
