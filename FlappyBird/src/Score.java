import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Score {
    int score;
    Image[] img;

    public Score(){
        score = 0;

        img = new Image[10];
        img[0] = new Image("File:/Users/yuchongzhang425/Desktop/CompSci/IDEA Projects/FlappyBird/src/assets/0.png");
        img[1] = new Image("File:/Users/yuchongzhang425/Desktop/CompSci/IDEA Projects/FlappyBird/src/assets/1.png");
        img[2] = new Image("File:/Users/yuchongzhang425/Desktop/CompSci/IDEA Projects/FlappyBird/src/assets/2.png");
        img[3] = new Image("File:/Users/yuchongzhang425/Desktop/CompSci/IDEA Projects/FlappyBird/src/assets/3.png");
        img[4] = new Image("File:/Users/yuchongzhang425/Desktop/CompSci/IDEA Projects/FlappyBird/src/assets/4.png");
        img[5] = new Image("File:/Users/yuchongzhang425/Desktop/CompSci/IDEA Projects/FlappyBird/src/assets/5.png");
        img[6] = new Image("File:/Users/yuchongzhang425/Desktop/CompSci/IDEA Projects/FlappyBird/src/assets/6.png");
        img[7] = new Image("File:/Users/yuchongzhang425/Desktop/CompSci/IDEA Projects/FlappyBird/src/assets/7.png");
        img[8] = new Image("File:/Users/yuchongzhang425/Desktop/CompSci/IDEA Projects/FlappyBird/src/assets/8.png");
        img[9] = new Image("File:/Users/yuchongzhang425/Desktop/CompSci/IDEA Projects/FlappyBird/src/assets/9.png");
    }

    public void add(){score++;}

    public void draw(Group root){
        int temp = score;
        ArrayList<Integer> list = new ArrayList<>();

        while (temp > 0){
            list.add(temp % 10);
            temp /= 10;
        }

        int n = list.size();

        if (n == 0) {
            ImageView digit = new ImageView(img[0]);
            digit.setX(196);
            digit.setY(200);
            root.getChildren().add(digit);
        }

        for (int i = 0; i < n; i++) {
            ImageView digit = new ImageView(img[list.get(i)]);

            if (n % 2 == 0)
                digit.setX(172);
            else
                digit.setX(196);

            if (list.get(i) == 1)
                digit.setX(digit.getX() + n / 2 * 48 - i * 48 + 8);
            else
                digit.setX(digit.getX() + n / 2 * 48 - i * 48);

            digit.setY(200);
            root.getChildren().add(digit);
        }
    }

    public int getScore(){return score;};



}
