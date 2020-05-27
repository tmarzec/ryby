package database;

import java.util.ArrayList;

public interface DatabaseHandler {
    void connect();
    ArrayList<String> getFish();
}
