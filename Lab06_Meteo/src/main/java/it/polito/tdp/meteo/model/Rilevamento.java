package it.polito.tdp.meteo.model;

import java.util.Date;

public class Rilevamento {
	
	private Citta localita;
	private Date data;
	private int umidita;

	public Rilevamento(Citta localita, Date data, int umidita) {
		this.localita = localita;
		this.data = data;
		this.umidita = umidita;
	}

	public Rilevamento(Rilevamento rilevamenti) {
		this.localita = rilevamenti.localita;
		this.data = rilevamenti.data;
		this.umidita = rilevamenti.umidita;
	}

	public Citta getLocalita() {
		return localita;
	}

	public void setLocalita(Citta localita) {
		this.localita = localita;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getUmidita() {
		return umidita;
	}

	public void setUmidita(int umidita) {
		this.umidita = umidita;
	}

	// @Override
	// public String toString() {
	// return localita + " " + data + " " + umidita;
	// }

	@Override
	public String toString() {
		return this.getLocalita() + " " + String.valueOf(umidita);
	}

	

}
