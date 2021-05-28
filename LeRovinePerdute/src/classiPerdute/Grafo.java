package classiPerdute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Grafo {
	private final static double EPSILON = 1E-14;//costante per confronto di numeri in virgola mobile
	
	private String nome;//nome del team
	private int numero_nodi;
	private ArrayList<Citta> nodi=new ArrayList<Citta>();
	private double matrice_adiacenza[][];
	
	private ArrayList<Integer> percorso_minimo=null;//arrayList che contiene gli indici delle città che compongono il percorso minimo
	private double costo_tot = Double.MAX_VALUE;
	
	private int id_arrivo_percorso;
	private int id_partenza_percorso;
	

	public Grafo(String _nome, ArrayList<Citta> _nodi) {
		this.nome=_nome;
		this.nodi=_nodi;
		this.numero_nodi=nodi.size();
		matrice_adiacenza= new double[numero_nodi][numero_nodi];
		
		id_partenza_percorso=nodi.get(0).getId();
		id_arrivo_percorso=nodi.get(numero_nodi-1).getId();
	}
	
	/**
	 * @return costo_tot costo totale di carburate speso per percorrere il percorso minimo
	 */
	public double getCosto_tot() {
		return costo_tot;
	}
	
	/**
	 * @return percorso_minimo ArrayList contenente gli indici delle citta che compongono il percorso minimo
	 */
	public ArrayList<Integer> getPercorso_minimo() {
		return percorso_minimo;
	}
	
	/**
	 * dato l'indice passato come argomento restituisce la citta associata ad esso
	 * @param id indice della citta
	 * @return nomeCitta nome della citta associata all'id
	 */
	public String getNameByIndex (int id) {
		String nomeCitta = nodi.get(id).getNome();
		
		return nomeCitta;
	}

	/**
	 * restituisce il contenuto dell'arrayList alla posizione passata come argomento
	 * @param i indice dell'arrayList
	 * @return contenuto dell'arrayList alla posizione passata come argomento
	 */
	public int getId (int i) {
		return percorso_minimo.get(i);
		
	}
 
	/**
	 * costruzione della matrice di adiacenza, che si riferisce al costo di carburante speso in base al tipo di veicolo
	 */
	public void calcolaGrafoPesato() {
		
		Citta citta_partenza;
		Citta citta_arrivo;
		
		int indice_citta_partenza;
		int indice_citta_link;
		
		double peso=0;
		
		for(int i=0; i<numero_nodi; i++){
			citta_partenza=nodi.get(i);
			indice_citta_partenza= citta_partenza.getId();
			
			matrice_adiacenza[i][i]=0;
			
			for(int j=0; j<citta_partenza.getNum_links(); j++) {
				indice_citta_link = citta_partenza.getLinkByIndex(j);
				citta_arrivo = nodi.get(indice_citta_link);
				
				switch(this.nome) {
				case "Tonatiuh"://caso veicolo Tonatiuh (dio sole)
					peso = citta_partenza.calcoloPesoT(citta_arrivo); 
					break;
				case "Metztli"://caso veicolo Metztli (dea luna)
					peso = citta_partenza.calcoloPesoM(citta_arrivo);
					break;
				}
				
				matrice_adiacenza[indice_citta_partenza][indice_citta_link] = peso;
			}			
		}		
	}

	/**
	 * dato l'identificativo di una citta passato come argomento, restituisce se esso e' gia' presente all'interno dell'arrayList di indici
	 * @param percorso_parziale arrayList contentente gli indici delle citta che compongono il percorso minimo
	 * @param id identificativo della citta di cui si vuole verificare la presenza all'interno dell'array
	 * @return true se presente, false altrimenti
	 */
	public boolean giaVisitata(ArrayList<Integer> percorso_parziale, int id) {
		
		for (int i=0; i<percorso_parziale.size(); i++) {
			if(percorso_parziale.get(i)==id) {
				return true;
			}
		}		
		return false;
	}
	
	/**
	 *trova il percoro minimo
	 */
	public void trovaPercorsoMinimo() {
		ArrayList<Integer> percorso_parziale= new ArrayList<>();
		Citta partenza = nodi.get(id_partenza_percorso);
		int num_link_visitato_di_partenza= partenza.getNum_links_visitati();
		boolean fine_ricerca=false;		
		
		Citta nuova_partenza = null;
		int id_nuova_partenza = 0;
		boolean trovato_arrivo=false;
		
		double costo_parziale = 0;
		
		
		while (!fine_ricerca) {
			trovato_arrivo=false;
			////costo_parziale = calcolaCosto(percorso_parziale);
			num_link_visitato_di_partenza= partenza.getNum_links_visitati();
			if(partenza.getId()!=id_arrivo_percorso && partenza.getId()!=id_partenza_percorso) {	//non iserisce due volte la stessa citta nel percorso
				if(costo_parziale <= costo_tot) {
					
					if(percorso_parziale.get(percorso_parziale.size()-1) != partenza.getId()) {
						percorso_parziale.add(partenza.getId());
						costo_parziale = costo_parziale + matrice_adiacenza[percorso_parziale.get(percorso_parziale.size() - 2)][percorso_parziale.get(percorso_parziale.size() - 1)];
					}
					
					while(!trovato_arrivo && num_link_visitato_di_partenza < partenza.getNum_links()){	//prossima citta da visitare
						id_nuova_partenza=partenza.getLinkByIndex(num_link_visitato_di_partenza);
						
						partenza.aggiornaNum_links_visitati();
						num_link_visitato_di_partenza = partenza.getNum_links_visitati();
						
						if(!giaVisitata(percorso_parziale, id_nuova_partenza)) {
							trovato_arrivo=true;
						}				
					}
					
					if(trovato_arrivo) {
						nuova_partenza=nodi.get(id_nuova_partenza);
						//costo_parziale = costo_parziale + matrice_adiacenza[partenza.getId()][nuova_partenza.getId()];
						//costo_parziale = calcolaCosto(percorso_parziale);
					}else {
						//costo_parziale = costo_parziale - matrice_adiacenza[percorso_parziale.get(percorso_parziale.size() - 2)][percorso_parziale.get(percorso_parziale.size() - 1)];
						percorso_parziale.remove(percorso_parziale.size()-1);	//se non e' stata trovata una nuova citta da visitare torna al passo precedente
						costo_parziale = calcolaCosto(percorso_parziale);
						partenza.resetNum_links_visitati();
						id_nuova_partenza=percorso_parziale.get(percorso_parziale.size()-1);
						nuova_partenza=nodi.get(id_nuova_partenza);				
					}
				} else {
					if(percorso_parziale.size()>=2) {
						//costo_parziale = costo_parziale - matrice_adiacenza[percorso_parziale.get(percorso_parziale.size() - 2)][percorso_parziale.get(percorso_parziale.size() - 1)];
						percorso_parziale.remove(percorso_parziale.size()-1);	//se non e' stata trovata una nuova citta da visitare torna al passo precedente
						costo_parziale = calcolaCosto(percorso_parziale);
						partenza.resetNum_links_visitati();
						id_nuova_partenza=percorso_parziale.get(percorso_parziale.size()-1);
						nuova_partenza=nodi.get(id_nuova_partenza);		
					}
				}
			}else if(partenza.getId()==id_arrivo_percorso) {	//scelta percorso minimo
				percorso_parziale.add(partenza.getId());
				costo_parziale = costo_parziale + matrice_adiacenza[percorso_parziale.get(percorso_parziale.size() - 2)][percorso_parziale.get(percorso_parziale.size() - 1)];
				if(Objects.isNull(percorso_minimo)){
					percorso_minimo = new ArrayList<>();
					setPercorso_minimo(percorso_parziale);
					costo_tot = costo_parziale;
					
				}else {
									
					if(costo_parziale < costo_tot) {
						setPercorso_minimo(percorso_parziale);
						costo_tot=costo_parziale;
						
					}else if(Math.abs(costo_parziale-costo_tot) <= EPSILON) {
						
						if(percorso_parziale.size() < percorso_minimo.size()) {
							setPercorso_minimo(percorso_parziale);
							costo_tot=costo_parziale;
							
						}else if(percorso_parziale.size() == percorso_minimo.size()) {
							if(isIdMaggiorePercorso(percorso_parziale, percorso_minimo)) {
								setPercorso_minimo(percorso_parziale);
								costo_tot=costo_parziale;
							}
						}
					}
				}
				
				//costo_parziale = costo_parziale - matrice_adiacenza[percorso_parziale.get(percorso_parziale.size() - 2)][percorso_parziale.get(percorso_parziale.size() - 1)];
				percorso_parziale.remove(percorso_parziale.size()-1);
				costo_parziale = calcolaCosto(percorso_parziale);
				//partenza.resetNum_links_visitati();
				id_nuova_partenza=percorso_parziale.get(percorso_parziale.size()-1);
				nuova_partenza=nodi.get(id_nuova_partenza);	
			}else if(partenza.getId() == id_partenza_percorso) {
				if(percorso_parziale.isEmpty()) {
					percorso_parziale.add(partenza.getId());
				}
				costo_parziale = calcolaCosto(percorso_parziale);
				if(num_link_visitato_di_partenza < partenza.getNum_links()) {
					//costo_parziale = 0;
					id_nuova_partenza = partenza.getLinkByIndex(num_link_visitato_di_partenza);
					nuova_partenza = nodi.get(id_nuova_partenza);
					partenza.aggiornaNum_links_visitati();
					//costo_parziale = costo_parziale + matrice_adiacenza[partenza.getId()][nuova_partenza.getId()];
					//costo_parziale = calcolaCosto(percorso_parziale);
				}else {
					fine_ricerca=true;
				}
			}
			partenza=nuova_partenza;
		}
	}
	

	/**
	 * Dati due percorsi che sono caratterizzati dallo stesso costo di carburante e lo stesso numero di citta attraversate, determina il percorso che 
	 * attraversa la citta con identificativo maggiore
	 * @param percorso_parziale 
	 * @param percorso_minimo
	 * @return true se percorso parziale contiene la citta con id maggiore, false altrimenti
	 */
	public boolean isIdMaggiorePercorso(ArrayList<Integer> percorso_parziale, ArrayList<Integer> percorso_minimo){
		//riordino array e faccio altro
		Integer[] array_percorso_parziale= percorso_parziale.toArray(new Integer[percorso_parziale.size()]);
		Integer[] array_percorso_minimo= percorso_minimo.toArray(new Integer[percorso_minimo.size()]);
		
		Arrays.sort(array_percorso_minimo); //ordinamento dell'array in ordine crescente
		Arrays.sort(array_percorso_parziale);
		
		for(int i=array_percorso_minimo.length-1; i>=0; i--) {
			if(array_percorso_minimo[i]-array_percorso_parziale[i] < 0) {
				return true;
			}else if(array_percorso_minimo[i]-array_percorso_parziale[i] > 0) return false;
		}		
		return false;
	}
	
	/**
	 * dato il percorso passato come argomento, restituisce il costo di carburante da spendere
	 * @param percorso 
	 * @return costo del carburate per poter attaraversare le citta del percorso
	 */
	public double calcolaCosto(ArrayList<Integer> percorso) {
		double costo = 0;
		int j=0;
		
		for(int i=0; j<percorso.size()-1; i++) {
			j=i+1;
			costo= costo + matrice_adiacenza[percorso.get(i)][percorso.get(j)];
		}
		
		return costo;
	}	
	
	/**
	 * associa al percorso minimo il percoso passato come argomento
	 * @param percorso_minimo_scelto
	 */
	public void setPercorso_minimo(ArrayList<Integer> percorso_minimo_scelto){
		percorso_minimo.clear();
		for(int i=0; i<percorso_minimo_scelto.size(); i++) {
			percorso_minimo.add(percorso_minimo_scelto.get(i));
		}
	}

	
	
	
}
