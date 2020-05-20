package it.polito.tdp.crimes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import it.polito.tdp.crimes.model.Event;


public class EventsDao {
	
	public void AllEvents(Map<Long,Event>idMap){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
				ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				if(!idMap.containsKey(res.getLong("incident_id"))) {
					idMap.put((res.getLong("incident_id")),new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				}
			}
			conn.close();
			

		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}

	public List<String>getReatiPerCategoria(String category, Month month){
		
		String sql="SELECT offense_type_id FROM EVENTS WHERE offense_category_id=? AND "
				+"MONTH(reported_date)=?";
		List<Event>result=new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setString(1, category);
			st.setInt(2, month.getValue());
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				
					result.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	public List<String>eventsCategories(){
		final String sql="SELECT distinct offense_category_id FROM EVENTS ";
		List<String>result= new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			ResultSet res = st.executeQuery() ;
		while(res.next()) {
		result.add(new String(res.getString(1)));	
		
		}
		conn.close();
		return result;
		
	} catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
	}
	
}
