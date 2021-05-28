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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}
	
	public int getNum_links_visitati() {
		return num_links_visitati;
	}

	public void aggiornaNum_links_visitati() {
		this.num_links_visitati++;
	}
	
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
	
	
	
	public int getNum_links() {
		return num_links;
	}
	
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
