package View;

import database.DatabaseHandler;
import entities.Polow;
import entities.Turniej;
import entities.Wedka;
import entities.Wedkarz;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class wedkarzController implements Initializable {
    Stage owner;
    DatabaseHandler dh;
    int wedkarz;
    Wedkarz our;
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
        refresh();//update
    }


    @FXML
    private TableView<Polow> polowy;

    @FXML
    private TableColumn<Polow, Timestamp> dataCL;

    @FXML
    private TableColumn<Polow, String> gdzieCL;

    @FXML
    private TableColumn<Polow, String> rybaCL;

    @FXML
    private TableColumn<Polow, Float> wagaCL;

    @FXML
    private TableColumn<Polow, Float> pktCL;

    @FXML
    private ComboBox<Turniej> turniejBT;

    public void setID(int id) {
        wedkarz=id;
    }
    public void refresh() {
        ArrayList<Wedka> arr = dh.getWedki(wedkarz);
        wedki.getItems().setAll(arr);
        materialyCB.getItems().setAll(dh.getMaterialy());
        rodzajeCB.getItems().setAll(dh.getRodzaje());
        polowy.getItems().setAll(dh.getPolowy(wedkarz, turniejBT.getSelectionModel().getSelectedItem()));

    }

    ArrayList<Turniej> turniejs;
    public void magic() {
        our = dh.getWedkarz(wedkarz);
        basicInfo.setText("Witaj " + our.getImie() + " " + our.getNazwisko()+"!");


        ArrayList<Turniej> all = dh.getTurnieje();
        all.add(0, new Turniej());
        turniejs=all;

        turniejBT.getItems().setAll(all);
        turniejBT.getSelectionModel().select(0);

        refresh();
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

        dataCL.setCellValueFactory(new PropertyValueFactory<>("kiedy"));
        gdzieCL.setCellValueFactory(new PropertyValueFactory<>("gdzie"));
        rybaCL.setCellValueFactory(new PropertyValueFactory<>("ryba"));
        wagaCL.setCellValueFactory(new PropertyValueFactory<>("waga"));
        pktCL.setCellValueFactory(new PropertyValueFactory<>("punkty"));

        turniejBT.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                refresh();
            }
        });
    }

}