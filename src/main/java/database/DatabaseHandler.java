package database;

import entities.Wedka;
import entities.Wedkarz;

import java.util.ArrayList;

public interface DatabaseHandler {
    void connect();
    ArrayList<String> getFish();
    ArrayList<Wedka> getWedki(int rybak);
    ArrayList<Wedkarz> getWedkarze();
    Wedkarz getWedkarz(int id);
    boolean existsWedk(int wk);
    ArrayList<String> getRodzaje();
    ArrayList<String> getMaterialy();
    void addWedka(int wedkarz, Wedka wedka);
}
