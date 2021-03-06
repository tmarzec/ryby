package View;

import database.DatabaseHandler;
import entities.Starosta;
import entities.Wedkarz;
import exceptions.IdIstnieje;
import exceptions.ZaMlody;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.Date;


import java.time.LocalDate;
import java.util.ResourceBundle;

public class sekretarzController implements Initializable {

    DatabaseHandler dh;
    @FXML
    private TableView<Wedkarz> wedkarze;

    @FXML
    private TableColumn<Wedkarz, Integer> kartaCL;

    @FXML
    private TableColumn<Wedkarz, String> imieCL;

    @FXML
    private TableColumn<Wedkarz, String> nazwCL;

    @FXML
    private TableColumn<Wedkarz, Date> urodzenieCL;

    @FXML
    private TableColumn<Wedkarz, Date> odCL;

    @FXML
    private Button obszaryBT;
    @FXML
    private Text warningBox;
    @FXML
    void dodajWedkarza(ActionEvent event) {
        try {
            Integer.parseInt(kartaTF.getText());
            if(imieTF.getCharacters().length() == 0 || nazwTF.getCharacters().length()==0||starostaCB.getSelectionModel().isEmpty()||urodzD.getValue()==null
            ) throw new Exception();
            Date.valueOf(urodzD.getValue());
        }
        catch (Exception e) {
            warningBox.setText("błąd");
            warningBox.setVisible(true);
            return;
        }
        Wedkarz ne = new Wedkarz(Integer.parseInt(kartaTF.getText()), imieTF.getCharacters().toString(), nazwTF.getCharacters().toString(),
                java.sql.Date.valueOf(urodzD.getValue()), java.sql.Date.valueOf(LocalDate.now()));
        try {
            dh.insertWedkarz(ne, starostaCB.getSelectionModel().getSelectedItem().getId());
        } catch (IdIstnieje idIstnieje) {
            warningBox.setText("takie id już istnieje");
            warningBox.setVisible(true);
            return;
        } catch (ZaMlody zaMlody) {
            warningBox.setText("wędkarz za młody");
            warningBox.setVisible(true);
            return;
        }
        refresh();
        warningBox.setVisible(false);
    }

    @FXML
    void openObszary(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ObszaryWodne.fxml"));
        Stage stage=new Stage();
        stage.setScene(new Scene(loader.load()));

        ObszaryWodne obsz = loader.getController();
        obsz.setDB(dh);
        obsz.doMagic();
        stage.setResizable(false);
        stage.show();
    }
    private void refresh() {
        //load wedk
        wedkarze.getItems().setAll(dh.getWedkarze());
        starostaCB.getItems().setAll(dh.getStarosciAkt());
    }


    @FXML
    void openStarosci(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("StarosciWindow.fxml"));
        Stage stage=new Stage();
        stage.setScene(new Scene(loader.load()));

        StarosciWindow obsz = loader.getController();
        obsz.setDb(dh);
        obsz.setBoxes();
        obsz.setMe(stage);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private Button zmienBT;

    //change wedk
    @FXML
    void zmienWedk(ActionEvent event) throws IOException {
        if(wedkarze.getSelectionModel().isEmpty()) {
            warningBox.setText("zaznacz wędkarza!");
            warningBox.setVisible(true);
            return;
        }
        Wedkarz a = wedkarze.getSelectionModel().getSelectedItem();
        //open window where new name&naziwkso
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ModifyWedkarz.fxml"));
        Stage stage=new Stage();
        stage.setScene(new Scene(loader.load()));

        ModifyWedkarz wedk = loader.getController();
        wedk.setDh(dh);
        wedk.setA(a);
        wedk.setParent(this);
        wedk.setMe(stage);

        wedk.doMagic();
        stage.setResizable(false);
        stage.show();
        warningBox.setVisible(false);
    }

    @FXML
    private TextField kartaTF;

    @FXML
    private TextField imieTF;

    @FXML
    private TextField nazwTF;

    @FXML
    private DatePicker urodzD;

    @FXML
    private ChoiceBox<Starosta> starostaCB;

    public void doMagic() {
        refresh();
        starostaCB.getSelectionModel().select(0);
    }


    public void setDB(DatabaseHandler dh) {
        this.dh=dh;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        kartaCL.setCellValueFactory(new PropertyValueFactory<>("karta"));
        imieCL.setCellValueFactory(new PropertyValueFactory<>("imie"));
        nazwCL.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        urodzenieCL.setCellValueFactory(new PropertyValueFactory<>("dataUrodzenia"));
        odCL.setCellValueFactory(new PropertyValueFactory<>("dataWydania"));

        warningBox.setVisible(false);

    }
}
