import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Base {

    private ImageView img;
    private HitBox hitBox;

    public Base(){
        img = new ImageView(new Image("File:/Users/yuchongzhang425/Desktop/CompSci/IDEA Projects/FlappyBird/src/assets/base.png"));
        hitBox = new HitBox(768, 100);
        hitBox.setX(266);
        hitBox.setY(700);
    }

    private int cycle;

    public ImageView draw(){
        img.setPreserveRatio(true);
        img.setFitWidth(504);
        img.setY(650);

        return img;
    }

    public void updateX(){
        img.setX(-cycle);

        cycle += 4;
        if (cycle >= 72)
            cycle = 0;
    }

    public HitBox getHitBox(){return hitBox;}
}
