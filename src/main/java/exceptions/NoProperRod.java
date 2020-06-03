package exceptions;

public class NoProperRod extends Exception {
    String msg;
    public NoProperRod(Exception e) {
        msg="Wędkarz nie posiada odpowiedniej wędki!";
    }

    public String getMsg() {
        return msg;
    }
}
