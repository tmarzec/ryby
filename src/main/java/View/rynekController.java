package View;


import database.DatabaseHandler;
import exceptions.CenaDwaRazy;
import exceptions.ParaRybaZbiornikJest;
import exceptions.ZlyOkres;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class rynekController implements Initializable {
    String myFish=null;
    DatabaseHandler dh;
    final String regExp = "[0-9]+([,.][0-9]{1,2})?";
    final String regExp2 = "(^[1-9]|^1[012])-([1-9]$|1[012]$)";
    public void setBase(DatabaseHandler db) {
        this.dh=db;
    }
    @FXML
    private ComboBox<String> choiceBox;
    @FXML
    private Text fish;
    @FXML
    private Pane rybaPNG;
    @FXML
    private Text price;
    @FXML
    private TextField poleCena;
    private CustomBox wystepowanie;
    @FXML
    private TableView<String> places;
    @FXML
    private ComboBox<String> Zbiorniki;
    @FXML
    private TableColumn<String, String> placeCOL;

    @FXML
    private TableView<String> okresyTable;

    @FXML
    private TableColumn<String, String> okresyCOL;

    @FXML
    private Button priceButton;

    @FXML
    private Button OkresButton;

    @FXML
    private Button commiButton;
    @FXML
    private GridPane Grid;
    @FXML
    private TextField TextInfo;

    @FXML
    void DodajCene(ActionEvent event) {
        if(choiceBox.getValue()==null) return;
        TextInfo.setText("");
        String str=poleCena.getText();
        Float money=null;
        if(!str.matches(regExp) || str.equals("")){
            poleCena.setText("Zły format!");
        }else{
            str=str.replace(',', '.');
            money=new Float(str);
            try {
                dh.addPrice(choiceBox.getValue(),money);
            } catch (CenaDwaRazy cenaDwaRazy) {
                TextInfo.setText("Zmiana ceny tylko raz dziennie!");
            }
        }
        if(choiceBox.getValue()!=null) refresh();
    }
    @FXML
    private TextField okresPole;
    @FXML
    void DodajOkres(ActionEvent event) {
        TextInfo.setText("");
        if(okresPole.getText()=="" || !okresPole.getText().matches(regExp2)){
            okresPole.setText("Zły przedział! (a-b : a i b in [1-12])");
        }else if(choiceBox.getValue()==null){
            okresPole.setText("Wybierz rybę");
        }else{
            try {
               if(choiceBox.getValue()!=null) dh.addOkres(choiceBox.getValue(),okresPole.getText());
            } catch (ZlyOkres zlyOkres) {
               TextInfo.setText("Przedziały się przecinają!");
            }
        }
        if(choiceBox.getValue()!=null) refresh();
    }
    @FXML
    void setZbiorniki(ActionEvent event) {

    }
    @FXML
    void commit(ActionEvent event) {
        TextInfo.setText("");
        if(wystepowanie.getValue()==null || Zbiorniki.getValue()==null || choiceBox.getValue()==null) TextInfo.setText("Najpierw wybierz miejsce i rybę!");
        else{
            try{
                dh.addWystepowanie(choiceBox.getValue(),Zbiorniki.getValue());
            } catch (ParaRybaZbiornikJest paraRybaZbiornikJest) {
                TextInfo.setText("Ryba już występuje w tym zbiorniku!");
            }
        }
        if(choiceBox.getValue()!=null) refresh();
    }
    public void refresh() {
        Float money=dh.getActualPrice(choiceBox.getValue()); // jedna
        String temp=null;
        if(money!=null) temp=Float.toString(money);
        price.setText(temp+" zł");
        ArrayList<String> arr = dh.getPlaces(choiceBox.getValue()); // get places
        places.getItems().setAll(arr);
        ArrayList<String> arr2 = dh.getOkresy(choiceBox.getValue()); // get places
        okresyTable.getItems().setAll(arr2);
        //TextInfo.setText("");

    }
    private void setImage(Pane pane, String ryba){
        //Integer id=dh.getIdFish(ryba);
        Image logo;
        pane.getChildren().clear();
        //if picture not found do sth
        logo=new Image("file:src/main/resources/ryby/"+ryba+".png");
        pane.getChildren().add(new ImageView(logo));
    }
    public void setChoiceBox() {
       // WystepowanieBox.setItems(FXCollections.observableArrayList(dh.getOkregi()));
        wystepowanie=new CustomBox(FXCollections.observableArrayList(dh.getOkregi()));
        wystepowanie.getItems();
        Grid.add(wystepowanie,1,0);

        wystepowanie.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                if(wystepowanie.getValue()==null)
                    TextInfo.setText("Najpierw wybierz okrąg!");
                else{
                    Zbiorniki.setItems(FXCollections.observableArrayList(dh.getZbiorniki(wystepowanie.getValue())));
                    Zbiorniki.setPromptText("Zbiorniki wodne");
                }
            }
        });

        choiceBox.setItems(FXCollections.observableArrayList(dh.getFish()));
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                setImage(rybaPNG,choiceBox.getValue());
                refresh();
            }
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        placeCOL.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue()));
        okresyCOL.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue()));
    }
}

