package database;

import entities.*;
import exceptions.*;

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

    String getOkreg(String zbiornik);

    void addWedka(int wedkarz, Wedka wedka) throws RodAlrThere;
    void addPrice(String fish,Float money) throws CenaDwaRazy;
    void addWystepowanie(String fish, String zbiornik) throws ParaRybaZbiornikJest;
    Float getActualPrice(String fish);
    Integer getIdFish(String fish);
    Integer getIdZbiornik(String miejsce);
    ArrayList<Polow> getPolowy(int id, Turniej turniej);
    ArrayList<Turniej> getTurnieje();
    ArrayList<Turniej> getAktTurnieje();
    void addOkres(String fish,String text) throws ZlyOkres;
    void addPolow(Wedkarz wedk, Polow xd, Turniej turn) throws NoProperRod;

    void addZbiornik(Okreg okreg, String name, Float pow) throws OkragZbiornik;
    void updateZbiornik(Okreg okreg, String old, String neww) throws OkragZbiornik;
}
