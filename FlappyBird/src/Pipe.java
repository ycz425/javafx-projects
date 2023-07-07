import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Pipe {

    private boolean up;
    private int x;
    private int length;
    private ImageView img;
    private HitBox hitBox;

    public Pipe(boolean up, int length){
        this.up = up;
        this.length = length;
        x = 432;

        if (up)
            img = new ImageView(new Image("File:/Users/yuchongzhang425/Desktop/CompSci/IDEA Projects/FlappyBird/src/assets/pipe-green-down.png"));
        else
            img = new ImageView(new Image("File:/Users/yuchongzhang425/Desktop/CompSci/IDEA Projects/FlappyBird/src/assets/pipe-green.png"));

        hitBox = new HitBox(90, 10000);
        centerHitBox();
    }

    public ImageView draw(){
        img.setX(x);

        if (up) {
            img.setY(length - 640);
        }
        else
            img.setY(768 - length);

        return img;
    }

    public void move(){x -= 4;}

    public void centerHitBox(){
        hitBox.setX(x + 52);
        if (up)
            hitBox.setY(length - 5000);
        else
            hitBox.setY(768 - length + 5000);
    }

    public boolean isUp(){return up;}
    public int getX(){return x;}

    public HitBox getHitBox(){
        return hitBox;
    }
}
