package View;

import database.DatabaseHandler;
import entities.Wedka;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class WedkarzID implements Initializable {

    DatabaseHandler dh;
    Stage me;


    @FXML
    private TextField fieldID;

    @FXML
    private Button loginB;


    private void loadWedk(int id) {
        try {
            FXMLLoader wedkarzLoader = new FXMLLoader(getClass().getClassLoader().getResource("wedkarz.fxml"));
            Pane wedkarzPane = wedkarzLoader.load();
            Stage wedkarzStage=new Stage();
            wedkarzStage.setScene(new Scene(wedkarzPane));
            wedkarzController wedkarzController = wedkarzLoader.getController();
            wedkarzController.setBase(dh);
            wedkarzController.setID(id);
            wedkarzController.magic();
            wedkarzStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void doStuff(ActionEvent event) {
        //is id ok?
        String a = fieldID.getText();
        if(a.length()>0 && a.matches("[0-9]+") && dh.existsWedk(Integer.parseInt(a))) { //ok
            System.out.println("loadwedk");
            loadWedk(Integer.parseInt(a));
            me.close();
        }
        else {
            //print warning, no such wedkarz
        }
    }

    void setDB(DatabaseHandler dh) {
        this.dh=dh;
    }

    void setStage(Stage too) {
        me = too;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
