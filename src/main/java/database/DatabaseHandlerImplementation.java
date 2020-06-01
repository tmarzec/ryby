package database;


import entities.Polow;
import entities.Turniej;


import entities.Wedka;
import entities.Wedkarz;
import org.postgresql.jdbc4.Jdbc4Array;

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
    public Integer getIdFish(String fish){
        Integer ret=null;
        String sql = "select * from projektid.ryby where nazwa="+"'"+fish+"'";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            ret=rs.getInt(1);
        }
        catch (Exception e) {
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
    public ArrayList<String> getZbiorniki() {
        ArrayList<String> arr = new ArrayList<>();
        String sql = "select nazwa from projektid.zbiorniki_wodne";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                arr.add(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }
    private String miesiac(int id){
        switch (id){
            case 1:
                return "Sty";
            case 2:
                return "Luty";
            case 3:
                return "Marz";
            case 4:
                return "Kwi";
            case 5:
                return "Maj";
            case 6:
                return "Czerw";
            case 7:
                return "Lip";
            case 8:
                return "Sie";
            case 9:
                return "Wrz";
            case 10:
                return "Paź";
            case 11:
                return "Lis";
            case 12:
                return "Gru";
            default:
                return "-";
        }
    }

    @Override
    public ArrayList<String> getOkresy(String fish) {
        Integer id=getIdFish(fish);
        ArrayList<String> arr = new ArrayList<>();
        String sql="select od_msc from projektid.okresy_ochronne where id_ryby="+id;
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                arr.add( miesiac(rs.getInt(1)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(String a : arr) a=a+"-";
        sql="select do_msc  from projektid.okresy_ochronne where id_ryby="+id;
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            int i=0;
            while (rs.next()) {
                arr.set(i,arr.get(i) + miesiac(rs.getInt(1)));
               i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(String a: arr)
            System.out.println(a);
        return arr;
    }

    @Override
    public ArrayList<String> getPlaces(String fish) {
        Integer id=getIdFish(fish);
        ArrayList<String> arr = new ArrayList<>();
        String sql = "select zw.nazwa from projektid.wystepowanie_ryb wr join projektid.zbiorniki_wodne zw on wr.zbiornik=zw.id_zbiornika where wr.ryba="+id+" order by 1";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                arr.add(rs.getString(1));
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

    @Override
    public Float getActualPrice(String fish) {
        Float toReturn=null;
        Integer id=getIdFish(fish);
        String sql="select cena from projektid.historia_cen_ryb where id_ryby="+id+"order by data_wprowadzenia DESC limit 1";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            toReturn=rs.getFloat(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return toReturn;
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
