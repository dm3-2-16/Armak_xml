/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import model.Armak;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author aitor
 */
public class GestionListaEnMemoria {
    
    public static ObservableList<Armak> cargaArmas(File file) throws ParserConfigurationException, SAXException, IOException
    {
        
        ObservableList<Armak> list = FXCollections.observableArrayList();   
        
        try {
            //Strima irekitzen dugu.
            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            
            NodeList armaNodo = document.getElementsByTagName("arma");
            for(int i =0; i<armaNodo.getLength(); i++){
                
                Element eElement = (Element) armaNodo.item(i);
                Armak arma = new Armak(eElement.getElementsByTagName("Izena").item(0).getTextContent(),
                        eElement.getElementsByTagName("Jatorria").item(0).getTextContent(),
                        eElement.getElementsByTagName("Description").item(0).getTextContent(),
                        Integer.parseInt(eElement.getElementsByTagName("Urtea").item(0).getTextContent()));
                list.add(arma);
                
            }
                
            }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void lista_gordexml(ObservableList<Armak> arma, File file) {
        try {
            
            
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
//
            // definimos el elemento raíz del documento
            Element eRaiz = doc.createElement("Armak");
            doc.appendChild(eRaiz);
            for(Armak arm:arma){
                Element eArma = doc.createElement("arma");
                eRaiz.appendChild(eArma);
                
                Element eName = doc.createElement("Izena");
                eName.appendChild(doc.createTextNode(arm.getName()));
                eArma.appendChild(eName);
                
                Element eJator = doc.createElement("Jatorria");
                eJator.appendChild(doc.createTextNode(arm.getJatorria()));
                eArma.appendChild(eJator);
                
                Element eDescription = doc.createElement("Description");
                eDescription.appendChild(doc.createTextNode(arm.getDesk()));
                eArma.appendChild(eDescription);
                
                Element eUrte = doc.createElement("Urtea");
                eUrte.appendChild(doc.createTextNode(String.valueOf(arm.getUrtea())));
                eArma.appendChild(eUrte);
                
            }
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);

            transformer.transform(source, result);
        }
            catch (Exception e) {
            e.printStackTrace();
        }
    }
}
        

    
    
    
    
 //  
    
    /*public static ObservableList<Armak> cargarDatos(){
        
        return FXCollections.observableArrayList(
            new Armak("Katana", "Japon", "Se refiere a un tipo particular de sable de filo único, curvado, tradicionalmente utilizado por los samuráis.", 1392),
            new Armak("Tambo", "Mundu osoa", "Palo grueso de aproximadamente 45cm", 0)
        );
    }     */

    
//    public static void añadirDato(ObservableList<Person> lista, Person persona){
//        
//        lista.add(persona);
//    }      
//    
    

