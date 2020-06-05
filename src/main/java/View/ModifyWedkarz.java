package View;

import database.DatabaseHandler;
import entities.Wedkarz;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifyWedkarz {
    DatabaseHandler dh;
    Wedkarz a;
    Stage me;
    sekretarzController parent;

    public void setParent(sekretarzController parent) {
        this.parent = parent;
    }

    public void setMe(Stage me) {
        this.me = me;
    }

    public void setA(Wedkarz a) {
        this.a = a;
    }
    public void doMagic() {
        //
        imieFL.setText(a.getImie());
        nazwiskoFL.setText(a.getNazwisko());
    }
    public void setDh(DatabaseHandler dh) {
        this.dh = dh;
    }
    @FXML
    private TextField imieFL;

    @FXML
    private TextField nazwiskoFL;

    @FXML
    void aktualizuj(ActionEvent event) {
        //update wedkarz
        if(imieFL.getCharacters().length()==0 || nazwiskoFL.getCharacters().length()==0) return;
        dh.updateWedkarz(a.getKarta(), imieFL.getCharacters().toString(), nazwiskoFL.getCharacters().toString());
        parent.doMagic();
        me.close();
    }
}
