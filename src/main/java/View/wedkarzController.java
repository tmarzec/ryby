package View;

import database.DatabaseHandler;
import entities.Polow;
import entities.Turniej;
import entities.Wedka;
import entities.Wedkarz;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
        System.out.println(wagaIN.getCharacters());

        ArrayList<Wedka> arr = dh.getWedki(wedkarz);

        wedki.getItems().setAll(arr);
        materialyCB.getItems().setAll(dh.getMaterialy());
        rodzajeCB.getItems().setAll(dh.getRodzaje());
        polowy.getItems().setAll(dh.getPolowy(wedkarz, turniejBT.getSelectionModel().getSelectedItem()));

        //String okr = okregIN.getSelectionModel().getSelectedItem();
        //zbiornikIN.getItems().setAll(dh.getZbiorniki(okr));

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



        ArrayList<Turniej> turniejs = dh.getAktTurnieje();
        turniejIN.getItems().setAll(turniejs);
        turniejIN.getSelectionModel().select(0);

        turniejON.selectedProperty().setValue(false);
        turniejIN.setDisable(true);
        if(turniejs.isEmpty()) {
            turniejIN.setDisable(true);
            turniejON.setDisable(true);
            turniejON.selectedProperty().setValue(false);
        }

        turniejIN.setOnAction(new EventHandler<ActionEvent>() {//setting miejsce when its disabled
            @Override
            public void handle(ActionEvent actionEvent) {
                zbiornikIN.getSelectionModel().select(turniejIN.getSelectionModel().getSelectedItem().getMiejsce());
            }
        });
        zbiornikIN.setOnAction(new EventHandler<ActionEvent>(){//setting rybs when ...
            @Override
            public void handle(ActionEvent actionEvent) {
                rybaIN.getItems().clear();
                ArrayList<String> fish = dh.getFish(zbiornikIN.getSelectionModel().getSelectedItem());
                for(String morszczuk:fish) {
                    if(!dh.chroniona(morszczuk)) {
                        rybaIN.getItems().add(morszczuk);
                    }
                }
                //rybaIN.getItems().setAll(dh.getFish(zbiornikIN.getSelectionModel().getSelectedItem()));
                rybaIN.getSelectionModel().select(0);
            }
        });
        zbiornikIN.getItems().setAll(dh.getZbiorniki());

        rybaIN.getItems().setAll(dh.getFish());


        refresh();
    }
    //public void setStage(Stage stage){
    //    owner=stage;
    //}

    @FXML
    private ChoiceBox<String> zbiornikIN;

    @FXML
    private ChoiceBox<String> rybaIN;

    @FXML
    private Button inpPolow;

    @FXML
    void insPolow(ActionEvent event) {
        //add button
        Turniej xd = new Turniej();
        if(turniejON.selectedProperty().get()) {
            xd = turniejIN.getSelectionModel().getSelectedItem();
        }
        //bad input to waga?
        Polow a = new Polow("", zbiornikIN.getSelectionModel().getSelectedItem(), rybaIN.getSelectionModel().getSelectedItem(),
                Float.parseFloat(wagaIN.getCharacters().toString()), 0.f);
        dh.addPolow(our, a, xd);
        refresh();
    }
    //@FXML
    //private ChoiceBox<String> okregIN;

    @FXML
    private TextField wagaIN;

    @FXML
    private CheckBox turniejON;

    @FXML
    private ChoiceBox<Turniej> turniejIN;

    @FXML
    void showHide(ActionEvent event) {
        boolean disable = !turniejON.selectedProperty().get();
        boolean enable = !disable;
        turniejIN.setDisable(disable);
        if(enable) {
            zbiornikIN.getSelectionModel().select(turniejIN.getSelectionModel().getSelectedItem().getMiejsce());
            zbiornikIN.setDisable(true);
        }
        else {
            zbiornikIN.setDisable(false);
        }
    }



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
        rybaIN.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                refresh();
            }
        });
    }

}