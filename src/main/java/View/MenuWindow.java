package View;

import database.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class MenuWindow extends Stage {

    DatabaseHandler dh;

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
    public void RynekWejdz(MouseEvent event) throws IOException {
        rynekLoader = new FXMLLoader(getClass().getClassLoader().getResource("rynek.fxml"));
        rynekPane = rynekLoader.load();
        rynekController=rynekLoader.getController();
        rynekController.setBase(dh);
        rynekController.setChoiceBox();
        rynekStage=new Stage();
        rynekStage.setScene(new Scene(rynekPane));
        rynekStage.show();
    }

    @FXML
    public void TurniejWejdz(MouseEvent event) throws IOException {
        turniejLoader = new FXMLLoader(getClass().getClassLoader().getResource("turniej.fxml"));
        turniejPane = turniejLoader.load();
        turniejcontroller=turniejLoader.getController();
        turniejcontroller.setBase(dh);
        turniejcontroller.setBoxes();

        turniejStage=new Stage();
        turniejStage.setScene(new Scene(turniejPane));
        turniejStage.show();
    }

    @FXML
    public void WedkarzWejdz(MouseEvent event) throws IOException {
        wedkarzLoader=new FXMLLoader(getClass().getClassLoader().getResource("wedkID.fxml"));
        wedkarzPane=wedkarzLoader.load();
        wedkarzStage=new Stage();
        wedkarzStage.setScene(new Scene(wedkarzPane));
        wedkarzController =wedkarzLoader.getController();
        wedkarzController.setDB(dh);
        wedkarzController.setStage(wedkarzStage);
        wedkarzStage.setResizable(false);
        wedkarzStage.show();
    }

    public void sekretarzTAbCiemny(MouseEvent event) {

    }

    @FXML
    public void sekretarzTabJasny(MouseEvent event) {

    }

    @FXML
    public void sekretarzWejdz(MouseEvent event) throws IOException {
        sekretarzLoader = new FXMLLoader(getClass().getClassLoader().getResource("sekretarz.fxml"));
        sekretarzPane = sekretarzLoader.load();
        sekretarzStage=new Stage();
        sekretarzStage.setScene(new Scene(sekretarzPane));
        sekretarzController = sekretarzLoader.getController();

        sekretarzController.setDB(dh);
        sekretarzController.doMagic();
        sekretarzStage.setResizable(false);
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
    WedkarzID wedkarzController=null;
    Pane wedkarzPane;
    Stage wedkarzStage;

    FXMLLoader rynekLoader = null;
    rynekController rynekController=null;
    Pane rynekPane;
    Stage rynekStage;

    FXMLLoader sekretarzLoader = null;
    sekretarzController sekretarzController=null;
    Pane sekretarzPane;
    Stage sekretarzStage;

    FXMLLoader turniejLoader = null;
    turniejController turniejcontroller=null;
    Pane turniejPane;
    Stage turniejStage;

    public void setDb(DatabaseHandler dh) {
        this.dh=dh;
    }
    @FXML
    public void initialize() throws IOException {

        wedkarzTab.setStyle("-fx-background-image: url(rybak_mc.png); -fx-cursor: hand");
        turniejTab.setStyle("-fx-background-image: url(turniej.png); -fx-cursor: hand");
        RynekTab.setStyle("-fx-background-image: url(ryby.png); -fx-cursor: hand");
        sekretarzTab.setStyle("-fx-background-image: url(man.png); -fx-cursor: hand");
    }
}

