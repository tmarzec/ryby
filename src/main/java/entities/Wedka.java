package entities;

public class Wedka {
    String rodzaj;
    String material;

    public String getRodzaj() {
        return rodzaj;
    }

    public String getMaterial() {
        return material;
    }

    public Wedka(String rodzaj, String material) {
        this.rodzaj=rodzaj;
        this.material=material;
    }
}
