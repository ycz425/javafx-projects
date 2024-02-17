import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Game {
    private Group root;

    private int grid[][]; //0 = empty, 1 = snake, 2 = apple
    private Snake snake;
    private Apple apple;

    private int nextDirX; //direction change queues until next frame
    private int nextDirY;

    int score;

    boolean playing;

    AnimationTimer timer;
    private double time;

    public void start(Stage stage) {
        root = new Group();
        Scene scene = new Scene(root, 600, 600);
        scene.setFill(Color.BLACK);
        stage.setScene(scene);
        stage.show();

        playing = true;
        time = 0;
        grid = new int[20][20];
        snake = new Snake();
        nextDirX = 1;
        nextDirY = 0;
        score = 3;

        timer = new AnimationTimer(){
            @Override
            public void handle(long l) {
                time += 0.017;
                if (time >= 0.1){
                    snake.setDir(nextDirX, nextDirY);
                    update();
                    render();

                    time = 0;
                }
            }
        };

        scene.setOnKeyPressed(event -> {
            if (snake != null)
                switch (event.getCode()){

                    case W:
                        if (snake.getDirX() != 0 || snake.getDirY() != 1) {
                            nextDirX = 0;
                            nextDirY = -1;
                        }
                        break;
                    case A:
                        if (snake.getDirX() != 1 || snake.getDirY() != 0){
                            nextDirX = -1;
                            nextDirY = 0;
                        }
                        break;
                    case D:
                        if (snake.getDirX() != -1 || snake.getDirY() != 0){
                            nextDirX = 1;
                            nextDirY = 0;
                        }
                        break;
                    case S:
                        if (snake.getDirX() != 0 || snake.getDirY() != -1){
                            nextDirX = 0;
                            nextDirY = 1;
                        }
                        break;
                    default:
                }
        });

        spawnApple();
        render();
        timer.start();
    }

    public void update(){
        snake.move();

        if (offScreen() || colliding()) {
            gameOver();
            return;
        }

        if (grid[snake.getHead().getY()][snake.getHead().getX()] == 2){ //eat
            snake.grow();

            if (score < 400)
                spawnApple();

            score++;
        }

        updateGrid();
    }

    public void updateGrid(){
        grid = new int[20][20];

        for (Piece p : snake.getList())
            grid[p.getY()][p.getX()] = 1;

        grid[apple.getY()][apple.getX()] = 2;
    }

    public void render(){
        root.getChildren().clear();

        if (playing)
            snake.draw(root);

        apple.draw(root);

        Label label = new Label("Snake Length: " + score);
        label.setFont(Font.font("Verdana", 20));
        label.setTextFill(Color.WHITE);
        label.setOpacity(0.5);
        label.setLayoutX(10);
        label.setLayoutY(10);
        root.getChildren().add(label);

        if (!playing) {
            Button button = new Button("RESTART");
            button.setFont(Font.font("Verdana", 25));
            button.setLayoutX(225);
            button.setLayoutY(250);
            root.getChildren().add(button);

            button.setOnAction(event -> {
                start((Stage) ((Node) event.getSource()).getScene().getWindow());
            });
        }
    }

    public void spawnApple(){
        boolean valid = false;
        int x = 0;
        int y = 0;
        while (!valid){
            x = (int)(Math.random() * 20);
            y = (int)(Math.random() * 20);

            if (grid[y][x] == 0)
                valid = true;
        }
        apple = new Apple(x, y);
    }

    public boolean colliding(){
        return grid[snake.getHead().getY()][snake.getHead().getX()] == 1;
    }

    public boolean offScreen(){
        int x = snake.getHead().getX();
        int y = snake.getHead().getY();

        return x < 0 || x >= 20 || y < 0 || y >= 20;
    }

    public void gameOver(){
        playing = false;
        snake = null;
        timer.stop();
    }
}
