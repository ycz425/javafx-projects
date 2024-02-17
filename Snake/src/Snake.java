import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Snake {

    private ArrayList<Piece> list;
    private Piece head;

    private int dirX;
    private int dirY;

    public Snake(){
        head = new Piece(5,10);

        list = new ArrayList<>();
        list.add(head);
        list.add(new Piece(4, 10));
        list.add(new Piece(3, 10));

        dirX = 1;
        dirY = 0;
    }

    public void move(){
        for (int i = list.size() - 1; i > 0; i--){
            if (!list.get(i).stalling()) {
                int nextX = list.get(i - 1).getX();
                int nextY = list.get(i - 1).getY();

                list.get(i).setXY(nextX, nextY);
            }
            list.get(i).setStall(false);
        }

        head.setXY(head.getX() + dirX, head.getY() + dirY);
    }

    public void draw(Group root){
        for (int i = 1; i < list.size(); i++){
            Rectangle tile = new Rectangle(30, 30, Color.GREEN);
            tile.setX(list.get(i).getX() * 30);
            tile.setY(list.get(i).getY() * 30);
            root.getChildren().add(tile);
        }

        Rectangle tile = new Rectangle(30, 30, Color.LIMEGREEN);
        tile.setX(head.getX() * 30);
        tile.setY(head.getY() * 30);
        root.getChildren().add(tile);
    }

    public void grow(){
        int x = list.get(list.size() - 1).getX();
        int y = list.get(list.size() - 1).getY();
        list.add(new Piece(x, y, true));
    }

    public ArrayList<Piece> getList(){return list;}
    public Piece getHead(){return head;}

    public int getDirX(){return dirX;}
    public int getDirY(){return dirY;}

    public void setDir(int x, int y){
        dirX = x;
        dirY = y;
    }
}
