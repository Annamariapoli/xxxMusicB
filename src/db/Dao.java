package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import bean.City;

public class Dao {

public List<City> getVentiCittaConPiuAscolti(int gg){
	String query="select  count(l.id) as num, c.id, c.city  "
			+ "from listening l , city c   "
			+ "where l.cityid=c.id and l.weekday=?  "
			+ "group by l.trackid  "
			+ "order by num DESC  "
			+ "limit 20";
	Connection conn= DBConnect.getConnection();
	List<City> all= new LinkedList<>();
	try{
		PreparedStatement st= conn.prepareStatement(query);
		st.setInt(1, gg);
		ResultSet res = st.executeQuery();
		while(res.next()){
			City c = new City(res.getInt("id"), res.getString("city"));
			all.add(c);
		}
		conn.close();
		//System.out.println(all);
		return all;
	}catch(SQLException e){
		e.printStackTrace();
		return null;
	}
 }

public List<String> getElenco(int gg){
	String query="select l.trackid, count(l.id) as num   "
			+ "from listening l , city c   "
			+ "where l.cityid=c.id and l.weekday=?   "
			+ "group by l.trackid   "
			+ "order by num DESC  "
			+ "limit 20";
	Connection conn= DBConnect.getConnection();
	List<String> all= new LinkedList<>();
	try{
		PreparedStatement st= conn.prepareStatement(query);
		st.setInt(1, gg);
		ResultSet res = st.executeQuery();
		while(res.next()){
			all.add("Brano:   "+ res.getInt("trackid")+"   NumeroAscolti: "+res.getInt("num")+"  \n");
		}
		conn.close();
		//System.out.println(all);
		return all;
	}catch(SQLException e){
		e.printStackTrace();
		return null;
	}
}


 
public int getNumeroCanzoniInDueCitta(int gg, int idC1, int idC2){
	int num=0;
	String query ="select count(l1.trackid) as num  "
			+ "from listening l1, listening l2 "
			+ "where l1.weekday=l1.weekday and l1.weekday=? "
			+ "and l1.trackid=l2.trackid "
			+ "and l1.cityid=? and l2.cityid=?";
			Connection conn= DBConnect.getConnection();
	try{
		PreparedStatement st= conn.prepareStatement(query);
		st.setInt(1, gg);
		st.setInt(2,  idC1);
		st.setInt(3, idC2);
		ResultSet res = st.executeQuery();
		if(res.next()){
			num = res.getInt("num");
		}
		conn.close();
		System.out.println(num);
		return num;
	}catch(SQLException e){
		e.printStackTrace();
		return -1;
	}
}


}
