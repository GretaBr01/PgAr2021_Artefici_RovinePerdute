package classiPerdute;

import java.io.FileInputStream;
import java.util.ArrayList;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;


public class InputXML {
	
	private static final String TAG_MAP = "map";
	private static final String TAG_CITY = "city";
	private static final String TAG_LINK = "link";
	private static final String ERRORE_READER = "Errore nell'inizializzazione del reader:";
	

	/** 
	 * lettura del file passato come argomento e creazione di un'ArrayList contenente le informazioni ricavate 
	 * @param nome_file
	 * @return ArrayList di oggetti di tipo Persona
	 */
	public static ArrayList<Citta> leggiXMLCitta(String nome_file) {
		ArrayList<Citta> cities= new ArrayList<Citta>();
		Citta citta = null;
		String filename= nome_file;
		
		//creazione della variabile xmlr di tipo XMLStreamReader
		XMLInputFactory xmlif = null;
		XMLStreamReader xmlr = null;
		try {
			//istanziamento della variabile xmlr 
			 xmlif = XMLInputFactory.newInstance();
			 xmlr = xmlif.createXMLStreamReader(filename, new FileInputStream(filename));
		} catch (Exception e) {
			System.out.println(ERRORE_READER);
			System.out.println(e.getMessage());
		}
		
		try {
			while (xmlr.hasNext()){ 
				 switch (xmlr.getEventType()) { 
					 case XMLStreamConstants.START_ELEMENT: 
						 switch (xmlr.getLocalName()){
						 case TAG_MAP:
							 break;
						 case TAG_CITY:	//caso tag di apertura "city": istanziamento della variabili di tipo Citta
							 citta = new Citta();
							 
							 citta.setId(Integer.valueOf(xmlr.getAttributeValue(0))); //settaggio variabile id della citta
							 citta.setNome(xmlr.getAttributeValue(1)); //settaggio variabile nome della citta
							 citta.setX(Integer.valueOf(xmlr.getAttributeValue(2))); //settaggio variabile x della citta
							 citta.setY(Integer.valueOf(xmlr.getAttributeValue(3))); //settaggio variabile y della citta
							 citta.setH(Integer.valueOf(xmlr.getAttributeValue(4))); //settaggio variabile h della citta
							 break;
						 case TAG_LINK: //caso tag di apertura "link": istanziamento della ArrayList di tipo link
							 citta.addLink(Integer.valueOf(xmlr.getAttributeValue(0))); 
							 break;
						 
						 }						 	
						 break;
					 case XMLStreamConstants.END_ELEMENT: //caso tag di chiusura "citta": aggiunta dell'oggetto citta all'ArrayList di tipo Citta
						 if(xmlr.getLocalName().equalsIgnoreCase(TAG_CITY)) {
						 		cities.add(citta);
						 }
						 break;
				 }
				xmlr.next();
			}
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}			
		return cities;
	}
}
