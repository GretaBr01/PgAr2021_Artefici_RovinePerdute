package classiPerdute;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Grafo g= new Grafo("Tonatiuh", InputXML.leggiXMLCitta("PgAr_Map_5.xml"));
		g.calcolaGrafoPesato();
		//g.stampaMatrice();
		//g.dijkstra(0, 4);
		g.calcolaPercorsoMinimo();
		System.out.println("fin");
		
	}

}
