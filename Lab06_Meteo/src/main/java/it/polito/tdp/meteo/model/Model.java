package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 10;
	private final static int NUMERO_GIORNI_TOTALI = 25;
	Integer counter = 0;
	private MeteoDAO dao;
	private List<Giorno> rilevamenti;
	private List<Citta> citta;
	Integer j = 0;
	Integer bestCosto = null;

	List<Rilevamento> bestRilevamento = new ArrayList<Rilevamento>();
	
	public Model() {
		dao = new MeteoDAO();
		citta = dao.getAllCitta();
		
	}

	// of course you can change the String output with what you think works best
	public Map<Citta, Double> getUmiditaMedia(Integer mese) {
		
		Map<Citta, Double> umiditaMedia = new HashMap<Citta, Double>();
		
		for (Citta c : citta) {
			c.setRilevamenti(dao.getAllRilevamentiLocalitaMese(mese, c));
			
			Double media = 0.0;
			
			for (Rilevamento r : c.getRilevamenti())
				media += r.getUmidita();
			
			media = media / (c.getRilevamenti().size());
			
			umiditaMedia.put(c, Math.floor((media*100) / 100));
			
		}
		return umiditaMedia;

	
	}
	
	public Map<List<Rilevamento>, Integer> trovaSequenza(int mese) {
		rilevamenti = dao.getAllRilevamentiByMeseByDay(mese);
		List<Rilevamento> parziale = new ArrayList<Rilevamento>();
		
		cerca(parziale, 0);
		
		Map<List<Rilevamento>, Integer> finale = new HashMap<List<Rilevamento>, Integer>();
		finale.put(bestRilevamento, bestCosto);
		return finale;
	}
	
	private void cerca(List<Rilevamento> parziale, Integer livello) {		
		// Condizione terminale
		if (parziale.size() == NUMERO_GIORNI_TOTALI) {
			Integer costo = calcolaCosto(parziale);
			
			if (bestCosto == null || costo < bestCosto) {
				bestRilevamento = new ArrayList<Rilevamento>(parziale);
				bestCosto = costo;
			}
			
			return;
		}
		
		// Condizione intermedia
				
		for (Rilevamento r : rilevamenti.get(livello).getRilevamenti()) {
			if (aggiuntaValida(parziale, r))
			{
				parziale.add(r);
				cerca(parziale, livello+1);
				parziale.remove(r);

			}
		}
	}

	private boolean aggiuntaValida(List<Rilevamento> parziale, Rilevamento r) {
		// Gestione piu di sei casi
		
		List<Rilevamento> par = new ArrayList<Rilevamento>(parziale);
		Integer counter = 0;
		
		Citta attuale = r.getLocalita();
		
		for (Rilevamento ril : par)
			if(ril.getLocalita().equals(r.getLocalita()))
				counter++;
		
		if (counter >= NUMERO_GIORNI_CITTA_MAX)
			return false;
		
		//** -- Da MIGLIORARE
		// verifica giorni minimi
		if (parziale.size() == 0) // primo giorno
			return true;
		if (parziale.size() == 1 || parziale.size() == 2) { // secondo o terzo giorno: non posso cambiare
			return parziale.get(parziale.size() - 1).getLocalita().equals(attuale);
		}
		if (parziale.get(parziale.size() - 1).getLocalita().equals(attuale)) // giorni successivi, posso SEMPRE rimanere
			return true;
		// sto cambiando citta
		if (parziale.get(parziale.size() - 1).getLocalita().equals(parziale.get(parziale.size() - 2).getLocalita())
				&& parziale.get(parziale.size() - 2).getLocalita().equals(parziale.get(parziale.size() - 3).getLocalita()))
			return true;

		return false;
	}
	
	private Integer calcolaCosto(List<Rilevamento> parziale) {
		Integer costo = 0;

		for (Rilevamento r : parziale)
			costo += r.getUmidita();
		
		for (Integer j = 1; j<parziale.size(); j++) {
			if (!parziale.get(j).getLocalita().equals(parziale.get(j-1).getLocalita()))
				costo += COST;
		}
		
		return costo;
	}
}