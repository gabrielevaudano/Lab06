package it.polito.tdp.meteo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.meteo.model.Citta;
import it.polito.tdp.meteo.model.Giorno;
import it.polito.tdp.meteo.model.Rilevamento;

public class MeteoDAO {
	
	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(new Citta(rs.getString("Localita")), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public List<Citta> getAllCitta() {

		final String sql = "SELECT Localita FROM situazione ";

		List<Citta> citta = new ArrayList<Citta>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Citta c = new Citta(rs.getString("Localita"));
				
				if (!citta.contains(c))
					citta.add(c);
			}

			conn.close();
			return citta;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	
	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, Citta c) {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione WHERE localita = ? "
				+ "AND MONTH(Data) = ? ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, c.getNome());
			st.setInt(2, mese);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(new Citta(rs.getString("Localita")), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Rilevamento> getAllRilevamentiMese() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Rilevamento> getAllRilevamentiByMese(int mese) {
		final String sql = "SELECT Localita, Data, Umidita FROM situazione WHERE MONTH(Data) = ? ORDER BY data ASC ";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, mese);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(new Citta(rs.getString("Localita")), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}	

	public List<Giorno> getAllRilevamentiByMeseByDay(int mese) {
		final String sql = "SELECT Localita, Data, DAY(Data) as 'Day', Umidita FROM situazione "
				+ "WHERE MONTH(Data) = ? ORDER BY data ASC ";

		List<Giorno> rilevamenti = new ArrayList<Giorno>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, mese);
			
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {		
				Rilevamento r = new Rilevamento(new Citta(rs.getString("Localita")), rs.getDate("Data"), rs.getInt("Umidita"));
				Giorno g = new Giorno(rs.getInt("Day"));
				
				if (rilevamenti.contains(g))
					rilevamenti.get(rilevamenti.indexOf(g)).setRilevamenti(r);
				else {
					rilevamenti.add(new Giorno(rs.getInt("Day"), r));
				}
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}	

}
