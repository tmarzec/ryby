package View;

import database.DatabaseHandler;
import entities.Wedka;
import entities.Wedkarz;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class wedkarzController implements Initializable {
    Stage owner;
    DatabaseHandler dh;
    int wedkarz;

    @FXML
    private Text basicInfo;


    @FXML
    private TableView<Wedka> wedki;

    @FXML
    private TableColumn<Wedka, String> rodzajCOL;

    @FXML
    private TableColumn<Wedka, String> materialCOL;


    @FXML
    private Button addWB;

    @FXML
    private ChoiceBox<String> materialyCB;

    @FXML
    private ChoiceBox<String> rodzajeCB;

    @FXML
    void addWedka(ActionEvent event) {
        if(materialyCB.getSelectionModel().isEmpty() || rodzajeCB.getSelectionModel().isEmpty()) return;
        dh.addWedka(wedkarz, new Wedka(rodzajeCB.getSelectionModel().getSelectedItem(),materialyCB.getSelectionModel().getSelectedItem()));
        magic();//update
    }

    public void setID(int id) {
        wedkarz=id;
    }

    public void magic() {
        Wedkarz our = dh.getWedkarz(wedkarz);
        basicInfo.setText("Witaj " + our.getImie() + " " + our.getNazwisko()+"!");
        ArrayList<Wedka> arr = dh.getWedki(wedkarz);
        wedki.getItems().setAll(arr);
        materialyCB.getItems().setAll(dh.getMaterialy());
        rodzajeCB.getItems().setAll(dh.getRodzaje());
    }
    //public void setStage(Stage stage){
    //    owner=stage;
    //}

    public void setBase(DatabaseHandler db) {
        this.dh=db;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rodzajCOL.setCellValueFactory(new PropertyValueFactory<>("rodzaj"));
        materialCOL.setCellValueFactory(new PropertyValueFactory<>("material"));
    }

}