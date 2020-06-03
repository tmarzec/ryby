package database;


import entities.Polow;
import entities.Turniej;


import entities.Wedka;
import entities.Wedkarz;
import exceptions.CenaDwaRazy;
import exceptions.NoProperRod;
import exceptions.RodAlrThere;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class DatabaseHandlerImplementation implements DatabaseHandler {
    Connection conn;
    Properties pro = new Properties();
    @Override
    public void connect() {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("conn");
            //DriverManager.setLoginTimeout(1<<31);

             pro.put("user", "postgres");
             pro.put("password", "morszczukgora");
             pro.put("autoReconnect", "true");
            conn=DriverManager.getConnection("jdbc:postgresql://40.85.112.201:5432/database", pro);


            System.out.println("successfully connected");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean chroniona(String ryba) {
        int rybaId=getIdFish(ryba);
        String sql = "select projektid.ochrona_ryby("+rybaId+","+"extract(month from now())::int)";
        try {
            ResultSet rs = getRS(sql);
            rs.next();
            return rs.getBoolean(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public void addPolow(Wedkarz wedk, Polow xd, Turniej turn) throws NoProperRod {
        String sql = "insert into projektid.polowy values("+wedk.getKarta()+",now(),"+(turn.isDummy()? null:turn.getId())+","+getIdFish(xd.getRyba())+
                ','+xd.getWaga()+','+getIdZbiornik(xd.getGdzie())+')';
        System.out.println(sql);
        try {
            makeUpdate(sql);
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new NoProperRod(e);
        }
    }

    @Override
    public Integer getIdZbiornik(String miejsce) {
        String sql = "select * from projektid.zbiorniki_wodne zw where zw.nazwa='"+miejsce+"'";
        try {
            ResultSet rs = getRS(sql);
            rs.next();
            return rs.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    void makeUpdate(String sql) throws Exception {
        Statement st;
        try {
            st=conn.createStatement();
            st.executeUpdate(sql);
        } catch (Exception throwables) {
            conn.close();
            connect();
            st=conn.createStatement();
            st.executeUpdate(sql);
            //System.out.println(st.getWarnings().getNextWarning());
        }
    }
    ResultSet getRS(String sql) throws SQLException {
        Statement st;
        ResultSet rs;
        try {
            //conn = DriverManager.getConnection("jdbc:postgresql://40.85.112.201:5432/database", pro);
            st=conn.createStatement();
            rs=st.executeQuery(sql);
        } catch (Exception throwables) {
            conn.close();
            connect();
            st=conn.createStatement();
            rs=st.executeQuery(sql);
        }
        return rs;
    }
    @Override
    public ArrayList<String> getFish() {
        ArrayList<String> ret = new ArrayList<>();
        String sql = "select * from projektid.ryby";
        try {
            ResultSet rs=getRS(sql);

            while (rs.next()) {
                ret.add(rs.getString("nazwa"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    @Override
    public ArrayList<String> getFish(String miejsce) {
        ArrayList<String>res=new ArrayList<>();
        String sql = "select r.nazwa from projektid.ryby r join projektid.wystepowanie_ryb wr on wr.ryba=r.id_ryby "+
                "join projektid.zbiorniki_wodne  zw on zw.id_zbiornika=wr.zbiornik where zw.nazwa='"+miejsce+"'";
        getStrings(res, sql);
        return res;
    }

    @Override
    public Integer getIdFish(String fish){
        Integer ret=null;
        String sql = "select * from projektid.ryby where nazwa="+"'"+fish+"'";
        try {
            ResultSet rs=getRS(sql);
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
            ResultSet rs=getRS(sql);

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
            ResultSet rs=getRS(sql);

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
            ResultSet rs=getRS(sql);

            while (rs.next()) {
                arr.add(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }



    @Override
    public ArrayList<String> getZbiorniki(String okrag) {
        ArrayList<String> arr = new ArrayList<>();
        String sql = "select nazwa from projektid.zbiorniki_wodne where okrąg="+"'"+okrag+"'";
        try {
            ResultSet rs=getRS(sql);
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
            ResultSet rs=getRS(sql);

            while (rs.next()) {
                arr.add( miesiac(rs.getInt(1)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(String a : arr) a=a+"-";
        sql="select do_msc  from projektid.okresy_ochronne where id_ryby="+id;
        try {
            ResultSet rs=getRS(sql);

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
            ResultSet rs=getRS(sql);

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
            ResultSet rs=getRS(sql);

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
            ResultSet rs=getRS(sql);

            rs.next();
            boolean res = rs.getBoolean(1);
            System.out.println(res);
            return res;
        }
        catch (Exception e) {
            e.printStackTrace();
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
    public void addWedka(int wedkarz, Wedka wedka) throws RodAlrThere {
        String sql = "insert into projektid.ekwipunek values("+wedkarz+",'"+wedka.getRodzaj()+"','"+wedka.getMaterial()+"')";
        try {
            makeUpdate(sql);
        }
        catch (Exception e) {
            throw new RodAlrThere();
        }
    }

    @Override
    public void addPrice(String fish, Float money) throws CenaDwaRazy {
        int id=getIdFish(fish);
        String sql = "insert into projektid.historia_cen_ryb values("+id+","+money+","+"current_date)";
        try {
            makeUpdate(sql);
        }
        catch (Exception e) {
            throw new CenaDwaRazy();
        }
    }

    @Override
    public ArrayList<Polow> getPolowy(int wedk, Turniej turniej) {
        String sql="select p.data_polowu, zw.nazwa, r.nazwa, p.waga,p.waga*get_cena(p.ryba, p.data_polowu::date)::numeric::float8 " +
                "from projektid.polowy p join projektid.zbiorniki_wodne zw on zw.id_zbiornika=p.id_zbiornika " +
                "join projektid.ryby r on p.ryba=r.id_ryby where p.wędkarz="+wedk;
        if(!turniej.isDummy()) {
            sql+=" and id_turnieju="+turniej.getId();
        }
        sql += " order by 1 desc";
        ArrayList<Polow> arr = new ArrayList<>();
        try {
            ResultSet rs=getRS(sql);

            while(rs.next()) {
                Timestamp a = rs.getTimestamp("data_polowu");
                String timeStamp = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss").format(a);
                arr.add(new Polow(timeStamp, rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getFloat(5)));
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
                "from projektid.turnieje t join projektid.zbiorniki_wodne zw on zw.id_zbiornika=t.miejsce order by 2 desc";

        ArrayList<Turniej> arr = new ArrayList<>();
        try {
            ResultSet rs=getRS(sql);

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
    public ArrayList<Turniej> getAktTurnieje() {
        String sql="select t.id_turnieju, t.data_turnieju, t.rodzaj_konkurencji, zw.nazwa "+
                "from projektid.turnieje t join projektid.zbiorniki_wodne zw on zw.id_zbiornika=t.miejsce where t.data_turnieju=now()::date";

        ArrayList<Turniej> arr = new ArrayList<>();
        try {
            ResultSet rs=getRS(sql);

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
    public ArrayList<String> getOkregi() {
        ArrayList<String> arr=new ArrayList<>();
        String sql = "select unnest(enum_range(NULL::okrag))";
        arr= getStrings(arr, sql);
        arr.forEach(String::toLowerCase);
        return arr;
    }
    @Override
    public Float getActualPrice(String fish) {
        Float toReturn=null;
        Integer id=getIdFish(fish);
        String sql="select cena from projektid.historia_cen_ryb where id_ryby="+id+"order by data_wprowadzenia DESC limit 1";
        try {
            ResultSet rs=getRS(sql);
            rs.next();
            toReturn=rs.getFloat(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return toReturn;
    }

    private ArrayList<String> getStrings(ArrayList<String> arr, String sql) {
        try {
            ResultSet rs=getRS(sql);
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
