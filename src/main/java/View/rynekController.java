package View;


import database.DatabaseHandler;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class rynekController implements Initializable {
    String myFish=null;
    DatabaseHandler dh;
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
    private TableView<String> places;

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
    void DodajCene(ActionEvent event) {

    }

    @FXML
    void DodajOkres(ActionEvent event) {

    }

    @FXML
    void commit(ActionEvent event) {

    }
    public void refresh() {
        Float money=dh.getActualPrice(choiceBox.getValue()); // jedna
        String temp=Float.toString(money);
        price.setText(temp+" zł");
        ArrayList<String> arr = dh.getPlaces(choiceBox.getValue()); // get places
        places.getItems().setAll(arr);
        ArrayList<String> arr2 = dh.getOkresy(choiceBox.getValue()); // get places
        okresyTable.getItems().setAll(arr2);

    }
    private void setImage(Pane pane, String ryba){
        Integer id=dh.getIdFish(ryba);
        Image logo;
        switch (id){
            case 1:
                pane.getChildren().clear();
                logo=new Image("file:src/main/resources/ryby/szczupakf.png");
                pane.getChildren().add(new ImageView(logo));
                break;
            case 2:
                pane.getChildren().clear();
                logo=new Image("file:src/main/resources/ryby/wzdrega.png");
                pane.getChildren().add(new ImageView(logo));
                break;
            case 3:
                pane.getChildren().clear();
                logo=new Image("file:src/main/resources/ryby/wegorz.png");
                pane.getChildren().add(new ImageView(logo));
                break;
            case 4:
                pane.getChildren().clear();
                logo=new Image("file:src/main/resources/ryby/troc.png");
                pane.getChildren().add(new ImageView(logo));
                break;
            case 5:
                pane.getChildren().clear();
                logo=new Image("file:src/main/resources/ryby/ukleja.png");
                pane.getChildren().add(new ImageView(logo));
                break;
            case 6:
                pane.getChildren().clear();
                logo=new Image("file:src/main/resources/ryby/sandacz.png");
                pane.getChildren().add(new ImageView(logo));
                break;
            case 7:
                pane.getChildren().clear();
                logo=new Image("file:src/main/resources/ryby/tolpyga.png");
                pane.getChildren().add(new ImageView(logo));
                break;
            case 8:
                pane.getChildren().clear();
                logo=new Image("file:src/main/resources/ryby/karp.png");
                pane.getChildren().add(new ImageView(logo));
                break;
            case 9:
                pane.getChildren().clear();
                logo=new Image("file:src/main/resources/ryby/lin.png");
                pane.getChildren().add(new ImageView(logo));
                break;
            case 10:
                pane.getChildren().clear();
                logo=new Image("file:src/main/resources/ryby/losos.png");
                pane.getChildren().add(new ImageView(logo));
                break;
            case 11:
                pane.getChildren().clear();
                logo=new Image("file:src/main/resources/ryby/plocf.png");
                pane.getChildren().add(new ImageView(logo));
                break;
            case 12:
                pane.getChildren().clear();
                logo=new Image("file:src/main/resources/ryby/okonf.png");
                pane.getChildren().add(new ImageView(logo));
                break;
            case 13:
                pane.getChildren().clear();
                logo=new Image("file:src/main/resources/ryby/pstrag.png");
                pane.getChildren().add(new ImageView(logo));
                break;
            case 14:
                pane.getChildren().clear();
                logo=new Image("file:src/main/resources/ryby/krap.png");
                pane.getChildren().add(new ImageView(logo));
                break;
            case 15:
                pane.getChildren().clear();
                logo=new Image("file:src/main/resources/ryby/leszcz.png");
                pane.getChildren().add(new ImageView(logo));
                break;
            case 16:
                pane.getChildren().clear();
                logo=new Image("file:src/main/resources/ryby/lipien.png");
                pane.getChildren().add(new ImageView(logo));
                break;
            case 17:
                pane.getChildren().clear();
                logo=new Image("file:src/main/resources/ryby/sumf.png");
                pane.getChildren().add(new ImageView(logo));
                break;
            case 18:
                pane.getChildren().clear();
                logo=new Image("file:src/main/resources/ryby/brzana.png");
                pane.getChildren().add(new ImageView(logo));
                break;
            case 19:
                pane.getChildren().clear();
                logo=new Image("file:src/main/resources/ryby/amurf.png");
                pane.getChildren().add(new ImageView(logo));
                break;
            case 20:
                pane.getChildren().clear();
                logo=new Image("file:src/main/resources/ryby/karas.png");
                pane.getChildren().add(new ImageView(logo));
                break;
            case 21:
                pane.getChildren().clear();
                logo=new Image("file:src/main/resources/ryby/klen.png");
                pane.getChildren().add(new ImageView(logo));
                break;
            case 22:
                pane.getChildren().clear();
                logo=new Image("file:src/main/resources/ryby/bolen.png");
                pane.getChildren().add(new ImageView(logo));
                break;
            case 23:
                pane.getChildren().clear();
                logo=new Image("file:src/main/resources/ryby/doszf.png");
                pane.getChildren().add(new ImageView(logo));
                break;
            case 24:
                pane.getChildren().clear();
                logo=new Image("file:src/main/resources/ryby/jaz.png");
                pane.getChildren().add(new ImageView(logo));
                break;
            default:
                pane.getChildren().clear();
                break;
        }
    }
    public void setChoiceBox() {
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

