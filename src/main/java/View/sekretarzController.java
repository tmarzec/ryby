package View;

import database.DatabaseHandler;
import entities.Wedkarz;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.Date;


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
    void openObszary(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ObszaryWodne.fxml"));
        Stage stage=new Stage();
        stage.setScene(new Scene(loader.load()));

        ObszaryWodne obsz = loader.getController();
        obsz.setDB(dh);
        obsz.doMagic();
        stage.show();
    }
    private void refresh() {
        //load wedk
        wedkarze.getItems().setAll(dh.getWedkarze());
    }

    public void doMagic() {


        refresh();
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
        wedkarze.getItems().add(new Wedkarz(1002, "ala","makot", java.sql.Date.valueOf("2020-11-09"), java.sql.Date.valueOf("2020-10-09")));
    }
}
