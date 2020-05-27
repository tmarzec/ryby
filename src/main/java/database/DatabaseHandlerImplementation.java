package database;


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
        ArrayList<String> ret = null;
        String sql = "select * from projektid.ryby";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {
                String a = rs.getString("nazwa");
                System.out.println(a);
            }

            //ret= (ArrayList<String>) rs.getArray(1);
            //ret=rs.getArray("nazwa");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
}
