package View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class MenuWindow extends Stage {

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
        rynekStage.show();
    }

    @FXML
    public void TurniejWejdz(MouseEvent event) {
        turniejStage.show();
    }

    @FXML
    public void WedkarzWejdz(MouseEvent event) {
        wedkarzStage.show();
    }

    public void sekretarzTAbCiemny(MouseEvent event) {

    }

    @FXML
    public void sekretarzTabJasny(MouseEvent event) {

    }

    @FXML
    public void sekretarzWejdz(MouseEvent event) {
        sekretarzStage.show();
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
    FXMLLoader wedkarzLoader = null;
    wedkarzController controller=null;
    Pane wedkarzPane;
    Stage wedkarzStage;

    FXMLLoader rynekLoader = null;
   // wedkarzController controller=null;
    Pane rynekPane;
    Stage rynekStage;

    FXMLLoader sekretarzLoader = null;
    // wedkarzController controller=null;
    Pane sekretarzPane;
    Stage sekretarzStage;

    FXMLLoader turniejLoader = null;
    // turniejController controller=null;
    Pane turniejPane;
    Stage turniejStage;

    @FXML
    public void initialize() throws IOException {

        wedkarzLoader = new FXMLLoader(getClass().getClassLoader().getResource("wedkarz.fxml"));
        wedkarzPane = wedkarzLoader.load();
        wedkarzStage=new Stage();
        wedkarzStage.setScene(new Scene(wedkarzPane));

        rynekLoader = new FXMLLoader(getClass().getClassLoader().getResource("rynek.fxml"));
        rynekPane = rynekLoader.load();
        rynekStage=new Stage();
        rynekStage.setScene(new Scene(rynekPane));

        sekretarzLoader = new FXMLLoader(getClass().getClassLoader().getResource("sekretarz.fxml"));
        sekretarzPane = sekretarzLoader.load();
        sekretarzStage=new Stage();
        sekretarzStage.setScene(new Scene(sekretarzPane));

        turniejLoader = new FXMLLoader(getClass().getClassLoader().getResource("turniej.fxml"));
        turniejPane = turniejLoader.load();
        turniejStage=new Stage();
        turniejStage.setScene(new Scene(turniejPane));

        wedkarzTab.setStyle("-fx-background-image: url(wedkarz.png); -fx-cursor: hand");
        turniejTab.setStyle("-fx-background-image: url(turniej.png); -fx-cursor: hand");
        RynekTab.setStyle("-fx-background-image: url(ryby.png); -fx-cursor: hand");
        sekretarzTab.setStyle("-fx-background-image: url(man.png); -fx-cursor: hand");


    }



}

