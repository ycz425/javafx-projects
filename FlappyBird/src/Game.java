import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;

public class Game {
    private Bird bird;
    private Base base;
    private Score score;
    private Queue<Pipe> list;

    private Image message;
    private Image message2;
    private Image backdrop;
    private Image restart;
    private Button btn;

    private double time;

    private boolean started;
    private boolean gameOver;

    private Group root;

    public void start(Group root){
        this.root = root;
        start();
    }

    public void start(){
        root.getChildren().clear();

        started = false;
        gameOver = false;
        time = 0;
        list = new ArrayDeque<>();

        message = new Image("File:/Users/yuchongzhang425/Desktop/CompSci/IDEA Projects/FlappyBird/src/assets/message.png");
        message2 = new Image("File:/Users/yuchongzhang425/Desktop/CompSci/IDEA Projects/FlappyBird/src/assets/gameover.png");
        backdrop = new Image("File:/Users/yuchongzhang425/Desktop/CompSci/IDEA Projects/FlappyBird/src/assets/background-day.png");
        restart = new Image("File:/Users/yuchongzhang425/Desktop/CompSci/IDEA Projects/FlappyBird/src/assets/start.png");

        bird = new Bird();
        base = new Base();
        score = new Score();

        root.getScene().setOnKeyPressed(event -> {
            if (Objects.requireNonNull(event.getCode()) == KeyCode.SPACE) {
                if (!started)
                    started = true;
                if (!gameOver)
                    bird.jump();
            }
        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (colliding()) {
                    this.stop();
                    gameOver();
                    return;
                }

                update();
                render();

                if (time > 1.4) {
                    spawnPipes();
                    time = 0;

                    if (list.size() > 4) {
                        list.remove();
                        list.remove();
                    }
                }

                if (started)
                    time += 0.017;
            }
        };

        render();

        timer.start();
    }

    public void render(){
        root.getChildren().clear();

        ImageView background = new ImageView(backdrop);
        background.setPreserveRatio(true);
        background.setFitWidth(432);
        root.getChildren().add(background);

        for (Pipe p : list){
            root.getChildren().add(p.draw());
            //root.getChildren().add(p.getHitBox().draw());
        }

        if (started)
            score.draw(root);

        root.getChildren().add(bird.draw());
        //root.getChildren().add(bird.getHitBox().draw());

        root.getChildren().add(base.draw());
        //root.getChildren().add(base.getHitBox().draw());

        if (!started){
            ImageView img = new ImageView(message);
            img.setX(32);
            img.setY(20);
            root.getChildren().add(img);
        }
    }

    public void update(){
        base.updateX();

        if (!gameOver)
            bird.updateAvatar();

        if (started) {
            bird.updateHeight();
            bird.updateVelocity();
            bird.centerHitBox();

            for (Pipe p : list) {
                p.move();
                p.centerHitBox();

                if (p.isUp() && p.getX() == -4)        //scoring
                    score.add();
            }
        }
    }

    public void spawnPipes(){
        int y = (int)(Math.random() * ((650 - 200) - 200 + 1)) + 200;
        list.add(new Pipe(true, y - 115));
        list.add(new Pipe(false, 768 - (y + 115)));
    }

    public boolean colliding(){
        for (Pipe p : list){
            if (bird.getHitBox().colliding(p.getHitBox()))
                return true;
        }

        return bird.getHitBox().colliding(base.getHitBox());
    }

    public void gameOver() {
        gameOver = true;

        Rectangle box = new Rectangle(432, 768, Color.RED);

        ImageView img = new ImageView(message2);
        img.setX(24);
        img.setY(70);

        Button btn = new Button();
        btn.setGraphic(new ImageView(restart));
        btn.setPadding(Insets.EMPTY);
        btn.setLayoutX(190);
        btn.setLayoutY(400);

        if (bird.getVelocity() < 0)
            bird.setVelocity(10);

        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long l) {
                if (!bird.getHitBox().colliding(base.getHitBox())) {
                    bird.updateHeight();
                    bird.updateVelocity();
                    bird.centerHitBox();
                } else
                    bird.setVelocity(20);

                render();

                box.setOpacity(box.getOpacity() - 0.1);

                root.getChildren().add(box);
                root.getChildren().add(img);
                root.getChildren().add(btn);

                if (box.getOpacity() <= 0 && bird.getHitBox().colliding(base.getHitBox()))
                    this.stop();
            }
        };

        btn.setOnAction(event -> {
            timer.stop();
            start();
        });

        timer.start();
    }
}
