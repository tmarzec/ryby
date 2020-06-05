package View;

import database.DatabaseHandler;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class StarosciWindow implements Initializable {

    DatabaseHandler dh;
    public void setDb(DatabaseHandler dh) {
        this.dh=dh;
    }
    public void doMagic() {

    }
    public void setMe(Stage me) {
        this.me=me;
    }
    Stage me;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
