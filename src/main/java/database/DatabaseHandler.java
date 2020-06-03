package database;

import entities.Polow;
import entities.Turniej;
import entities.Wedka;
import entities.Wedkarz;
import exceptions.CenaDwaRazy;

import java.util.ArrayList;

public interface DatabaseHandler {
    void connect();
    ArrayList<String> getFish();
    ArrayList<String> getFish(String miejsce);
    ArrayList<Wedka> getWedki(int rybak);
    ArrayList<Wedkarz> getWedkarze();
    boolean chroniona(String ryba);
    ArrayList<String> getZbiorniki();
    ArrayList<String> getZbiorniki(String okrag);

    ArrayList<String> getOkresy(String fish);
    ArrayList<String> getPlaces(String fish);
    Wedkarz getWedkarz(int id);
    boolean existsWedk(int wk);
    ArrayList<String> getRodzaje();
    ArrayList<String> getMaterialy();
    ArrayList<String> getOkregi();

    void addWedka(int wedkarz, Wedka wedka);
    void addPrice(String fish,Float money) throws CenaDwaRazy;
    Float getActualPrice(String fish);
    Integer getIdFish(String fish);
    Integer getIdZbiornik(String miejsce);
    ArrayList<Polow> getPolowy(int id, Turniej turniej);
    ArrayList<Turniej> getTurnieje();
    ArrayList<Turniej> getAktTurnieje();

    void addPolow(Wedkarz wedk, Polow xd, Turniej turn);
}
