package database;


import entities.*;
import exceptions.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;

public class DatabaseHandlerImplementation implements DatabaseHandler {
    Connection conn;
    Properties pro = new Properties();
    @Override
    public void connect() {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("conn");
            DriverManager.setLoginTimeout(1<<31);
            pro.put("user", "postgres");
            pro.put("password", "morszczukgora");
            //pro.put("password", "123456789");
            pro.put("autoReconnect", "true");
            conn=DriverManager.getConnection("jdbc:postgresql://40.85.112.201:5432/database", pro);
            //conn=DriverManager.getConnection("jdbc:postgresql://13.79.145.88:5432/ryby", pro);

            System.out.println("successfully connected");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateWedkarz(int karta, String imie, String nazwisko) {
        String sql="update projektid.wędkarze set imie='"+imie+"',"+"nazwisko='"+nazwisko+"' where karta_rybacka="+karta;

        try {
            makeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void updateStarosta(Integer id, boolean coRobic) {
        String sql=null;
        if(coRobic)
        sql="update projektid.starości set aktualny=true where id_starosty="+id;
        else  sql="update projektid.starości set aktualny=false where id_starosty="+id;
        try {
            makeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void insertWedkarz(Wedkarz ne, int starosta) throws IdIstnieje, ZaMlody {
        String sql1="select exists(select * from projektid.wędkarze where karta_rybacka="+ne.getKarta()+")";
        try {
            ResultSet rs = getRS(sql1);
            rs.next();
            if(rs.getBoolean(1)) throw new SQLException();
        } catch (SQLException throwables) {
            throw new IdIstnieje();
        }
        sql1="insert into projektid.wędkarze values("+ne.getKarta()+",'"+ne.getImie()+"','"+ne.getNazwisko()+"','"+ne.getDataUrodzenia()+"',now()::date,"+starosta+")";
        //System.out.println(sql1);
        try {
            makeUpdate(sql1);
        } catch (Exception e) {
            throw new ZaMlody();
        }
    }

    @Override
    public void addZbiornik(Okreg okreg, String name, Float pow) throws OkragZbiornik {
        String sql = "insert into projektid.zbiorniki_wodne(nazwa, powierzchnia, okręg) values" +
        "('"+name+"',"+pow+",'"+okreg.toString()+"')";
        try {
            makeUpdate(sql);
        } catch (Exception e) {
            throw new OkragZbiornik();
        }
    }
    @Override
    public void addStarosta(String imie, String nazwisko, String miasto, String text3) throws StarostaError {
        String sql=null;

        if(text3.toLowerCase().equals("tak"))
            sql = "insert into projektid.starości(imie, nazwisko, miasto, aktualny) values" +
                "('"+imie+"','"+nazwisko+"','"+miasto+"',"+"'true'"+")";
        else if(text3.toLowerCase().equals("nie"))
            sql = "insert into projektid.starości(imie, nazwisko, miasto, aktualny) values" +
                    "('"+imie+"','"+nazwisko+"','"+miasto+"',"+"'false'"+")";

        System.out.println(sql);
        if(sql!=null){
            try {
                makeUpdate(sql);
            } catch (Exception e) {
                throw new StarostaError();
            }
        }
    }
    @Override
    public void updateZbiornik(Okreg okreg, String old, String neww) throws OkragZbiornik {
        String sql = "update projektid.zbiorniki_wodne set nazwa='"+neww+"' where nazwa='"+old+"' and okręg='"+okreg.toString()+"'";
        try {
            makeUpdate(sql);
        } catch (Exception e) {
            throw new OkragZbiornik();
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
        try {
            makeUpdate(sql);
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new NoProperRod(e);
        }
    }

    @Override
    public void addOkres(String fish,String text) throws ZlyOkres {
        int id=getIdFish(fish);
        boolean where=true;
        String a="";
        String b="";
        for(char k : text.toCharArray()){
            if(k!='-' && where) a+=k;
            else where=false;
            if(k!='-' && !where) b+=k;
        }
        String sql = "insert into projektid.okresy_ochronne values("+id+","+a+","+b+")";
        try {
            makeUpdate(sql);
        }
        catch (Exception e) {
           e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Starosta> getStarosciAkt() {
        ArrayList<Starosta> arr = new ArrayList<>();
        String sql = "select * from projektid.starosci_online()";
        try {
            ResultSet rs = getRS(sql);
            while(rs.next()) {
                arr.add(new Starosta(rs.getInt(1), rs.getString("imie"), rs.getString("nazwisko"), rs.getString("miasto")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return arr;
    }
    @Override
    public ArrayList<Starosta> getStarosci() {
        ArrayList<Starosta> arr = new ArrayList<>();
        String sql = "select * from projektid.starości order by aktualny desc" ;
        System.out.println(sql);
        try {
            ResultSet rs = getRS(sql);
            while(rs.next()) {
                arr.add(new Starosta(rs.getInt(1), rs.getString("imie"), rs.getString("nazwisko"), rs.getString("miasto"), rs.getBoolean("aktualny")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return arr;
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
                arr.add(new Wedkarz(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getDate(5)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arr;
    }

    @Override
    public String getOkreg(String zbiornik) {
        String sql = "select ";
        return null;
    }

    @Override
    public ArrayList<String> getZbiorniki() {
        ArrayList<String> arr = new ArrayList<>();
        String sql = "select nazwa from projektid.zbiorniki_wodne order by 1";
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
        String sql = "select nazwa from projektid.zbiorniki_wodne where okręg="+"'"+okrag+"'";
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
        for(int i=0;i<arr.size();i++){
            arr.set(i,arr.get(i) + '-');
        }
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
        //for(String a: arr)
        //   System.out.println(a);
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
            return new Wedkarz(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getDate(5));
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
            //System.out.println(res);
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
    public void addTurniej(String miejsce, String rodzaj) throws TurniejIstnieje {
        int id=getIdZbiornik(miejsce);
        String sql = "insert into projektid.turnieje(data_turnieju, rodzaj_konkurencji, miejsce) values(current_date,"+"'"+rodzaj+"'"+","+id+")";
        //System.out.println(sql);
        try {
            makeUpdate(sql);
        }
        catch (Exception e) {
            throw new TurniejIstnieje();
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
    public void addWystepowanie(String fish, String zbiornik) throws ParaRybaZbiornikJest {
        int idRyb=getIdFish(fish);
        int idZb=getIdZbiornik(zbiornik);
        String sql = "insert into projektid.wystepowanie_ryb values("+idZb+","+idRyb+")";
        try {
            makeUpdate(sql);
        }
        catch (Exception e) {
            throw new ParaRybaZbiornikJest();
        }
    }

    @Override
    public ArrayList<Polow> getPolowy(int wedk, Turniej turniej) {
        String sql="select * from projektid.get_polowy where wędkarz="+wedk;
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
                arr.add(new Polow(timeStamp, rs.getString(4), rs.getString(5), rs.getFloat(6), rs.getFloat(7)));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    @Override
    public ArrayList<Turniej> getTurnieje() {
        String sql="select * from get_turnieje order by 2 desc";

        ArrayList<Turniej> arr = new ArrayList<>();
        try {
            ResultSet rs=getRS(sql);

            while(rs.next()) {
                arr.add(new Turniej(rs.getInt(1), rs.getString(4), rs.getDate(2), rs.getString(3), rs.getString(5)));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }
    @Override
    public ArrayList<rankingREC> getRanking() {
        String sql="select * from get_Ranking order by 3 desc";

        ArrayList<rankingREC> arr = new ArrayList<>();
        try {
            ResultSet rs=getRS(sql);

            while(rs.next()) {
                arr.add(new rankingREC(rs.getString(1),rs.getString(2),rs.getFloat(3)));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }
    @Override
    public ArrayList<rankingREC> getFilteredRanking(Turniej turniej) {
        String sql="select w.imie, w.nazwisko, sum(p.waga*get_cena(p.ryba, p.data_polowu::date)::numeric::float8) as \"kasa\" from projektid.wędkarze w join projektid.polowy p on p.wędkarz=\n" +
                "w.karta_rybacka group by w.karta_rybacka, p.id_turnieju having p.id_turnieju="+turniej.getId()+" order by 3";
        //System.out.println(sql);
        ArrayList<rankingREC> arr = new ArrayList<>();
        try {
            ResultSet rs=getRS(sql);

            while(rs.next()) {
                arr.add(new rankingREC(rs.getString(1),rs.getString(2),rs.getFloat(3)));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }



    @Override
    public ArrayList<Turniej> getAktTurnieje() {
        String sql="select * from get_turnieje where data_turnieju=now()::date";

        ArrayList<Turniej> arr = new ArrayList<>();
        try {
            ResultSet rs=getRS(sql);

            while(rs.next()) {
                arr.add(new Turniej(rs.getInt(1), rs.getString(4), rs.getDate(2), rs.getString(3), rs.getString(5)));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    @Override
    public ArrayList<Turniej> getFilterTurnieje(String miejsce, String date) {
        String sql;
        if(miejsce==null && date==null)
            sql="select * from get_turnieje";
        else if(miejsce==null){
            sql="select * from get_turnieje where data_turnieju>"+"'"+date+"'";
        }else if(date==null || date.equals("")){
            sql="select * from get_turnieje where nazwa="+"'"+miejsce+"'";
        } else sql="select * from get_turnieje where data_turnieju>"+"'"+date+"'"+" and nazwa="+"'"+miejsce+"'";

        //System.out.println(sql);
        ArrayList<Turniej> arr = new ArrayList<>();
        try {
            ResultSet rs=getRS(sql);

            while(rs.next()) {
                arr.add(new Turniej(rs.getInt(1), rs.getString(4), rs.getDate(2), rs.getString(3), rs.getString(5)));
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
        String sql = "select unnest(enum_range(NULL::okreg))";
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
