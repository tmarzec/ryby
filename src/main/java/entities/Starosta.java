package entities;

public class Starosta {
    Integer id;
    String miasto;
    String imie;
    String nazwisko;

    public Starosta(Integer id,String imie, String nazwisko, String miasto) {
        this.id=id;
        this.miasto = miasto;
        this.imie = imie;
        this.nazwisko = nazwisko;
    }

    @Override
    public String toString() {
        return imie+' '+nazwisko+'('+miasto+')';
    }
    public Integer getId() {
        return id;
    }
    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }
}
