package entities;

import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Okreg {
    String nazwa;

    public void setZbiorniki(ArrayList<String> zbiorniki) {
        this.zbiorniki = zbiorniki;
    }

    public ArrayList<String> getZbiorniki() {
        return zbiorniki;
    }

    ArrayList<String> zbiorniki;
    public Okreg(String a) {
        nazwa=a;
    }

    @Override
    public String toString() {
        return nazwa;
    }
}
