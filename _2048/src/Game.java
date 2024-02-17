import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Game {

    private Tile grid[][][];

    private Group root;

    private int time;

    private boolean animating; //activates when moved, ends after merge
    private boolean moveSuccessful; //activitates when movable tile found
    private boolean playing;

    public void start(Stage stage) {
        root = new Group();
        Scene scene = new Scene(root, 450, 450);
        stage.setScene(scene);
        scene.setFill(Color.rgb(182, 163, 148));
        setStage();

        animating = false;
        playing = true;
        grid = new Tile[4][4][2];

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                for (int k = 0; k < 2; k++)
                    grid[i][j][k] = new Tile(0, j, i);

        scene.setOnKeyPressed(event -> {
            if (playing && !animating)
                switch (event.getCode()){
                    case RIGHT:
                        moveRight();
                        break;
                    case LEFT:
                        moveLeft();
                        break;
                    case UP:
                        moveUp();
                        break;
                    case DOWN:
                        moveDown();
                        break;
                }
            moveSuccessful = false;
        });

        spawnRandom();
        spawnRandom();
    }

    public void setStage(){
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++) {
                Rectangle tile = new Rectangle(i * 100 + 10 * (i + 1), j * 100 + 10 * (j + 1), 100, 100);
                tile.setFill(Color.rgb(199, 187, 171));
                root.getChildren().add(tile);
            }
    }

    public void spawnRandom(){            //MUST CHECK FOR GAMEOVER BEFORE THIS METHOD OR ELSE INFINITE LOOP
        boolean valid = false;

        int x = 0;
        int y = 0;


        while (!valid){
            x = (int)(Math.random() * 4);
            y = (int)(Math.random() * 4);

            if (isEmpty(x, y))
                valid = true;
        }

        if (Math.random() < 0.1)
            add(new Tile(4, x, y), x, y);
        else
            add(new Tile(2, x, y), x, y);

        spawn(getTile(x, y));
    }

    public void spawn(Tile tile){
        ImageView img = tile.draw(2, 2);
        root.getChildren().add(img);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {

                tile.draw((int) tile.getImg().getFitWidth() + 14, (int) tile.getImg().getFitHeight() + 14);

                if (img.getFitHeight() >= 100) {
                    this.stop();
                    animating = false;

                    updateRoot();

                    if (checkWin()){
                        win();
                        return;
                    }

                    if (checkGameOver())
                        gameOver();
                }
            }
        };

        timer.start();
    }

    public void animateMove(ArrayList<Tile> list){
        animating = true;
        moveSuccessful = false;
        time = 0;

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {    //11 frames total
                for (Tile t : list){
                    if (t.getDX() != 0 || t.getDY() != 0)
                        moveSuccessful = true;

                    int dx = t.getDX() * 10 + t.getDX();
                    int dy = t.getDY() * 10 + t.getDY();

                    t.getImg().setX(t.getImg().getX() + dx);
                    t.getImg().setY(t.getImg().getY() + dy);
                }

                time++;

                if (time == 10) {   //termination
                    this.stop();

                    for (Tile t : list)
                        t.updateXY0();

                    if (moveSuccessful) {   //if at least one tile moves successfully
                        merge();
                        spawnRandom();
                    } else
                        animating = false;

                }
            }
        };

        timer.start();
    }

    public void moveRight(){
        ArrayList<Tile> list = new ArrayList<>();

        for (int i = 0; i < 4; i++) //y - column
            for (int j = 3; j >= 0; j--) //x - row
                if (!isEmpty(j, i)) {     //(j, i) = current Tile

                    int k = j;

                    while (k < 3) { //case: k = 3
                        k++;

                        if (getTile(j, i).getVal() == getTile(k, i).getVal() && !isFull(k, i))   //case: merge
                            break;

                        if (!isEmpty(k, i)) {   //case: obstruction
                            k--;
                            break;
                        }
                    }

                    Tile tile = getTile(j, i);

                    tile.move(k, i);   //update x y instances for tile
                    list.add(getTile(j, i));

                    clear(j, i);                //translate tile on grid
                    add(tile, k, i);
                }

        animateMove(list);
    }

    public void moveLeft(){
        ArrayList<Tile> list = new ArrayList<>();

        for (int i = 0; i < 4; i++) //y - column
            for (int j = 0; j < 4; j++) //x - row
                if (!isEmpty(j, i)) {     //(j, i) = current Tile

                    int k = j;

                    while (k > 0) { //case: k = 0
                        k--;

                        if (getTile(j, i).getVal() == getTile(k, i).getVal() && !isFull(k, i))      //case: merge
                            break;

                        if (!isEmpty(k, i)) {   //case: obstruction
                            k++;
                            break;
                        }
                    }

                    Tile tile = getTile(j, i);

                    tile.move(k, i);   //update x y instances for tile
                    list.add(getTile(j, i));

                    clear(j, i);                //translate tile on grid
                    add(tile, k, i);
                }

        animateMove(list);
    }

    public void moveUp(){
        ArrayList<Tile> list = new ArrayList<>();

        for (int i = 0; i < 4; i++) //x - row
            for (int j = 0; j < 4; j++) //y - column
                if (!isEmpty(i, j)) {     //(i, j) = current Tile

                    int k = j;

                    while (k > 0) { //case: k = 0
                        k--;

                        if (getTile(i, j).getVal() == getTile(i, k).getVal() && !isFull(i, k))      //case: merge
                            break;

                        if (!isEmpty(i, k)) {   //case: obstruction
                            k++;
                            break;
                        }
                    }

                    Tile tile = getTile(i, j);

                    tile.move(i, k);   //update x y instances for tile
                    list.add(getTile(i, j));

                    clear(i, j);                //translate tile on grid
                    add(tile, i, k);
                }

        animateMove(list);
    }

    public void moveDown(){
        ArrayList<Tile> list = new ArrayList<>();

        for (int i = 0; i < 4; i++) //x - row
            for (int j = 3; j >= 0; j--) //y - column
                if (!isEmpty(i, j)) {     //(i, j) = current Tile

                    int k = j;

                    while (k < 3) { //case: k = 3
                        k++;

                        if (getTile(i, j).getVal() == getTile(i, k).getVal() && !isFull(i, k))      //case: merge
                            break;

                        if (!isEmpty(i, k)) {   //case: obstruction
                            k--;
                            break;
                        }
                    }

                    Tile tile = getTile(i, j);

                    tile.move(i, k);   //update x y instances for tile
                    list.add(getTile(i, j));

                    clear(i, j);                //translate tile on grid
                    add(tile, i, k);
                }

        animateMove(list);
    }

    public void merge(){
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                if (isFull(j, i)){
                    int val = getTile(j, i).getVal();
                    clear(j, i);
                    add(new Tile(val * 2, j, i), j, i);
                    spawn(getTile(j, i));

                }
    }

    public void add(Tile tile, int x, int y){   //add tile to back
        if (grid[y][x][0].isEmpty())
            grid[y][x][0] = tile;
        else if (grid[y][x][1].isEmpty())
            grid[y][x][1] = tile;
    }

    public void clear(int x, int y){    //clear all tiles
        grid[y][x][0] = new Tile(0, x, y);
        grid[y][x][1] = new Tile(0, x, y);
    }

    public void updateRoot(){
        root.getChildren().clear();

        setStage();

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                if (!isEmpty(j, i))
                    root.getChildren().add(getTile(j, i).getImg());
    }

    public Tile getTile(int x, int y){  //gets first tile at grid location
        return grid[y][x][0];
    }

    public void win(){
        playing = false;

        Rectangle winScreen = new Rectangle(450, 450, Color.rgb(239, 193, 48, 0.5));
        root.getChildren().add(winScreen);

        Label gameOverLabel = new Label("You Win!");
        gameOverLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 45));
        gameOverLabel.setTextFill(Color.rgb(255, 255, 255));
        gameOverLabel.setLayoutX(113);
        gameOverLabel.setLayoutY(150);
        root.getChildren().add(gameOverLabel);

        Button button = new Button("Try again");
        button.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        button.setTextFill(Color.WHITE);
        button.setStyle("-fx-background-color: #8f7a66");
        button.setLayoutX(160);
        button.setLayoutY(250);
        root.getChildren().add(button);

        button.setOnAction(event -> start((Stage)((Node)event.getSource()).getScene().getWindow()));
    }

    public void gameOver(){
        playing = false;

        Rectangle gameOverScreen = new Rectangle(450, 450, Color.rgb(255, 255, 255, 0.5));
        root.getChildren().add(gameOverScreen);

        Label gameOverLabel = new Label("Game Over");
        gameOverLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 45));
        gameOverLabel.setTextFill(Color.rgb(51, 51, 51));
        gameOverLabel.setLayoutX(85);
        gameOverLabel.setLayoutY(150);
        root.getChildren().add(gameOverLabel);

        Button button = new Button("Try again");
        button.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        button.setTextFill(Color.WHITE);
        button.setStyle("-fx-background-color: #8f7a66");
        button.setLayoutX(160);
        button.setLayoutY(250);
        root.getChildren().add(button);

        button.setOnAction(event -> start((Stage)((Node)event.getSource()).getScene().getWindow()));
    }

    public boolean isEmpty(int x, int y){   //true if both slots of grid is empty
        return grid[y][x][0].isEmpty() && grid[y][x][1].isEmpty();
    }

    public boolean isFull(int x, int y){
        return !grid[y][x][0].isEmpty() && !grid[y][x][1].isEmpty();
    }

    public boolean checkWin(){
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                if (getTile(j, i).getVal() == 2048)
                    return true;

        return false;
    }

    public boolean checkGameOver(){
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j ++){
                if (i > 0)
                    if (isEmpty(j, i - 1) || getTile(j, i - 1).getVal() == getTile(j, i).getVal())
                        return false;
                if (i < 3)
                    if (isEmpty(j, i + 1) || getTile(j, i + 1).getVal() == getTile(j, i).getVal())
                        return false;
                if (j > 0)
                    if (isEmpty(j - 1, i) || getTile(j - 1, i).getVal() == getTile(j, i).getVal())
                        return false;
                if (j < 3)
                    if (isEmpty(j + 1, i) || getTile(j + 1, i).getVal() == getTile(j, i).getVal())
                        return false;
            }

        return true;
    }
}
