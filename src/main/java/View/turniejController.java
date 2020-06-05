package View;

        import database.DatabaseHandler;
        import entities.Turniej;
        import entities.rankingREC;
        import javafx.beans.value.ChangeListener;
        import javafx.beans.value.ObservableValue;
        import javafx.collections.FXCollections;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.control.Button;
        import javafx.scene.control.ComboBox;
        import javafx.scene.control.DatePicker;
        import javafx.scene.control.TableColumn;
        import javafx.scene.control.TableView;
        import javafx.scene.control.TextField;
        import javafx.scene.control.cell.PropertyValueFactory;
        import javafx.scene.input.MouseButton;
        import javafx.scene.input.MouseEvent;
        import javafx.scene.layout.Pane;
        import javafx.scene.text.Text;
        import tools.CustomBox;

        import java.net.URL;
        import java.time.format.DateTimeFormatter;
        import java.util.Date;
        import java.util.ResourceBundle;

public class turniejController implements Initializable {
    DatabaseHandler dh;
    public void setBase(DatabaseHandler db) {
        this.dh=db;
    }
    private CustomBox zbiorniki;
    @FXML
    private TableView<Turniej> TurniejeTable;
    @FXML
    private Pane PaneForCustom;
    @FXML
    private TableColumn<Turniej, String> MiejsceCol;

    @FXML
    private TableColumn<Turniej, Date> DataCol;

    @FXML
    private DatePicker DataFiltr;

    @FXML
    private ComboBox<?> MiejsceFiltr;

    @FXML
    private TableView<rankingREC> RankingTable;

    @FXML
    private TableColumn<rankingREC, String> ImieCol;

    @FXML
    private TableColumn<rankingREC, String> NazwiskoCol;

    @FXML
    private TableColumn<rankingREC, Float> SumaPolowCol;

    @FXML
    private Button DodajButton;

    @FXML
    private ComboBox<?> MiejsceBox;

    @FXML
    private ComboBox<?> RodzajBox;

    @FXML
    private DatePicker DataBox;

    @FXML
    private TextField InofLab;

    @FXML
    private Text Cytaty;

    @FXML
    private Text Autor;

    @FXML
    void DodajTurniej(ActionEvent event) {

    }
    public void refresh(){
        String miejsce=zbiorniki.getValue();
        String date=null;
        if(DataFiltr.getValue()!=null)
            date = DataFiltr.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(miejsce);
        System.out.println(date);
        TurniejeTable.getItems().setAll(dh.getFilterTurnieje(miejsce, date));

    }
    @FXML
    void dataAction(ActionEvent event) {
        refresh();
    }
    public void setBoxes() {
        zbiorniki = new CustomBox(FXCollections.observableArrayList(dh.getZbiorniki()));
        PaneForCustom.getChildren().add(zbiorniki);
        zbiorniki.setPromptText("Wszystkie");
        TurniejeTable.getItems().setAll(dh.getTurnieje());

        RankingTable.getItems().setAll(dh.getRanking());

        zbiorniki.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                refresh();
            }
        });
        TurniejeTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                refreshRanking(TurniejeTable.getSelectionModel().getSelectedItem());
            }
        });
    }
    private void refreshRanking(Turniej turniej){
        RankingTable.getItems().setAll(dh.getFilteredRanking(turniej));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MiejsceCol.setCellValueFactory(new PropertyValueFactory<>("miejsce"));
        DataCol.setCellValueFactory(new PropertyValueFactory<>("data"));

        ImieCol.setCellValueFactory(new PropertyValueFactory<>("imie"));
        NazwiskoCol.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        SumaPolowCol.setCellValueFactory(new PropertyValueFactory<>("punkty"));
    }
}
