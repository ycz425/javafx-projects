import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile {

    private int value;

    private int x;
    private int y;

    private int x0;
    private int y0;

    private ImageView img;

    public Tile(int value, int x, int y){
        this.value = value;
        this.x = x;
        this.y = y;
        x0 = x;
        y0 = y;
    }

    public ImageView draw(int width, int height) {  //only for initializing
        if (img == null)
            switch (value) {
                case 2:
                    img = new ImageView(new Image("File:_2048/src/tile/2.png"));
                    break;
                case 4:
                    img = new ImageView(new Image("File:_2048/src/tile/4.png"));
                    break;
                case 8:
                    img = new ImageView(new Image("File:_2048/src/tile/8.png"));
                    break;
                case 16:
                    img = new ImageView(new Image("File:_2048/src/tile/16.png"));
                    break;
                case 32:
                    img = new ImageView(new Image("File:_2048/src/tile/32.png"));
                    break;
                case 64:
                    img = new ImageView(new Image("File:_2048/src/tile/64.png"));
                    break;
                case 128:
                    img = new ImageView(new Image("File:_2048/src/tile/128.png"));
                    break;
                case 256:
                    img = new ImageView(new Image("File:_2048/src/tile/256.png"));
                    break;
                case 512:
                    img = new ImageView(new Image("File:_2048/src/tile/512.png"));
                    break;
                case 1024:
                    img = new ImageView(new Image("File:_2048/src/tile/1024.png"));
                    break;
                case 2048:
                    img = new ImageView(new Image("File:_2048/src/tile/2048.png"));
                    break;
            }

        img.setFitHeight(height);
        img.setFitWidth(width);
        img.setX(x * 100 + 50 - width / 2.0 + 10 * (x + 1));
        img.setY(y * 100 + 50 - height / 2.0 + 10 * (y + 1));

        return img;
    }

    public void move(int newX, int newY){
        x = newX;
        y = newY;
    }

    public void updateXY0(){
        x0 = x;
        y0 = y;
    }

    public int getX(){return x;}
    public int getY(){return y;}

    public int getDX(){return x - x0;}

    public int getDY(){return y - y0;}

    public boolean isEmpty(){
        return value == 0;
    }

    public ImageView getImg(){return img;}

    public int getVal(){
        return value;
    }
}
