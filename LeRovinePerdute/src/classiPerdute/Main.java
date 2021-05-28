package classiPerdute;

import java.util.ArrayList;

public class Main {

	private static final String INPUT_XML_10000 = "PgAr_Map_10000.xml";
	private static final String INPUT_XML_2000 = "PgAr_Map_2000.xml";
	private static final String INPUT_XML_200 = "PgAr_Map_200.xml";
	private static final String INPUT_XML_50 = "PgAr_Map_50.xml";
	private static final String INPUT_XML_12 = "PgAr_Map_12.xml";
	private static final String INPUT_XML_5= "PgAr_Map_5.xml";

	public static void main(String[] args) {
		System.out.println("elaborazione in corso...");
		Grafo T= new Grafo("Tonatiuh", InputXML.leggiXMLCitta(INPUT_XML_50));
		Grafo M= new Grafo("Metztli", InputXML.leggiXMLCitta(INPUT_XML_50));
		T.calcolaGrafoPesato();
		M.calcolaGrafoPesato();
		T.trovaPercorsoMinimo();
		M.trovaPercorsoMinimo();
		
		OutputXML.scritturaXML(T, M);
		
		System.out.println("\nfine elaborazione");
		
	}

}