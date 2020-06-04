package entities;

import java.sql.Date;
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

    public Date getDataUrodzenia() {
        return dataUrodzenia;
    }

    public Date getDataWydania() {
        return dataWydania;
    }

    String imie;
    String nazwisko;
    Date dataUrodzenia;
    Date dataWydania;
    public Wedkarz(Integer karta, String imie, String nazwisko, Date du, Date dw) {
        this.karta=karta;
        this.imie=imie;
        this.nazwisko=nazwisko;
        this.dataUrodzenia=du;
        this.dataWydania=dw;
    }
}
