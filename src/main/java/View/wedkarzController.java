package View;

import database.DatabaseHandler;
import entities.*;
import exceptions.NoProperRod;
import exceptions.RodAlrThere;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Timestamp;
import java.text.DecimalFormat;
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
        ekwExc.setVisible(false);
        if(materialyCB.getSelectionModel().isEmpty() || rodzajeCB.getSelectionModel().isEmpty()) return;
        try {
            dh.addWedka(wedkarz, new Wedka(rodzajeCB.getSelectionModel().getSelectedItem(),materialyCB.getSelectionModel().getSelectedItem()));
        }
        catch (RodAlrThere e) {
            ekwExc.setVisible(true);
            ekwExc.setText(e.getMessage());
        }
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

    //top right
    @FXML
    private ComboBox<Turniej> turniejBT;

    public void setID(int id) {
        wedkarz=id;
    }

    public void refreshPolows() {
        if(turniejBT.getSelectionModel().isEmpty()) return;
        ArrayList<Polow> polows = dh.getPolowy(wedkarz, turniejBT.getSelectionModel().getSelectedItem());
        Float a = 0.f;
        for(Polow kk: polows) {
            a+=kk.getPunkty();
        }
        DecimalFormat aa = new DecimalFormat();
        aa.setMaximumFractionDigits(2);
        sumBT.setText(aa.format(a));
        polowy.getItems().setAll(polows);
    }
    public void refresh() {

        ArrayList<Wedka> arr = dh.getWedki(wedkarz);
        wedki.getItems().setAll(arr);


        ArrayList<Turniej> all = dh.getTurnieje(our);
        all.add(0, new Turniej());
        turniejs=all;
        turniejBT.getItems().setAll(all);

        if(turniejBT.getSelectionModel().isEmpty())
        turniejBT.getSelectionModel().select(0);

        //fill polowy


        //String okr = okregIN.getSelectionModel().getSelectedItem();
        //zbiornikIN.getItems().setAll(dh.getZbiorniki(okr));


    }
    ComboBox<String> okregBox;
    @FXML
    private Text warningText;

    ArrayList<Turniej> turniejs;
    public void magic() {

        materialyCB.getItems().setAll(dh.getMaterialy());
        rodzajeCB.getItems().setAll(dh.getRodzaje());

        ekwExc.setVisible(false);
        our = dh.getWedkarz(wedkarz);
        basicInfo.setText("Witaj " + our.getImie() + " " + our.getNazwisko()+"!");



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
                okregBox.getSelectionModel().select(turniejIN.getSelectionModel().getSelectedItem().getPzw());
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

        //okregBox = new CustomBox(FXCollections.observableList(dh.getOkregi()));
        okregBox=new ComboBox<>(FXCollections.observableList(dh.getOkregi()));
        //when pzw changed
        okregBox.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> {
            zbiornikIN.getItems().setAll(dh.getZbiorniki(okregBox.getSelectionModel().getSelectedItem()));
            if(zbiornikIN.getSelectionModel().isEmpty()) zbiornikIN.getSelectionModel().select(0);
        });
        placeHolder.getChildren().add(0, okregBox);
        rybaIN.getItems().setAll(dh.getFish());

        okregBox.getSelectionModel().select(0);
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
    private GridPane placeHolder;
    @FXML
    private Text ekwExc;

    @FXML
    void insPolow(ActionEvent event) {
        boolean badInp=false;
        boolean badWei=false;
        if(wagaIN.getCharacters().length() == 0 || zbiornikIN.getSelectionModel().getSelectedIndex()==-1 || rybaIN.getSelectionModel().getSelectedIndex()==-1) badInp=true;
        try {
            if(!badInp)
            if(Float.parseFloat(wagaIN.getCharacters().toString())>100.f) badWei=true;
        }
        catch (Exception e) {
            badInp=true;
        }
        if(badWei){
            warningText.setText("zbyt duża waga!");
            warningText.setVisible(true);
            return;
        }
        if(badInp) {
            warningText.setText("Bad input");
            warningText.setVisible(true);
            return;
        }
        //add button
        Turniej xd = new Turniej();
        if(turniejON.selectedProperty().get()) {
            xd = turniejIN.getSelectionModel().getSelectedItem();
        }
        Polow a = new Polow("", zbiornikIN.getSelectionModel().getSelectedItem(), rybaIN.getSelectionModel().getSelectedItem(),
                Float.parseFloat(wagaIN.getCharacters().toString()), 0.f);
        try {
            dh.addPolow(our, a, xd);
        }
        catch (NoProperRod nop) {
            warningText.setVisible(true);
            warningText.setText(nop.getMsg());
            return;
        }
        warningText.setVisible(false);
        refresh();
    }
    //@FXML
    //private ChoiceBox<String> okregIN;

    @FXML
    private Text sumBT;
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
            okregBox.getSelectionModel().select(turniejIN.getSelectionModel().getSelectedItem().getPzw());
            zbiornikIN.getSelectionModel().select(turniejIN.getSelectionModel().getSelectedItem().getMiejsce());

            placeHolder.setDisable(true);
        }
        else {
            placeHolder.setDisable(false);
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
                refreshPolows();
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