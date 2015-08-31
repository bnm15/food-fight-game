import java.util.ArrayList;
import java.util.Random;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


/**
 * This is the code for the design and controls of Food Fight
 * 
 * @author Brenna Milligan
 */
class FoodFight {

	public static final String TITLE = "Food Fight!";
	private static final int PLAYERMOVE = 10;

	private Scene myScene;
	private ArrayList<FlyingFood> flyingFood;
	private ArrayList<PlayerFood> playerFood;
	private Circle myPlayer;
	private ArrayList<Enemy> enemies;
	private Group root;
	private boolean start;
	private int lives;
	private int level1;
	private int heat;
	private boolean boss;
	private int enemyHeat;
	private int shieldCounter;
	private Circle shield;
	Random randomGenerator = new Random();


	public FoodFight() {
		flyingFood = new ArrayList<>();
		playerFood = new ArrayList<>();
		enemies = new ArrayList<>();
		start = true;
		lives = 3;
		level1 = 0;
		enemyHeat = 180;
		shieldCounter = 0;
		heat = 0;
		boss = false;
		myPlayer = new Circle(100, Main.HEIGHT/2, 40, Color.BROWN);
		for(int i = 0; i < 4; i++) {
			flyingFood.add(new FlyingFood(Main.WIDTH*(4+i)/4+50, randomGenerator.nextInt(Main.HEIGHT-50)+25));
			playerFood.add(new PlayerFood(-50, myPlayer.getCenterY()));
			if(i < 3) {
				enemies.add(new Enemy(Main.HEIGHT*(1+i)/3-75));
			}
		}
	}

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
		myScene = new Scene(root, width, height, Color.WHITE);
		// Create objects
		shield = new Circle(100, Main.HEIGHT/2, 55);
		shield.setFill(Color.rgb(20, 20, 100, .3));
		// Start text
		startScreen();
		// Respond to input
		myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		myScene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
		return myScene;
	}

	/**
	 * Sets up the user's start screen with instructions
	 */
	private void startScreen() {
		// Title
		Text title = new Text("Food Fight!");
		title.setX(Main.WIDTH/2 - title.getBoundsInLocal().getWidth()*2.2);
		title.setY(Main.HEIGHT/4);
		title.setFill(Color.DARKMAGENTA);
		title.setFont(Font.font ("Comic Sans MS", 55));
		root.getChildren().add(title);
		// Instructions
		Text instruct = new Text("Press Up and Down to move, Space to shoot\nHit 10 foods to move on to the Boss level\nYou have 3 lives");
		instruct.setX(Main.WIDTH/2 - instruct.getBoundsInLocal().getWidth());
		instruct.setY(Main.HEIGHT/4+75);
		instruct.setFill(Color.BLACK);
		instruct.setTextAlignment(TextAlignment.CENTER);
		instruct.setFont(Font.font ("Comic Sans MS", 30));
		root.getChildren().add(instruct);
		// Click to start
		Text t = new Text("Click anywhere to start");
		t.setX(Main.WIDTH/2 - t.getBoundsInLocal().getWidth()*1.6);
		t.setY(Main.HEIGHT/2+100);
		t.setFill(Color.DARKGREEN);
		t.setFont(Font.font ("Comic Sans MS", 40));
		root.getChildren().add(t);
	}

	/**
	 * Keep's track of the player's three lives and
	 * the shield cheat
	 */
	private void checkPlayerHit() {
		if(shieldCounter==0) {
			shield.setVisible(false);
			for(FlyingFood f : flyingFood) {
				Shape intersect = Shape.intersect(myPlayer, f);
				if (intersect.getBoundsInLocal().getWidth() != -1) {
					lives--;
					if(!boss){
						f.respawn();
					}
					else {
						f.setX(-50);
					}
				}
			}
		}
		else {
			shield.setVisible(true);
			shieldCounter--;
		}

		if (lives == 0) {
			gameOver();
		}
	}

	private void checkEnemyHit() {
		int sumLives = 0;
		for(Enemy e : enemies) {
			for(PlayerFood pf : playerFood) {
				Shape intersect = Shape.intersect(e, pf);
				if (intersect.getBoundsInLocal().getWidth() != -1) {
					e.lives--;
					pf.setX(-50);
				}
			}
			if(e.lives == 0) {
				root.getChildren().remove(e);
			}
			sumLives += e.lives;
		}
		if(sumLives == 0){
			gameWin();
		}
	}

	/**
	 * Clears the screen for the Game Over text
	 */
	private void gameOver() {
		root.getChildren().clear();
		myScene.setFill(Color.BLACK);
		Text t = new Text("GAME OVER");
		t.setX(Main.WIDTH/2 - t.getBoundsInLocal().getWidth()*2.2);
		t.setY(Main.HEIGHT/2);
		t.setFill(Color.WHITE);
		t.setFont(Font.font ("Comic Sans MS", 60));
		root.getChildren().add(t);
	}
	
	private void gameWin() {
		lives = -1;
		root.getChildren().clear();
		myScene.setFill(Color.GOLD);
		Text t = new Text("YOU WIN!!");
		t.setX(Main.WIDTH/2 - t.getBoundsInLocal().getWidth()*2.2);
		t.setY(Main.HEIGHT/2);
		t.setFill(Color.WHITE);
		t.setFont(Font.font ("Comic Sans MS", 60));
		root.getChildren().add(t);
	}

	/**
	 * Keeps track of the player's "hit food" points
	 */
	private void checkFoodHit() {
		for (PlayerFood pf : playerFood) {
			for(FlyingFood ff : flyingFood) {
				Shape intersect = Shape.intersect(pf, ff);
				if (intersect.getBoundsInLocal().getWidth() != -1) {
					level1++;
					if(!boss) {
						ff.respawn();
					}
					else {
						ff.setX(-50);
					}
					pf.setX(-50);
				}
			}
		}
	}

	private void initializeBoss() {
		boss = true;
		lives = 3;
		root.getChildren().clear();
		myPlayer.setCenterX(100);
		myPlayer.setCenterY(Main.HEIGHT/2);
		root.getChildren().add(myPlayer);
		root.getChildren().addAll(enemies);
		for(int i = 0; i < 4; i++) {
			playerFood.get(i).setX(-50);
			flyingFood.get(i).setX(-50);
		}
		root.getChildren().addAll(playerFood);
		for(int i = 0; i < 5; i++) {
			flyingFood.add(new FlyingFood(-50, 0));
		}
		root.getChildren().addAll(flyingFood);
		level1++;
	}

	private void enemyFire() {
		for(int i = 0; i < 3; i++) {
			if(enemyHeat == 180) {
				flyingFood.get(i).setX(enemies.get(i).getCenterX()-enemies.get(i).getRadius());
				flyingFood.get(i).setY(enemies.get(i).getCenterY());
			}
			else if(enemyHeat == 120) {
				flyingFood.get(i+3).setX(enemies.get(i).getCenterX()-enemies.get(i).getRadius());
				flyingFood.get(i+3).setY(enemies.get(i).getCenterY());
			}
			else if(enemyHeat == 60){
				flyingFood.get(i+6).setX(enemies.get(i).getCenterX()-enemies.get(i).getRadius());
				flyingFood.get(i+6).setY(enemies.get(i).getCenterY());
			}
			else if(enemyHeat == 0){
				enemyHeat = 181;
			}
		}
		enemyHeat--;
	}

	/**
	 * Change properties of shapes to animate them
	 * 
	 * Note, there are more sophisticated ways to animate shapes,
	 * but these simple ways work too.
	 */
	public void step (double elapsedTime) {
		if(!start) {
			if(level1 < 10) {
				for(int i = 0; i < 4; i++) {
					flyingFood.get(i).offScreen();
					flyingFood.get(i).move(-1);
					playerFood.get(i).offScreen();
					if(playerFood.get(i).getX() != -50) {
						playerFood.get(i).move(1);
					}
				}
			}
			else if(level1 == 10) {
				initializeBoss();
			}
			else {
				for(PlayerFood pf : playerFood) {
					pf.offScreen();
					if(pf.getX() != -50) {
						pf.move(1);
					}
				}
				enemyFire();
				for(FlyingFood ff : flyingFood) {
					ff.move(-1);
				}
				checkEnemyHit();
			}
		}
		checkPlayerHit();
		checkFoodHit();
		heat--;
	}


	// What to do each time a key is pressed
	private void handleKeyInput (KeyCode code) {
		switch (code) {
		case UP:
			if (myPlayer.getCenterY()-myPlayer.getRadius() >= 0) {
				myPlayer.setCenterY(myPlayer.getCenterY()-PLAYERMOVE);
				shield.setCenterY(shield.getCenterY()-PLAYERMOVE);
			}
			break;
		case DOWN:
			if (myPlayer.getCenterY()+myPlayer.getRadius() <= Main.HEIGHT) {
				myPlayer.setCenterY(myPlayer.getCenterY()+PLAYERMOVE);
				shield.setCenterY(shield.getCenterY()+PLAYERMOVE);
			}
			break;
		case SPACE:
			for(PlayerFood pf : playerFood) {
				if(pf.getX() == -50 && heat <= 0) {
					heat = 40;
					pf.setX(myPlayer.getCenterX()+myPlayer.getRadius());
					pf.setY(myPlayer.getCenterY());
					break;
				}
			}
			break;
		case Z:
			level1 = 10;
			break;
		case S:
			shieldCounter = 600;
			shield.setCenterX(myPlayer.getCenterX());
			shield.setCenterY(myPlayer.getCenterY());
			root.getChildren().add(shield);
			break;
		case X:
			for(Enemy e : enemies){
				e.lives = 0;
			}
		default:
			// do nothing
		}
	}

	// What to do each time a key is pressed
	private void handleMouseInput (double x, double y) {
		if (start) {
			start = false;
			root.getChildren().clear();
			root.getChildren().add(myPlayer);
			root.getChildren().addAll(flyingFood);
			root.getChildren().addAll(playerFood);
		}
	}
}
