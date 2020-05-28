package View;

import database.DatabaseHandler;
import entities.Wedka;
import entities.Wedkarz;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class wedkarzController implements Initializable {
    Stage owner;
    DatabaseHandler dh;
    @FXML
    private Button BackButton;

    @FXML
    void Back(ActionEvent event) {

    }

    @FXML
    private TableView<Wedkarz> tableView;
    @FXML
    private TableColumn<Wedkarz, String> imie;

    @FXML
    private TableColumn<Wedkarz, String> nazwisko;

    @FXML
    private TableColumn<Wedkarz, Timestamp> dataUrodz;

    @FXML
    private TableColumn<Wedkarz, Timestamp> dataWydania;
    public void setStage(Stage stage){
        owner=stage;
    }

    public void setBase(DatabaseHandler db) {
        this.dh=db;
        ArrayList<Wedkarz> wkrze = dh.getWedkarze();
        for(Wedkarz a : wkrze) {
            tableView.getItems().add(a);
        }
        tableView.refresh();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imie.setCellValueFactory(new PropertyValueFactory<>("imie"));
        nazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        dataUrodz.setCellValueFactory(new PropertyValueFactory<>("dataUrodzenia"));
        dataWydania.setCellValueFactory(new PropertyValueFactory<>("dataWydania"));
    }

}
