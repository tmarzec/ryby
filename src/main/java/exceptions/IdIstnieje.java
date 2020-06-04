package exceptions;

public class IdIstnieje extends Exception{
    @Override
    public String toString() {
        return "takie id już jest w bazie";
    }
}
