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
	
	/**
	 * @param _nome nome del grafo
	 * @param _nodi citta della mappa
	 */
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
	 *l'algoritmo passa da una citta' alle sue citta' collegate se esse non sono gia' state passate in precedenza
	 *se il costo del percorso che sta percorrendo e' maggiore del percorso minimo trovato all'ora abbandona il percorso intrappreso tornando alla citta' appenna precedente
	 *
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
			num_link_visitato_di_partenza= partenza.getNum_links_visitati();
			if(partenza.getId()!=id_arrivo_percorso && partenza.getId()!=id_partenza_percorso) {		//entra se si e' in una citta che non sia l'arrivo o la partenza
				if(costo_parziale <= costo_tot) {	//se il costo del percorso parziale risulta gia' maggiore del percorso minimo trovato si ritorna alla citta precedente
					
					if(percorso_parziale.get(percorso_parziale.size()-1) != partenza.getId()) { //controllo per non iserire due volte la stessa citta nel percorso
						percorso_parziale.add(partenza.getId());
					}
					
					while(!trovato_arrivo && num_link_visitato_di_partenza < partenza.getNum_links()){	//trovo prossima citta da visitare
						id_nuova_partenza=partenza.getLinkByIndex(num_link_visitato_di_partenza);
						
						partenza.aggiornaNum_links_visitati();
						num_link_visitato_di_partenza = partenza.getNum_links_visitati();
						
						if(!giaVisitata(percorso_parziale, id_nuova_partenza)) {	//controllo che la citta collegata non sia gia' all'interno nel percorso parziale
							trovato_arrivo=true;
						}				
					}
					
					if(trovato_arrivo) {	//se ho trovato la prossima citta in cui andare aggiorno la partenza con la nuova destinazione
						nuova_partenza=nodi.get(id_nuova_partenza);
						costo_parziale = costo_parziale + matrice_adiacenza[partenza.getId()][nuova_partenza.getId()];
					}else {		//altrimeni torno alla citta precedente
						percorso_parziale.remove(percorso_parziale.size()-1);	
						costo_parziale = calcolaCosto(percorso_parziale);
						partenza.resetNum_links_visitati();
						id_nuova_partenza=percorso_parziale.get(percorso_parziale.size()-1);
						nuova_partenza=nodi.get(id_nuova_partenza);				
					}
				} else {
					if(percorso_parziale.size()>=2) {		//se il costo del percorso intrappreso e' maggiore del percorso minimo gia' trovato torno alla citta precedente
						costo_parziale = calcolaCosto(percorso_parziale);
						id_nuova_partenza=percorso_parziale.get(percorso_parziale.size()-1);
						nuova_partenza=nodi.get(id_nuova_partenza);		
					}
				}
			}else if(partenza.getId()==id_arrivo_percorso) {	//entra se la partenza corrisponde all'arrivo del percorso
				percorso_parziale.add(partenza.getId());
				
				if(Objects.isNull(percorso_minimo)){		//se non esiste ancora un percorso minimo setto il primo percorso calcolato
					percorso_minimo = new ArrayList<>();
					setPercorso_minimo(percorso_parziale);
					costo_tot = calcolaCosto(percorso_minimo);
					
				}else {		//se esiste un percorso minimo allora...
									
					if(costo_parziale < costo_tot) {	//controllo quale sia il costo minore
						setPercorso_minimo(percorso_parziale);
						costo_tot=costo_parziale;		//se il costo del nuovo percorso calcolato e' minore lo setto come percorso minimo
						
					}else if(Math.abs(costo_parziale-costo_tot) <= EPSILON) {	//se hanno stesso costo
						
						if(percorso_parziale.size() < percorso_minimo.size()) {		//controllo quello che fa il minor numero di passi
							setPercorso_minimo(percorso_parziale);
							costo_tot=costo_parziale;			//prendo quello che attraversa il minor numero di citta
							
						}else if(percorso_parziale.size() == percorso_minimo.size()) {		//se fanno lo stesso numero di passi
							if(isIdMaggiorePercorso(percorso_parziale, percorso_minimo)) {
								setPercorso_minimo(percorso_parziale);
								costo_tot=costo_parziale;				//prendo quello che attraversa la citta con id maggiore
							}
						}
					}
				}
				
				//arrivato all'arrivo del percorso da calcolare torno alla citta precedente
				percorso_parziale.remove(percorso_parziale.size()-1);
				costo_parziale = calcolaCosto(percorso_parziale);
				id_nuova_partenza=percorso_parziale.get(percorso_parziale.size()-1);
				nuova_partenza=nodi.get(id_nuova_partenza);	
				
			}else if(partenza.getId() == id_partenza_percorso) {	//se si trova nella citta di partenza
				if(percorso_parziale.isEmpty()) {	//se il percorso parziale e' vuoto inserisco la ctta di partenza
					percorso_parziale.add(partenza.getId());
				}
				if(num_link_visitato_di_partenza < partenza.getNum_links()){	//controllo che la citta di partenza ha altre citta collegate in cui poter andare
					costo_parziale = 0;
					id_nuova_partenza = partenza.getLinkByIndex(num_link_visitato_di_partenza);
					nuova_partenza = nodi.get(id_nuova_partenza);
					partenza.aggiornaNum_links_visitati();
					costo_parziale = costo_parziale + matrice_adiacenza[partenza.getId()][nuova_partenza.getId()];
				}else {
					fine_ricerca=true;		//se la citta di partenza non ha piu' citta da visitare all'ora la ricerca e' conclusa
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
