package View;

import database.DatabaseHandler;
import entities.Starosta;
import entities.StarostaError;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class StarosciWindow implements Initializable {
    DatabaseHandler dh;

    public void setDb(DatabaseHandler dh) {
        this.dh = dh;
    }


    public void setMe(Stage me) {
        this.me = me;
    }
    Stage me;


    @FXML
    private TableView<Starosta> StarosciTable;

    @FXML
    private TableColumn<Starosta, String> ImieCol;

    @FXML
    private TableColumn<Starosta, String> NazwiskoCol;

    @FXML
    private TableColumn<Starosta, String> MiastoCol;

    @FXML
    private TableColumn<Starosta, Boolean> StatusCol;

    @FXML
    private Button DodajButton;

    @FXML
    private Button AktywujButton;

    @FXML
    private Button ZakonczButton;

    @FXML
    private TextField InfoLab;

    @FXML
    private TextField imieField;

    @FXML
    private TextField NazwiskoField;

    @FXML
    private TextField MiastoField;

    @FXML
    private TextField AktywnyField;

    @FXML
    void AktywujStaroste(ActionEvent event) {
        if(StarosciTable.getSelectionModel().isEmpty())
            InfoLab.setText("Najpierw wybierz starostę!");
        else {
            Starosta temp=StarosciTable.getSelectionModel().getSelectedItem();
            try{
                dh.updateStarosta(temp.getId(), true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        setBoxes();
    }

    @FXML
    void DodajStaroste(ActionEvent event) {
        if(imieField.getText().equals("") || NazwiskoField.getText().equals("") || MiastoField.getText().equals("")
                || AktywnyField.getText().equals(""))
            InfoLab.setText("Wypełnij wszystkie pola!");
        else{
            try {
                dh.addStarosta(imieField.getText(),NazwiskoField.getText(),MiastoField.getText(),AktywnyField.getText());
            } catch (StarostaError starostaError) {
                starostaError.printStackTrace();
            }
        }
        setBoxes();
    }

    @FXML
    void ZakonczKadencje(ActionEvent event) {
        if(StarosciTable.getSelectionModel().isEmpty())
            InfoLab.setText("Najpierw wybierz starostę!");
        else {
            Starosta temp=StarosciTable.getSelectionModel().getSelectedItem();
            try{
                dh.updateStarosta(temp.getId(), false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        setBoxes();
    }
    public void setBoxes(){
        StarosciTable.getItems().setAll(dh.getStarosci());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ImieCol.setCellValueFactory(new PropertyValueFactory<>("imie"));
        NazwiskoCol.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        MiastoCol.setCellValueFactory(new PropertyValueFactory<>("miasto"));
        StatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
}
