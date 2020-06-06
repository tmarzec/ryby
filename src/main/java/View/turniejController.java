package View;

        import database.DatabaseHandler;
        import entities.Turniej;
        import entities.TurniejIstnieje;
        import entities.rankingREC;
        import exceptions.RodAlrThere;
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
        import java.util.Random;
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
    private ComboBox<String> MiejsceBox;

    @FXML
    private ComboBox<String> RodzajBox;

    @FXML
    private DatePicker DataBox;

    @FXML
    private TextField InofLab;

    @FXML
    private Text Cytaty;
    @FXML
    private Button nowyCytat;
    @FXML
    private Text Autor;
    private int oldId=1;
    @FXML
    void generateCytat(ActionEvent event) {
        Random rand = new Random();
        int id=1;
        while(id==oldId){
            id =rand.nextInt(9);
        }
       oldId=id;
        switch (id){
            case 1:
                Cytaty.setText("„Nie ma ryby bez ości i człowieka bez wad.”");
                Autor.setText("przysłowie norweskie");
                break;
            case 2:
                Cytaty.setText("„Człowiek bez swobody jak ryba bez wody.”");
                Autor.setText("przysłowie polskie");
                break;
            case 3:
                Cytaty.setText("„Machaj wędą – ryby będą.”");
                Autor.setText("autor nieznany");
                break;
            case 4:
                Cytaty.setText("„Jak ryby na wędkę, tak ludzi łowią na grzeczność.”");
                Autor.setText("przysłowie polskie");
                break;
            case 5:
                Cytaty.setText("„Ryba widzi nie haczyk, a przynętę.”");
                Autor.setText("przysłowie rosyjskie");
                break;
            case 6:
                Cytaty.setText("„Gdyby ptak kochał rybę, gdzie mogliby zamieszkać?.”");
                Autor.setText("Donya Al-Nahi");
                break;
            case 7:
                Cytaty.setText("Leszcz bierze w deszcz.");
                Autor.setText("autor nieznany");
                break;
            case 8:
                Cytaty.setText("„Mówią, że wędkarstwo to sztuka cierpliwości”");
                Autor.setText("Edward Michael Grylls");
                break;
            case 9:
                Cytaty.setText("„Ryba psuje się od głowy, a człowiek od serca.”");
                Autor.setText("Mariusz Fiedorek");
                break;
        }

    }
    @FXML
    void DodajTurniej(ActionEvent event) {
        if(MiejsceBox.getValue()==null || RodzajBox.getValue()==null)
            InofLab.setText("Najpierw wybierz miejsce i rodzaj!");
        else{
            try {
                dh.addTurniej(MiejsceBox.getValue(), RodzajBox.getValue());
            } catch (TurniejIstnieje e) {
               InofLab.setText("Taki turniej już istnieje!");
            }
        }
        refresh();
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
        RodzajBox.getItems().setAll(dh.getRodzaje());
        MiejsceBox.getItems().setAll(dh.getZbiorniki());

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
