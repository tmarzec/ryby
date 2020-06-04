package exceptions;

public class OkragZbiornik extends Exception{
    public OkragZbiornik() {
        super(new Exception("Ten okrąg ma już ten zbiornik"));
    }
}
