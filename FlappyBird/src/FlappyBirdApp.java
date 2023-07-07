import javafx.animation.AnimationTimer;
import javafx.application.Application;
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

/**
 * Yuchong Zhang
 * July 6 2023
 */

public class FlappyBirdApp extends Application{

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 432, 768);
        stage.setScene(scene);
        stage.setResizable(false);

        Game game = new Game();
        game.start(root);

        stage.show();
    }
}
