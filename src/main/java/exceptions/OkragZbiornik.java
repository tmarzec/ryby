package exceptions;

public class OkragZbiornik extends Exception{
    public OkragZbiornik() {
    }

    @Override
    public String getMessage() {
        return "Ten okręg ma już ten zbiornik!";
    }
}
