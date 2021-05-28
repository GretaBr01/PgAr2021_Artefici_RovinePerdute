package classiPerdute;

import java.util.ArrayList;

public class Citta {
	private int id;
	private String nome;
	private double x;
	private double y;
	private double h;
	
	private ArrayList<Integer> links = new ArrayList<>();
	private int num_links=0;
	private int num_links_visitati=0;

	/**
	 * @return stringa corrispondente all'id della persona
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * set id della persona
	 * @param string
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return stringa corrispondente al nome della persona
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * set nome della persona
	 * @param nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return coordinata x della citta
	 */
	public double getX() {
		return x;
	}

	/**
	 * set coordinata x della citta
	 * @param x
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	/**
	 * @return coordinata y della citta
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * set coordinata y della citta
	 * @param y
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * @return altezza sul livello del mare della citta
	 */
	public double getH() {
		return h;
	}

	/**
	 * set altezza sul livello del mare della citta
	 * @param h
	 */
	public void setH(double h) {
		this.h = h;
	}
	
	/**
	 * 
	 * @return numero dei collegamenti gia' controllati della relativa citta
	 */
	public int getNum_links_visitati() {
		return num_links_visitati;
	}

	/**
	 * aggiornamento del numero dei collegamenti gia' controllati della relativa citta
	 */
	public void aggiornaNum_links_visitati() {
		this.num_links_visitati++;
	}
	
	/**
	 * reimposto a 0 il numero dei collegamenti gia' controllati della relativa citta
	 */
	public void resetNum_links_visitati() {
		this.num_links_visitati=0;
	}

	/**
	 * aggiunge alla lista dei collegamenti della citta' l'id delle citta' a cui essa e' collegata
	 * @param linked_id_city id della citta'  collegata
	 */
	public void addLink (int linked_id_city) {
		links.add(linked_id_city);
		num_links++;
	}
	
	/**
	 * 
	 * @return il numero dei collegamenti della citta relativa
	 */
	public int getNum_links() {
		return num_links;
	}
	
	/**
	 * prendere l'id della citta a cui e' collegata sapento il rispettivo indice dell'ArrayList "links" 
	 * @param i indice a cui si vuole prendere il link
	 * @return l'id della citta a cui e' collegata
	 */
	public int getLinkByIndex(int i) {
		return links.get(i);
	}

	/**
	 * calcolo carburante speso per il veicolo Tonatiuh 
	 * @param citta_arrivo
	 * @return costo carburante mediante la distanza euclidea delle citta'
	 */
	public double calcoloPesoT (Citta citta_arrivo) {
		double peso;
		peso = Math.sqrt(Math.pow((citta_arrivo.getX() - this.x), 2) + Math.pow(citta_arrivo.getY() - this.y, 2));
		
		return peso;
	}
	
	/**
	 * calcolo carburante speso per il veicolo Metztil
	 * @param citta_arrivo
	 * @return costo carburante mediante il modulo della differenza dell'altitudine delle citta'
	 */
	public double calcoloPesoM (Citta citta_arrivo) {
		double peso;
		peso = Math.abs(citta_arrivo.getH() - this.h);
		
		return peso;
	}
	
	

}
