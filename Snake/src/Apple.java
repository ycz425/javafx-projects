import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Apple {

    private int x;
    private int y;

    public Apple(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void draw(Group root){
        Rectangle tile = new Rectangle(30, 30, Color.RED);
        tile.setX(x * 30);
        tile.setY(y * 30);

        root.getChildren().add(tile);
    }

    public int getX(){return x;}
    public int getY(){return y;}
}
