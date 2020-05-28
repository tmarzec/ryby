package database;


import entities.Wedka;
import entities.Wedkarz;
import org.postgresql.core.Encoding;

import java.beans.Encoder;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseHandlerImplementation implements DatabaseHandler {
    Connection conn;
    @Override
    public void connect() {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("conn");
            conn=DriverManager.getConnection("jdbc:postgresql://40.85.112.201:5432/database", "postgres", "morszczukgora");
            System.out.println("successfully connected");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> getFish() {
        ArrayList<String> ret = new ArrayList<>();
        String sql = "select * from projektid.ryby";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                ret.add(rs.getString("nazwa"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    @Override
    public ArrayList<Wedka> getWedki(int rybak) {
        return null;
    }

    @Override
    public ArrayList<Wedkarz> getWedkarze() {
        ArrayList<Wedkarz> arr = new ArrayList<>();
        String sql = "select * from projektid.wędkarze order by 1";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                arr.add(new Wedkarz(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4), rs.getTimestamp(5)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arr;
    }
}
