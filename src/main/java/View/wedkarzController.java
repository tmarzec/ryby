package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class wedkarzController extends Stage{
    Stage owner;
    @FXML
    private Button BackButton;

    @FXML
    void Back(ActionEvent event) {

    }
    public void setStage(Stage stage){
        owner=stage;
    }
}
