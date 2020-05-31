package entities;

import java.sql.Timestamp;

public class Polow {
    Timestamp kiedy;
    String gdzie;
    String ryba;
    Float waga;
    Float punkty;

    public Polow(Timestamp kiedy, String gdzie, String ryba, Float waga, Float punkty) {
        this.kiedy = kiedy;
        this.gdzie = gdzie;
        this.ryba = ryba;
        this.waga = waga;
        this.punkty = punkty;
    }

    public Timestamp getKiedy() {
        return kiedy;
    }

    public String getGdzie() {
        return gdzie;
    }

    public String getRyba() {
        return ryba;
    }

    public Float getWaga() {
        return waga;
    }

    public Float getPunkty() {
        return punkty;
    }

}
