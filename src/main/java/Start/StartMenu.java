package Start;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StartMenu extends Application {


    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("main.fxml"));
        Pane root = loader.load();
        Scene scene = new Scene(root, 800, 400);
        stage.setScene(scene);
        //stage.initStyle(StageStyle.TRANSPARENT);
        //stage.getIcons().add(new Image("file:src/main/resources/czapka.png"));
        stage.setTitle("Rybak v1");
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}