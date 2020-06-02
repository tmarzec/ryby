package database;

import entities.Polow;
import entities.Turniej;
import entities.Wedka;
import entities.Wedkarz;

import java.util.ArrayList;

public interface DatabaseHandler {
    void connect();
    ArrayList<String> getFish();
    ArrayList<Wedka> getWedki(int rybak);
    ArrayList<Wedkarz> getWedkarze();

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
    Float getActualPrice(String fish);
    Integer getIdFish(String fish);
    ArrayList<Polow> getPolowy(int id, Turniej turniej);
    ArrayList<Turniej> getTurnieje();
    ArrayList<Turniej> getAktTurnieje();


}
