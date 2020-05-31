package entities;

import java.sql.Date;

public class Turniej {
    boolean dummy = false;
    String miejsce;
    Date data;
    String rodzaj;
    public String getMiejsce() {
        return miejsce;
    }

    public Date getData() {
        return data;
    }

    public int getId() {
        return id;
    }

    int id;
    public Turniej(int id, String miejsce, Date data, String rodzaj) {
        this.miejsce = miejsce;
        this.data = data;
        this.id=id;
        this.rodzaj=rodzaj;
    }
    public Turniej() {
        dummy=true;
    }
    public boolean isDummy() {
        return dummy;
    }
    @Override
    public String toString() {
        if(dummy) return "wszystkie";
        return miejsce+'('+data+')';
    }
}
