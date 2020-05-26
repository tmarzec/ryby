import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class mainController implements Initializable {

    @FXML
    private Pane wedkarzTab;

    @FXML
    private Pane sekretarzTab;

    @FXML
    private Pane turniejTab;

    @FXML
    private Pane ostatniTab;

    Pane getPane(int i) {
        switch (i) {
            case 0: return wedkarzTab;
            case 1: return sekretarzTab;
            case 2: return turniejTab;
            case 3: return ostatniTab;
        }
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        wedkarzTab.setStyle("-fx-background-image: url(rybak.png); -fx-cursor: hand");


    }
}
