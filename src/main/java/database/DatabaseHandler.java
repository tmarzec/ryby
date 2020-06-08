package database;

import entities.*;
import exceptions.*;

import javax.lang.model.type.ArrayType;
import java.util.ArrayList;

public interface DatabaseHandler {
    void connect(String ip, String port, String nazwaBazy, String user, String password);
    ArrayList<Starosta> getStarosciAkt();
    ArrayList<String> getFish();
    ArrayList<String> getFish(String miejsce);
    ArrayList<Wedka> getWedki(int rybak);
    ArrayList<Wedkarz> getWedkarze();
    boolean chroniona(String ryba);
    ArrayList<String> getZbiorniki();
    ArrayList<String> getZbiorniki(String okrag);
    ArrayList<rankingREC> getRanking();
    ArrayList<String> getOkresy(String fish);
    ArrayList<String> getPlaces(String fish);
    Wedkarz getWedkarz(int id);
    void insertWedkarz(Wedkarz ne, int starosta)throws IdIstnieje, ZaMlody;
    void updateWedkarz(int karta, String imie, String nazwisko);
    boolean existsWedk(int wk);
    ArrayList<String> getRodzaje();
    ArrayList<String> getMaterialy();
    ArrayList<String> getOkregi();

    String getOkreg(String zbiornik);
    ArrayList<Starosta> getStarosci();
    void addWedka(int wedkarz, Wedka wedka) throws RodAlrThere;
    void addPrice(String fish,Float money) throws CenaDwaRazy;
    void addWystepowanie(String fish, String zbiornik) throws ParaRybaZbiornikJest;
    Float getActualPrice(String fish);
    Integer getIdFish(String fish);
    Integer getIdZbiornik(String miejsce);
    ArrayList<Polow> getPolowy(int id, Turniej turniej);

    ArrayList<Turniej> getTurnieje(Wedkarz he);
    ArrayList<Turniej> getAktTurnieje();
    ArrayList<Turniej> getFilterTurnieje(String miejsce, String date);
    void addOkres(String fish,String text) throws ZlyOkres;
    void addPolow(Wedkarz wedk, Polow xd, Turniej turn) throws NoProperRod;

    void addZbiornik(Okreg okreg, String name, Float pow) throws OkragZbiornik;
    void updateZbiornik(Okreg okreg, String old, String neww) throws OkragZbiornik;

    ArrayList<rankingREC> getFilteredRanking(Turniej turniej);

    void addTurniej(String miejsce, String rodzaj) throws TurniejIstnieje;

    void updateStarosta(Integer id, boolean coRobic);

    void addStarosta(String text, String text1, String text2, String text3) throws StarostaError;

}
