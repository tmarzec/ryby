package View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.net.URL;
import java.util.ResourceBundle;
public class MenuWindow  implements Initializable{

    @FXML
    private Pane wedkarzTab;

    @FXML
    private Pane sekretarzTab;

    @FXML
    private Pane turniejTab;

    @FXML
    private Pane RynekTab;

    @FXML
    public void RynekCiemny(MouseEvent event) {

    }

    @FXML
    public  void RynekJasny(MouseEvent event) {

    }
    @FXML
    public void RynekWejdz(MouseEvent event) {

    }

    @FXML
    public void TurniejWejdz(MouseEvent event) {

    }

    @FXML
    public void WedkarzWejdz(MouseEvent event) {

    }

    public void sekretarzTAbCiemny(MouseEvent event) {

    }

    @FXML
    public void sekretarzTabJasny(MouseEvent event) {

    }

    @FXML
    public void sekretarzWejdz(MouseEvent event) {

    }

    @FXML
    public void turniejCiemny(MouseEvent event) {

    }

    @FXML
    public void turniejJasny(MouseEvent event) {

    }

    @FXML
    public void wedkarzTabCiemny(MouseEvent event) {

    }

    @FXML
    public void wedkarzTabJasny(MouseEvent event) {

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       /* Stage aStage=new Stage();
        ImageView img = new ImageView();
        img.setImage(new Image(getClass().getResource("src/main/resources/czapka.png").toExternalForm()));
        wedkarzTab.getChildren().add(img);
        Scene scene = new Scene(wedkarzTab, 500, 500);
        scene.setFill(Color.TRANSPARENT);
        aStage.initStyle(StageStyle.TRANSPARENT);
        aStage.setScene(scene);
        aStage.show();*/

        wedkarzTab.setStyle("-fx-background-image: url(wedkarz.png); -fx-cursor: hand");
        turniejTab.setStyle("-fx-background-image: url(turniej.png); -fx-cursor: hand");
        RynekTab.setStyle("-fx-background-image: url(ryby.png); -fx-cursor: hand");
        sekretarzTab.setStyle("-fx-background-image: url(man.png); -fx-cursor: hand");


    }



}

