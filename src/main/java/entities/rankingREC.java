package entities;

public class rankingREC {
    String imie;
    String nazwisko;
    Float punkty;
    public rankingREC(String imie, String nazwisko,Float punkty){
        this.imie=imie;
        this.nazwisko=nazwisko;
        this.punkty=punkty;
    }
    public String getImie(){
        return imie;
    }
    public String getNazwisko(){
        return nazwisko;
    }
    public Float getPunkty(){
        return punkty;
    }
}
