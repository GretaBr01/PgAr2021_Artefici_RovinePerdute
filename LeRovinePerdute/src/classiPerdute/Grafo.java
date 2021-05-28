package classiPerdute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Grafo {
	private final static double EPSILON = 1E-14;
	
	private String nome;
	private int numero_nodi;
	private ArrayList<Citta> nodi=new ArrayList<Citta>();
	private double matrice_adiacenza[][];
	
	private double[][] matrice_distanze= new double[numero_nodi][numero_nodi];
	
	private ArrayList<Integer> percorso_minimo=null;
	private double costo_tot;
	
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

	public ArrayList<Citta> getNodi() {
		return nodi;
	}
	
	public void annullaMatrice_adiacenza() {
		for(int i=0; i<numero_nodi; i++) {
			for(int j=0; j<numero_nodi; j++) {
				matrice_adiacenza[i][j] = -1;
			}
		}
	}
	
	public void calcolaGrafoPesato() {
		
		//annullaMatrice_adiacenza();
		
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
				case "Tonatiuh":
					peso = citta_partenza.calcoloPesoT(citta_arrivo);
					break;
				case "Metztli":
					peso = citta_partenza.calcoloPesoM(citta_arrivo);
					break;
				}
				
				matrice_adiacenza[indice_citta_partenza][indice_citta_link] = peso;
			}			
		}		
	}
	
	public void stampaMatrice() {
		for(int i=0; i<numero_nodi; i++) {
			for(int j=0; j<numero_nodi; j++) {
				System.out.printf("%f", matrice_adiacenza[i][j]);
			}
		}
	}
//*************************************************************************************************************************
	public int minDistance(ArrayList<Double> mindist, ArrayList<Boolean> visited){
	    double min=Double.MAX_VALUE;
	    int minindex=-1;
	    for (int i=0; i < mindist.size(); i++) {
	        if(!visited.get(i) && (mindist.get(i) <=min)) {
	            min=mindist.get(i);
	            minindex=i;
	        }
	    }
	    return minindex;
	}
	
	public double dijkstra(int start, int end) {
	    int computers=matrice_adiacenza.length;
	    ArrayList<Boolean> traversed=new ArrayList<>(); //Hold traversed nodes
	    for (int i=0; i < computers; i++) {
	        traversed.add(i,false);
	    }
	    ArrayList<Double> mindist=new ArrayList<>(); //Holds mindistances to nodes based on index
	    for (int i=0; i < computers; i++) {
	        mindist.add(i,Double.MAX_VALUE);
	    }
	    mindist.set(start,(double)0);

	    for (int i=0; i < computers; i++) {
	        int min=minDistance(mindist,traversed);
	        if(min==-1) return mindist.get(end);
	        traversed.set(min,true);
	        if(min==end) return mindist.get(min)==Double.MAX_VALUE ? -1 : mindist.get(min); //Error check
	        for (int j=0; j < computers; j++) {
	            if(!traversed.get(j) && matrice_adiacenza[min][j] !=0 && mindist.get(min) + matrice_adiacenza[min][j] < mindist.get(j)) {
		                mindist.set(j,(mindist.get(min) + matrice_adiacenza[min][j]));
		            }
	        }
	    }
	    stampaPercorso(traversed);
	    return mindist.get(end)==Double.MAX_VALUE ? -1 : mindist.get(end);
	}
	
	public void stampaPercorso(ArrayList<Boolean> visited) {
		for(int i=0; i<visited.size(); i++) {
			if(visited.get(i)) {
				System.out.println(i);
			}
		}
	}
