package database;


import entities.Polow;
import entities.Turniej;
import entities.Wedka;
import entities.Wedkarz;
import org.postgresql.jdbc4.Jdbc4Array;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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
        ArrayList<Wedka> arr = new ArrayList<>();
        String sql = "select * from projektid.ekwipunek where id_karty="+rybak+" order by 1";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                arr.add(new Wedka(rs.getString(2), rs.getString(3)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arr;
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

    @Override
    public Wedkarz getWedkarz(int id) {
        String sql = "select * from projektid.wędkarze where karta_rybacka="+id;
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            rs.next();
            return new Wedkarz(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4), rs.getTimestamp(5));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean existsWedk(int wkID) {
        String sql = "select exists(select * from projektid.wędkarze where karta_rybacka="+wkID+")";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            boolean res = rs.getBoolean(1);
            System.out.println(res);
            return res;
        }
        catch (Exception e) {

        }
        return false;
    }

    @Override
    public ArrayList<String> getRodzaje() {
        ArrayList<String> arr=new ArrayList<>();
        String sql = "select unnest(enum_range(NULL::rodzaje))";
        return getStrings(arr, sql);
    }

    @Override
    public ArrayList<String> getMaterialy() {
        ArrayList<String> arr=new ArrayList<>();
        String sql = "select unnest(enum_range(NULL::materialy))";
        return getStrings(arr, sql);
    }

    @Override
    public void addWedka(int wedkarz, Wedka wedka) {
        String sql = "insert into projektid.ekwipunek values("+wedkarz+",'"+wedka.getRodzaj()+"','"+wedka.getMaterial()+"')";
        try {
            Statement st = conn.createStatement();
            st.executeUpdate(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Polow> getPolowy(int wedk, Turniej turniej) {
        String sql="select p.data_polowu, zw.nazwa, r.nazwa, p.waga,p.waga*get_cena(p.ryba, p.data_polowu::date) " +
                "from projektid.polowy p join projektid.zbiorniki_wodne zw on zw.id_zbiornika=p.id_zbiornika " +
                "join projektid.ryby r on p.ryba=r.id_ryby where p.wędkarz="+wedk;
        if(!turniej.isDummy()) {
            sql+=" and id_turnieju="+turniej.getId();
        }
        sql += " order by 1 desc";
        ArrayList<Polow> arr = new ArrayList<>();
        try {
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while(rs.next()) {
                arr.add(new Polow(rs.getTimestamp("data_polowu"), rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getFloat(5)));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    @Override
    public ArrayList<Turniej> getTurnieje() {
        String sql="select t.id_turnieju, t.data_turnieju, t.rodzaj_konkurencji, zw.nazwa "+
                "from projektid.turnieje t join projektid.zbiorniki_wodne zw on zw.id_zbiornika=t.miejsce;";

        ArrayList<Turniej> arr = new ArrayList<>();
        try {
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while(rs.next()) {
                arr.add(new Turniej(rs.getInt(1), rs.getString(4), rs.getDate(2), rs.getString(3)));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    private ArrayList<String> getStrings(ArrayList<String> arr, String sql) {
        try {
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while(rs.next()) {
                arr.add(rs.getString(1));
            }
            return arr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }
}
