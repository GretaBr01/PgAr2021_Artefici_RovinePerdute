package classiPerdute;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Grafo T= new Grafo("Tonatiuh", InputXML.leggiXMLCitta("PgAr_Map_12.xml"));
		Grafo M= new Grafo("Metztli", InputXML.leggiXMLCitta("PgAr_Map_12.xml"));
		T.trovaPercorsoMinimo();
		M.trovaPercorsoMinimo();
		
		
		OutputXML.scritturaXML(T, M);
		
		
		System.out.println("fin");
		
	}

}