//****************************************************************************************************************************	
	
	public void calcolaPercorsoMinimo() {
		ArrayList<Integer> percorso_parziale = new ArrayList<>();
		boolean per = ricorsione(nodi.get(0), percorso_parziale);
		System.out.println(per);
	}
	
	public boolean giaVisitata(ArrayList<Integer> percorso_parziale, int id) {
		
		for (int i=0; i<percorso_parziale.size(); i++) {
			if(percorso_parziale.get(i)==id) {
				return true;
			}
		}		
		return false;
	}
	
	public boolean ricorsione(Citta partenza, ArrayList<Integer> percorso_parziale) {
		int num_link_visitato_di_partenza = partenza.getNum_links_visitati();
		int id_nuova_partenza = 0;
		Citta nuova_Partenza;
		boolean trovato_arrivo=false;
		
		if(partenza.getId()!=id_arrivo_percorso && partenza.getId()!=id_partenza_percorso) {
			if(percorso_parziale.get(percorso_parziale.size()-1) != partenza.getId()) {
				percorso_parziale.add(partenza.getId());
			}
			
			while(!trovato_arrivo && num_link_visitato_di_partenza < partenza.getNum_links()){
				id_nuova_partenza=partenza.getLinkByIndex(num_link_visitato_di_partenza);
				
				partenza.aggiornaNum_links_visitati();
				num_link_visitato_di_partenza = partenza.getNum_links_visitati();
				
				if(!giaVisitata(percorso_parziale, id_nuova_partenza)) {
					trovato_arrivo=true;
				}				
			}
			
			if(trovato_arrivo) {
				nuova_Partenza=nodi.get(id_nuova_partenza);				
			}else {
				percorso_parziale.remove(percorso_parziale.size()-1);
				id_nuova_partenza=percorso_parziale.get(percorso_parziale.size()-1);
				nuova_Partenza=nodi.get(id_nuova_partenza);				
			}
				
			ricorsione(nuova_Partenza, percorso_parziale);
			
		}else if(partenza.getId()==id_arrivo_percorso) {	//scelta percorso minimo
			percorso_parziale.add(partenza.getId());
			
			if(Objects.isNull(percorso_minimo)){
				percorso_minimo = new ArrayList<>();
				setPercorso_minimo(percorso_parziale);
				costo_tot = calcolaCosto(percorso_minimo);
				
			}else {
				double costo_parziale=	calcolaCosto(percorso_parziale);				
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
			
			percorso_parziale.remove(percorso_parziale.size()-1);
			id_nuova_partenza=percorso_parziale.get(percorso_parziale.size()-1);
			nuova_Partenza=nodi.get(id_nuova_partenza);
			
			ricorsione(nuova_Partenza,percorso_parziale);
			
		}else if(partenza.getId() == id_partenza_percorso) {
			if(percorso_parziale.isEmpty()) {
				percorso_parziale.add(partenza.getId());
			}
			if(num_link_visitato_di_partenza < partenza.getNum_links()) {
				id_nuova_partenza = partenza.getLinkByIndex(num_link_visitato_di_partenza);
				nuova_Partenza = nodi.get(id_nuova_partenza);
				partenza.aggiornaNum_links_visitati();
				ricorsione(nuova_Partenza, percorso_parziale);
			}else {
				return true;
			}
		}
		return false;
	}
	
	public boolean isIdMaggiorePercorso(ArrayList<Integer> percorso_parziale, ArrayList<Integer> percorso_minimo){
		//riordino array e faccio altro
		Integer[] array_percorso_parziale= percorso_parziale.toArray(new Integer[percorso_parziale.size()]);
		Integer[] array_percorso_minimo= percorso_parziale.toArray(new Integer[percorso_minimo.size()]);
		
		Arrays.sort(array_percorso_minimo); 
		Arrays.sort(array_percorso_parziale);
		
		for(int i=array_percorso_minimo.length-1; i>=0; i--) {
			if(array_percorso_minimo[i]-array_percorso_parziale[i] < 0) {
				return true;
			}
		}		
		return false;
	}
	
	public double calcolaCosto(ArrayList<Integer> percorso) {
		double costo = 0;
		int j=0;
		
		for(int i=0; j<percorso.size()-1; i++) {
			j=i+1;
			costo= costo + matrice_adiacenza[percorso.get(i)][percorso.get(j)];
		}
		
		return costo;
	}
	
	public void trovaPercorsoMinimo() {
		
		int indice_partenza = nodi.get(0).getId();
		int indice_arrivo = nodi.get(numero_nodi-1).getId();
		
		
	}
	
	
	public void setPercorso_minimo(ArrayList<Integer> percorso_minimo_scelto){
		for(int i=0; i<percorso_minimo_scelto.size(); i++) {
			percorso_minimo.add(percorso_minimo_scelto.get(i));
		}
	}
	
	
	
	
	
}