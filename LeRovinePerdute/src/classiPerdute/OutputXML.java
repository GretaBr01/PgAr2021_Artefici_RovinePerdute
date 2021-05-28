package classiPerdute;

import java.io.FileOutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

public class OutputXML {
	private static final String NOME_CITTA = "nome";
	private static final String ID_CITTA = "id";
	private static final String NUM_CITTA_TOCCATE = "cities";
	private static final String COSTO_CARBURANTE = "cost";
	private static final String TEAM = "team";
	private static final String TAG_CITY = "city";
	private static final String TAG_ROUTE = "route";
	private static final String TAG_ROUTES = "routes";
	private static final String ERRORE_SCRITTURA = "Errore nella scrittura";
	private static final String ERRORE_WRITER = "Errore nell'inizializzazione del writer:";
	private static final String NOME_FILE_OUTPUT = "Routes.xml";

	
	/**
	 * scrittura del file utilizzando le informazioni estratte dalle variabili passate come argomento
	 * @param grafoT
	 * @param grafoM
	 */
	public static void scritturaXML (Grafo grafoT, Grafo grafoM) {
		//creazione della variabile xmlw di tipo XMLStreamWriter
		XMLOutputFactory xmlof = null;
		XMLStreamWriter xmlw = null;
		try {
			//istanziamento della variabile xmlw 
			xmlof = XMLOutputFactory.newInstance();
			xmlw = xmlof.createXMLStreamWriter(new FileOutputStream(NOME_FILE_OUTPUT), "utf-8");
			xmlw.writeStartDocument("utf-8", "1.0");
		} catch (Exception e) {
			System.out.println(ERRORE_WRITER);
			System.out.println(e.getMessage());
		}
		try {
			xmlw.writeStartElement(TAG_ROUTES);//apertura tag routes
			
			xmlw.writeStartElement(TAG_ROUTE);//apertura tag route
			xmlw.writeAttribute(TEAM, "Tonathiu");//scrittura dell'attributo team di route
			xmlw.writeAttribute(COSTO_CARBURANTE, String.format("%.2f", grafoT.getCosto_tot()));//scrittura dell'attributo cost di route
			xmlw.writeAttribute(NUM_CITTA_TOCCATE,String.valueOf(grafoT.getPercorso_minimo().size()));//scrittura dell'attributo cities di route
			for (int i = 0; i < grafoT.getPercorso_minimo().size(); i++) {
				xmlw.writeStartElement(TAG_CITY);//apertura tag city
				xmlw.writeAttribute(ID_CITTA, String.valueOf(grafoT.getId(i)) );//scrittura dell'attributo id di city
				xmlw.writeAttribute(NOME_CITTA,String.valueOf(grafoT.getNameByIndex(grafoT.getId(i))));//scrittura dell'attributo nome di city
				xmlw.writeEndElement(); //chiusura elemento vuoto city
			}
			xmlw.writeEndElement(); //chiusura elemento route
			
			xmlw.writeStartElement(TAG_ROUTE);//apertura tag route
			xmlw.writeAttribute(TEAM, "Metztli");//scrittura dell'attributo team di route
			xmlw.writeAttribute(COSTO_CARBURANTE, String.format("%.2f", grafoM.getCosto_tot()));//scrittura dell'attributo cost di route
			xmlw.writeAttribute(NUM_CITTA_TOCCATE, String.valueOf(grafoM.getPercorso_minimo().size()));//scrittura dell'attributo cities di route
			for (int i = 0; i < grafoM.getPercorso_minimo().size(); i++) {
				xmlw.writeStartElement(TAG_CITY);//apertura tag city
				xmlw.writeAttribute(ID_CITTA, String.valueOf(grafoM.getId(i)));//scrittura dell'attributo id di city
				xmlw.writeAttribute(NOME_CITTA,String.valueOf(grafoM.getNameByIndex(grafoM.getId(i))));//scrittura dell'attributo nome di city
				xmlw.writeEndElement(); //chiusura elemento vuoto city
			}
			xmlw.writeEndElement(); //chiusura elemento route
			
			xmlw.writeEndElement(); //chiusura elemento routes
			xmlw.flush(); // svuota il buffer e procede alla scrittura
			xmlw.close(); // chiusura del documento e delle risorse impiegate
			
		} catch (Exception e) { // se c’è un errore viene eseguita questa parte
			System.out.println(ERRORE_SCRITTURA);
		}

		
	}

}