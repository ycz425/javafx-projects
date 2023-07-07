import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HitBox {

    private int width;
    private int height;
    private int x;          //center coords
    private int y;


    public HitBox(int width, int height){
        this.width = width;
        this.height = height;
    }

    public boolean colliding(HitBox hitBox){
        if (getRightBoundary() >= hitBox.getLeftBoundary() && getLeftBoundary() <= hitBox.getRightBoundary())
            if (getTopBoundary() <= hitBox.getBottomBoundary() && getBottomBoundary() >= hitBox.getTopBoundary())
                return true;

        return false;
    }

    public int getRightBoundary(){
        return x + width / 2;
    }

    public int getLeftBoundary(){
        return x - width / 2;
    }

    public int getTopBoundary(){
        return y - height / 2;
    }

    public int getBottomBoundary(){
        return y + height / 2;
    }

    public void setX(int x){this.x = x;}
    public void setY(int y){this.y = y;}

    public Rectangle draw(){
        Rectangle box = new Rectangle(width, height, Color.BLACK);
        box.setX(x - width / 2);
        box.setY(y - height / 2);
        box.setOpacity(0.5);
        return box;
    }
}
