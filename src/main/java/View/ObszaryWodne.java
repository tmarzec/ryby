package View;

import database.DatabaseHandler;
import entities.Okreg;
import exceptions.OkragZbiornik;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ObszaryWodne implements Initializable {

    class CustomItem extends HBox {
        public Okreg getOkreg() {
            return okreg;
        }

        public String getZbiornik() {
            return zbiornik;
        }

        Okreg okreg;
        String zbiornik;
        public boolean isOkrag() {
            return okreg!=null;
        }
        Label text;
        CustomItem(Okreg to) {
            super(5);
            Button btn = new Button("+");
            btn.setStyle("-fx-font-size: 13; -fx-alignment: center-right; -fx-fit-to-width: inherit");
            btn.setScaleX(0.8);
            btn.setScaleY(0.8);
            btn.setAlignment(Pos.CENTER_RIGHT);
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    handleClick(okreg);
                }
            });
            okreg=to;
            text= new Label(to.toString());
            getChildren().addAll(text, btn);
            text.setAlignment(Pos.CENTER_LEFT);
        }
        CustomItem(String zbiornik) {
            super();
            this.zbiornik=zbiornik;
            getChildren().add(new Label(zbiornik));
            setAlignment(Pos.CENTER_LEFT);
        }
    }
    @FXML
    private Button addBT;
    @FXML
    private TextField nameIN;
    @FXML
    private Text welcomeBT;
    @FXML
    private TextField powierzchniaIN;

    @FXML
    private Text warningField;

    Okreg curOkr;
    @FXML
    void addOA(ActionEvent event) {
        try {
            Float.parseFloat(powierzchniaIN.getText());
        }
        catch (Exception e) {
            warningField.setVisible(true);
            warningField.setText("Zły input");
            return;
        }
        try {

            dh.addZbiornik(curOkr, nameIN.getText(), Float.parseFloat(powierzchniaIN.getText()));
        } catch (OkragZbiornik okragZbiornik) {
            warningField.setVisible(true);
            warningField.setText(okragZbiornik.getMessage());
            return;
        }
        warningField.setVisible(false);
        ArrayList<Boolean> exp = new ArrayList<>();
        for(TreeItem<CustomItem> ob: treeView.getRoot().getChildren()) {
            exp.add(ob.isExpanded());
        }
        doMagic();
        for(int i = 0; i < exp.size(); i++) {
            treeView.getRoot().getChildren().get(i).setExpanded(exp.get(i));
        }
    }
    //part with name
    @FXML
    private TextField newName;

    @FXML
    private Button nameFLD;

    @FXML
    void updateName(ActionEvent event) {

    }

    //click on + buttn
    void handleClick(Okreg okrag) {
        welcomeBT.setText("Dodaj zbiornik do "+okrag);
        welcomeBT.setVisible(true);
        addBT.setVisible(true);
        nameIN.setVisible(true);
        powierzchniaIN.setVisible(true);
        curOkr=okrag;
    }

    ArrayList<Okreg> holder = new ArrayList<>();


    @FXML
    private TreeView<CustomItem> treeView;

    ArrayList<TreeItem<CustomItem>> getItems(ArrayList<String> arr) {
        ArrayList<TreeItem<CustomItem>> a = new ArrayList<>();
        for(String x : arr) {
            a.add(new TreeItem<>(new CustomItem(x)));
        }
        return a;
    }
    public void doMagic() {
        holder.clear();
        TreeItem<CustomItem> root = new TreeItem<>();

        ArrayList<String> okregs = dh.getOkregi();

        for(String naz: okregs) {
            Okreg aa = new Okreg(naz);
            ArrayList<String> zbiorniki = dh.getZbiorniki(naz);
            aa.setZbiorniki(zbiorniki);
            holder.add(aa);
        }
        ArrayList<TreeItem<CustomItem>> ro = new ArrayList<>();


        for(Okreg aa: holder) {
            TreeItem<CustomItem> a = new TreeItem<>(new CustomItem(aa));
            a.getChildren().setAll(FXCollections.observableList(getItems(aa.getZbiorniki())));
            ro.add(a);
        }

        root.getChildren().setAll(ro);
        treeView.setRoot(root);
        treeView.setShowRoot(false);

    }


    DatabaseHandler dh;
    public void setDB(DatabaseHandler dh) {
        this.dh = dh;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameIN.setVisible(false);
        welcomeBT.setVisible(false);
        addBT.setVisible(false);
        powierzchniaIN.setVisible(false);
        warningField.setVisible(false);

        treeView.setStyle("-fx-font-size: 15");
        //treeView.setCellFactory(((TreeView<String> t) ->new TeXt));
        treeView.selectionModelProperty().addListener((v , oldVal, newVal) -> System.out.println(oldVal));
    }
}
