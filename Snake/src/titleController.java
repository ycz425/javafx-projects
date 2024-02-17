import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class titleController {

    public void start(ActionEvent e){
        Game game = new Game();
        game.start((Stage)((Node)e.getSource()).getScene().getWindow());
    }

}
