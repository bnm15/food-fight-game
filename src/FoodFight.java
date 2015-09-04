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

    public static final String TITLE = "Food Fight!";
    public static final int LEVEL1_MAX_SCORE = 10;
    public static final int LEVEL1_FOOD_NUM = 4;
    public static final int BOSS_FOOD_NUM = 9;
    public static final int NUMBER_OF_ENEMIES = 3;

    private Scene myScene;
    private Group root;
    private ArrayList<FlyingFood> myFlyingFood;
    private ArrayList<PlayerFood> myPlayerFood;
    private ArrayList<Enemy> myEnemies;
    private Player myPlayer;
    private Shield myShield;
    private Text myScoreBoard;
    private Text myLifeBoard;

    public FoodFight () {
        myFlyingFood = new ArrayList<>();
        myPlayerFood = new ArrayList<>();
        myEnemies = new ArrayList<>();
        myPlayer = new Player();
        myShield = new Shield(myPlayer);
        myScoreBoard =
                new ScreenText().counter("Score: " + myPlayer.getMyScore(), Main.WIDTH - 110);
        myLifeBoard = new ScreenText().counter("Lives: " + myPlayer.getMyLives(), 35);
        for (int i = 0; i < LEVEL1_FOOD_NUM; i++) {
            myFlyingFood
                    .add(new FlyingFood(Main.WIDTH * (LEVEL1_FOOD_NUM + i) / LEVEL1_FOOD_NUM + 50));
            myPlayerFood.add(new PlayerFood());
        }
        for (int i = 0; i < NUMBER_OF_ENEMIES; i++) {
            myEnemies.add(new Enemy(Main.HEIGHT * (1 + i) / 3 - 75));
        }
    }

    public String getTitle () {
        return TITLE;
    }

    public Scene init (int width, int height) {
        // Create a scene graph to organize the scene
        root = new Group();
        // Create a place to see the shapes
        myScene = new Scene(root, width, height, Color.WHITE);
        splashScreen();
        // Respond to input
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        myScene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return myScene;
    }

    private void splashScreen () {
        ScreenText screenText = new ScreenText();
        root.getChildren().add(screenText.title());
        root.getChildren().add(screenText.instructions());
        root.getChildren().add(screenText.start());
    }

    public void step (double elapsedTime) {
        if (myPlayer.getMyScore() > -1) {
            gameLoop();
            if (isBossLevel()) {
                bossLevel();
            }
            else if (myPlayer.getMyScore() == LEVEL1_MAX_SCORE) {
                initializeBossLevel();
            }
        }
    }

    private boolean isBossLevel () {
        return myFlyingFood.size() == BOSS_FOOD_NUM;
    }

    private void gameLoop () {
        for (PlayerFood playerFood : myPlayerFood) {
            playerFood.act();
        }
        for (FlyingFood flyingFood : myFlyingFood) {
            flyingFood.act(isBossLevel());
        }
        updateLivesAndScore();
        myPlayer.checkHeat();
        myShield.act();
        handleCollisions();
        if (myPlayer.isDead()) {
            gameOver();
        }
    }

    private void updateLivesAndScore () {
        myLifeBoard.setText("Lives: " + myPlayer.getMyLives());
        myScoreBoard.setText("Score: " + myPlayer.getMyScore());
    }

    private void handleCollisions () {
        CollisionHandler collisions = new CollisionHandler(myPlayer);
        if (myShield.getMyHeat() == 0) {
            collisions.playerHit(myFlyingFood);
        }
        collisions.foodHit(myPlayerFood, myFlyingFood);
        collisions.enemyHit(myEnemies, myPlayerFood);
    }

    private void initializeBossLevel () {
        root.getChildren().removeAll(myFlyingFood);
        for (int i = 0; i < BOSS_FOOD_NUM - LEVEL1_FOOD_NUM; i++) {
            if (i < LEVEL1_FOOD_NUM) {
                myFlyingFood.get(i).setX(Food.DOCK);
            }
            myFlyingFood.add(new FlyingFood(Food.DOCK));
        }
        for (PlayerFood playerFood : myPlayerFood) {
            playerFood.setX(Food.DOCK);
        }
        myPlayer.setCenterY(Main.HEIGHT / 2);
        root.getChildren().addAll(myEnemies);
        root.getChildren().addAll(myFlyingFood);
    }

    private void bossLevel () {
        enemyFire();
        int sum = 0;
        for (Enemy enemy : myEnemies) {
            sum += enemy.getMyLives();
            if (enemy.isDead()) {
                root.getChildren().remove(enemy);
            }
        }
        if (sum == 0) {
            gameWin();
        }
    }

    /**
     * Staggered firing from the enemies with different food objects
     */
    private void enemyFire () {
        for (int i = 0; i < NUMBER_OF_ENEMIES; i++) {
            Enemy enemy = myEnemies.get(i);
            for (int j = 0; j <= NUMBER_OF_ENEMIES; j++) {
                int shootTime = Enemy.MAX_HEAT * j;
                int stagger = Enemy.MAX_HEAT / NUMBER_OF_ENEMIES * i;
                if (enemy.getMyHeat() == shootTime - stagger) {
                    if (j == 0) {
                        enemy.setMyHeat(enemy.getMyHeat() + Enemy.MAX_HEAT * NUMBER_OF_ENEMIES);
                    }
                    else if (!enemy.isDead()) {
                        myFlyingFood.get(i + 3 * j - 3).aim(myPlayer, enemy);
                    }
                }
            }
            enemy.setMyHeat(enemy.getMyHeat() - 1);
        }
    }

    private void gameOver () {
        root.getChildren().clear();
        myScene.setFill(Color.BLACK);
        root.getChildren()
                .add(new ScreenText().bigWords("GAME OVER", "Score: " + myPlayer.getMyScore()));
        myPlayer.setMyScore(-1);
    }

    private void gameWin () {
        root.getChildren().clear();
        myScene.setFill(Color.GOLD);
        root.getChildren()
                .add(new ScreenText().bigWords("YOU WIN!!", "Score: " + myPlayer.getMyScore()));
        myPlayer.setMyScore(-1);
    }

    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        switch (code) {
            case UP:
                if (myPlayer.getCenterY() - myPlayer.getRadius() >= 0) {
                    myPlayer.setCenterY(myPlayer.getCenterY() - Player.PLAYER_MOVE);
                }
                break;
            case DOWN:
                if (myPlayer.getCenterY() + myPlayer.getRadius() <= Main.HEIGHT) {
                    myPlayer.setCenterY(myPlayer.getCenterY() + Player.PLAYER_MOVE);
                }
                break;
            case SPACE:
                for (PlayerFood playerFood : myPlayerFood) {
                    if (playerFood.getX() == Food.DOCK && myPlayer.getMyHeat() <= 0) {
                        myPlayer.setMyHeat(Player.MAX_HEAT);
                        playerFood.shoot(myPlayer);
                        break;
                    }
                }
                break;
            case Z:
                if (myPlayer.getMyScore() > -1) {
                    myPlayer.setMyScore(10);
                }
                break;
            case S:
                myShield.setMyHeat(Shield.MAX_HEAT);
                break;
            case X:
                if (myPlayer.getMyScore() >= 10) {
                    for (Enemy enemy : myEnemies) {
                        enemy.setMyLives(0);
                    }
                }
                break;
            default:
                // do nothing
        }
    }

    // What to do each time the mouse is pressed
    private void handleMouseInput (double x, double y) {
        if (myPlayer.getMyScore() == -1) {
            myPlayer.setMyScore(0);
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
