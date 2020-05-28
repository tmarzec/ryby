package entities;

import java.sql.Timestamp;


public class Wedkarz {
    Integer karta;

    public Integer getKarta() {
        return karta;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public Timestamp getDataUrodzenia() {
        return dataUrodzenia;
    }

    public Timestamp getDataWydania() {
        return dataWydania;
    }

    String imie;
    String nazwisko;
    Timestamp dataUrodzenia;
    Timestamp dataWydania;
    public Wedkarz(Integer karta, String imie, String nazwisko, Timestamp du, Timestamp dw) {
        this.karta=karta;
        this.imie=imie;
        this.nazwisko=nazwisko;
        this.dataUrodzenia=du;
        this.dataWydania=dw;
    }
}
