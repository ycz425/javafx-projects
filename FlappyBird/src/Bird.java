import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bird {
    private int height;
    private int velocity;

    private ImageView[] img;
    private int cycle;

    private HitBox hitBox;

    public static final double ACCELERATION = 1;

    public Bird(){
        height = 360;
        velocity = 10;

        img = new ImageView[4];
        img[0] = new ImageView(new Image("File:/Users/yuchongzhang425/Desktop/CompSci/IDEA Projects/FlappyBird/src/assets/yellowbird-midflap.png"));
        img[1] = new ImageView(new Image("File:/Users/yuchongzhang425/Desktop/CompSci/IDEA Projects/FlappyBird/src/assets/yellowbird-upflap.png"));
        img[2] = new ImageView(new Image("File:/Users/yuchongzhang425/Desktop/CompSci/IDEA Projects/FlappyBird/src/assets/yellowbird-downflap.png"));
        img[3] = new ImageView(new Image("File:/Users/yuchongzhang425/Desktop/CompSci/IDEA Projects/FlappyBird/src/assets/yellowbird-midflap.png"));

        //INITIALIZE AND SET HITBOX SIZE
        hitBox = new HitBox(44, 44);
        centerHitBox();
    }

    public void updateHeight(){
        height += velocity;
    }

    public void updateVelocity(){
        if (velocity < 20)
            velocity += ACCELERATION;
    }

    public ImageView draw(){
        ImageView avatar = img[cycle / 3];
        avatar.setPreserveRatio(true);
        avatar.resize(64, 48);
        avatar.setFitWidth(64);

        avatar.setX(70);
        avatar.setY(height);


        if (velocity < 5)
            avatar.setRotate(-45);
        if (velocity >= 5)
            avatar.setRotate(-45 + 9 * (velocity - 5));

        return avatar;
    }

    public void updateAvatar(){
        cycle++;
        if (cycle >= 12)
            cycle = 0;
    }

    public void jump(){
        velocity = -15;
    }

    public void centerHitBox(){
        hitBox.setX(104);
        hitBox.setY(height + 24);
    }

    public void setVelocity(int vel){velocity = vel;}

    public int getVelocity(){return velocity;}

    public HitBox getHitBox(){return hitBox;}
}
