import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
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
		Group root = new Group();
		// Create a place to see the shapes
		myScene = new Scene(root, width, height, Color.BISQUE);
		// Respond to input
		myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		myScene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
		return myScene;
	}

	/**
	 * Change properties of shapes to animate them
	 * 
	 * Note, there are more sophisticated ways to animate shapes,
	 * but these simple ways work too.
	 */
	public void step (double elapsedTime) {

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
