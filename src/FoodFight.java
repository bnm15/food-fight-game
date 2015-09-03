import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


/**
 * This is the code for the design and controls of Food Fight
 * 
 * @author Brenna Milligan
 */
class FoodFight {

	public static final int FOOD_DOCK = -50;
	public static final String TITLE = "Food Fight!";
	public static final int PLAYER_MOVE = 10;
	public static final int PLAYER_GUN_HEAT = 40;
	public static final int ENEMY_GUN_HEAT = 180;
	public static final int SHIELD_HEAT = 300;
	public static final int LEVEL1_MAX_SCORE = 10;
	public static final int NUMBER_OF_FOOD = 4;
	public static final int NUMBER_OF_ENEMIES = 3;

	private Scene myScene;
	private Group root;
	private ArrayList<FlyingFood> myFlyingFood;
	private ArrayList<PlayerFood> myPlayerFood;
	private ArrayList<Actor> myEnemies;
	private Actor myPlayer;
	private int myScore;
	private Text myScoreBoard;
	private Text myLifeBoard;
	private Shield myShield;


	public FoodFight() {
		myFlyingFood = new ArrayList<>();
		myPlayerFood = new ArrayList<>();
		myEnemies = new ArrayList<>();
		myPlayer = new Actor(100, Main.HEIGHT/2, Color.rgb(200, 100, 100), 0);
		myScore = -1;
		myScoreBoard = new ScreenText().counter("Score: ", myScore, Main.WIDTH - 110);
		myLifeBoard = new ScreenText().counter("Lives: ", myPlayer.getLives(), 35);
		myShield = new Shield();
		for(int i = 0; i < NUMBER_OF_FOOD; i++) {
			myFlyingFood.add(new FlyingFood(Main.WIDTH*(4+i)/4+50));
			myPlayerFood.add(new PlayerFood(FOOD_DOCK, myPlayer.getCenterY()));
			if(i < NUMBER_OF_ENEMIES) {
				myEnemies.add(new Actor(Main.WIDTH-200, Main.HEIGHT*(1+i)/3-75, Color.BLACK, ENEMY_GUN_HEAT));
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
		startScreen();
		myShield.setVisible(false);
		// Respond to input
		myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		myScene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
		return myScene;
	}

	private void startScreen() {
		ScreenText screenText = new ScreenText();
		root.getChildren().add(screenText.title());
		root.getChildren().add(screenText.instructions());
		root.getChildren().add(screenText.start());
	}

	private void initializeBossLevel() {
		root.getChildren().clear();
		myPlayer.setLives(myPlayer.MAX_LIVES);
		myPlayer.setCenterY(Main.HEIGHT/2);
		myShield.setCenterY(Main.HEIGHT/2);
		root.getChildren().add(myPlayer);
		root.getChildren().addAll(myEnemies);
		root.getChildren().add(myShield);
		for(PlayerFood playerFood : myPlayerFood) {
			playerFood.setX(FOOD_DOCK);
		}
		root.getChildren().addAll(myPlayerFood);
		for(int i = 0; i < 5; i++) {
			if(i < myFlyingFood.size()) {
				myFlyingFood.get(i).setX(FOOD_DOCK);
			}
			myFlyingFood.add(new FlyingFood(FOOD_DOCK));
		}
		root.getChildren().addAll(myFlyingFood);
	}

	private void bossLevel() {
		enemyFire();
		for(PlayerFood playerFood : myPlayerFood) {
			playerFood.act();
		}
		for(FlyingFood flyingFood : myFlyingFood) {
			flyingFood.act(true);
		}
		int sum = 0;
		for(Actor enemy : myEnemies) {
			sum += enemy.getLives();
			if(enemy.isDead()) {
				root.getChildren().remove(enemy);
			}
		}
		if(sum == 0) {
			gameWin();
		}
	}

	private void gameOver() {
		root.getChildren().clear();
		myScene.setFill(Color.BLACK);
		root.getChildren().add(new ScreenText().bigWords("GAME OVER", "Score: "+myScore));
		myScore = -1;
	}

	private void gameWin() {
		root.getChildren().clear();
		myScene.setFill(Color.GOLD);
		root.getChildren().add(new ScreenText().bigWords("YOU WIN!!", "Score: "+myScore));
		myScore = -1;
	}

	/**
	 * Staggered firing from the enemies with different food objects
	 */
	private void enemyFire() {
		for(int i = 0; i < myEnemies.size(); i++) {
			Actor e = myEnemies.get(i);
			if(e.getHeat() == 390-40*i && !e.isDead()) {
				myFlyingFood.get(i).aim(myPlayer, e);
			}
			else if(e.getHeat() == 260-40*i && !e.isDead()) {
				myFlyingFood.get(i+3).aim(myPlayer, e);
			}
			else if(e.getHeat() == 130-40*i && !e.isDead()) {
				myFlyingFood.get(i+6).aim(myPlayer, e);
			}
			else if(e.getHeat() == 0-40*i){
				e.setHeat(391);
			}
			e.setHeat(e.getHeat()-1);
		}
	}

	private void updateLivesAndScore() {
		root.getChildren().remove(myLifeBoard);
		myLifeBoard.setText("Lives: " + myPlayer.getLives());
		root.getChildren().add(myLifeBoard);
		root.getChildren().remove(myScoreBoard);
		myScoreBoard.setText("Score: " + myScore);
		root.getChildren().add(myScoreBoard);
	}

	private void handleCollisions() {
		CollisionHandler collisions = new CollisionHandler();
		if(myShield.getHeat() <= 0) {
			collisions.playerHit(myPlayer, myFlyingFood);
		}
		if(collisions.foodHit(myPlayerFood, myFlyingFood)) {
			myScore++;
		}
		if(myScore > LEVEL1_MAX_SCORE && collisions.enemyHit(myEnemies, myPlayerFood)) {
			myScore++;
		}
	}

	private void runWithGame() {
		updateLivesAndScore();
		myPlayer.setHeat(myPlayer.getHeat()-1);
		handleCollisions();
		myShield.activate();
		myShield.setHeat(myShield.getHeat()-1);
		if(myPlayer.isDead()) {
			gameOver();
		}
	}

	private void level1Mechanics() {
		for(PlayerFood playerFood : myPlayerFood) {
			playerFood.act();
		}
		for(FlyingFood flyingFood : myFlyingFood) {
			flyingFood.act(false);
		}
	}

	/**
	 * Change properties of shapes to animate them
	 */
	public void step (double elapsedTime) {
		if(myScore != -1) {
			runWithGame();
			if(myFlyingFood.size() > 4) {
				bossLevel();
			}
			else if(myScore == LEVEL1_MAX_SCORE) {
				initializeBossLevel();
			}
			else if(myScore > -1) {
				level1Mechanics();
			}
		}
	}

	// What to do each time a key is pressed
	private void handleKeyInput (KeyCode code) {
		switch (code) {
		case UP:
			if (myPlayer.getCenterY()-myPlayer.getRadius() >= 0) {
				myPlayer.setCenterY(myPlayer.getCenterY()-PLAYER_MOVE);
				myShield.setCenterY(myShield.getCenterY()-PLAYER_MOVE);
			}
			break;
		case DOWN:
			if (myPlayer.getCenterY()+myPlayer.getRadius() <= Main.HEIGHT) {
				myPlayer.setCenterY(myPlayer.getCenterY()+PLAYER_MOVE);
				myShield.setCenterY(myShield.getCenterY()+PLAYER_MOVE);
			}
			break;
		case SPACE:
			for(PlayerFood playerFood : myPlayerFood) {
				if(playerFood.getX() == FOOD_DOCK && myPlayer.getHeat() <= 0) {
					myPlayer.setHeat(PLAYER_GUN_HEAT);
					playerFood.shoot(myPlayer);
					break;
				}
			}
			break;
		case Z:
			myScore = 10;
			break;
		case S:
			myShield.setHeat(SHIELD_HEAT);
			break;
		case X:
			if(myScore >= 10) {
				for(Actor e : myEnemies){
					e.setLives(0);
				}
			}
			break;
		default:
			// do nothing
		}
	}

	// What to do each time the mouse is pressed
	private void handleMouseInput (double x, double y) {
		if (myScore == -1) {
			myScore++;
			root.getChildren().clear();
			root.getChildren().add(myPlayer);
			root.getChildren().addAll(myFlyingFood);
			root.getChildren().addAll(myPlayerFood);
			root.getChildren().add(myScoreBoard);
			root.getChildren().add(myLifeBoard);
			root.getChildren().add(myShield);
		}
	}
}
