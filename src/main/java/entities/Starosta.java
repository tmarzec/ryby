package entities;

public class Starosta {
    Integer id;
    String miasto;
    String imie;
    String nazwisko;
    Boolean status;
    public Starosta(Integer id,String imie, String nazwisko, String miasto) {
        this.id=id;
        this.miasto = miasto;
        this.imie = imie;
        this.nazwisko = nazwisko;
    }
    public Starosta(Starosta obj) {
        this.id=obj.id;
        this.miasto = obj.miasto;
        this.imie = obj.imie;
        this.nazwisko = obj.nazwisko;
        this.status=obj.status;
    }
    public Starosta(Integer id,String imie, String nazwisko, String miasto, Boolean status) {
        this.id=id;
        this.miasto = miasto;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.status=status;
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
    public Boolean getStatus() {
        return status;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }
}
