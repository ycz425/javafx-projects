import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class TitleController {

    public void start(ActionEvent event){
        Game game = new Game();
        game.start((Stage)((Node) event.getSource()).getScene().getWindow());
    }
}
