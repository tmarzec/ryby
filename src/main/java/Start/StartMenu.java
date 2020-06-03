package Start;

import View.MenuWindow;
import database.DatabaseHandler;
import database.DatabaseHandlerImplementation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;

public class StartMenu extends Application {


    public void start(Stage stage) throws Exception {


        DatabaseHandler db = new DatabaseHandlerImplementation();
        db.connect();

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("main.fxml"));
        Pane root = loader.load();
        Scene scene = new Scene(root, 800, 400);
        stage.setScene(scene);

        ((MenuWindow)loader.getController()).setDb(db);

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getIcons().add(new Image("file:src/main/resources/czapka.png"));
        stage.setTitle("Rybak v1");
        stage.show();


        MenuWindow mw = loader.getController();
        mw.setDb(db);
        //ArrayList<String> a = db.getFish();
        //for(String fish: a) {
        //    System.out.println(fish);
        //}
    }


    public static void main(String[] args) {
        launch(args);
    }
}