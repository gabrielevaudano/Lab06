package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.List;

public class Giorno {
	private List<Rilevamento> rilevamenti;
	private Integer giorno;
	
	public Giorno(List<Rilevamento> rilevamenti, Integer giorno) {
		super();
		this.rilevamenti = rilevamenti;
		this.giorno = giorno;
	}
	
	public Giorno(Integer giorno) {
		this.rilevamenti = new ArrayList<Rilevamento>();
		this.giorno = giorno;
	}

	public Giorno(Integer giorno, Rilevamento r) {
		this.giorno = giorno;
		this.rilevamenti = new ArrayList<Rilevamento>();
		this.setRilevamenti(r);
	}

	public List<Rilevamento> getRilevamenti() {
		return rilevamenti;
	}

	public void setRilevamenti(Rilevamento rilevamenti) {
		this.rilevamenti.add(rilevamenti);
	}

	public Integer getGiorno() {
		return giorno;
	}

	public void setGiorno(Integer giorno) {
		this.giorno = giorno;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((giorno == null) ? 0 : giorno.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Giorno other = (Giorno) obj;
		if (giorno == null) {
			if (other.giorno != null) {
				return false;
			}
		} else if (!giorno.equals(other.giorno)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Giorno [giorno=" + giorno + ", rilevamenti=" + rilevamenti + "]";
	}
	
	
	
}
