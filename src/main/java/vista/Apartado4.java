package vista;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Programa que muestra la información contenida en un fichero XML usando DOM
 * @author EXUD1_MiNombre
 */
public class Apartado4 {
    
    /**
     * Método auxiliar para obtener los atributos de un elemento
     */
    private static String getAtributos(Element elemento) {
        String cadena = "";
        
        if (elemento.hasAttributes()) {
            for (int i = 0; i < elemento.getAttributes().getLength(); i++) {
                cadena += elemento.getAttributes().item(i).getNodeName() + "=" + 
                          elemento.getAttributes().item(i).getNodeValue() + " ";
            }
        }
        
        return (cadena.isEmpty()) ? "" : "Atributos de " + elemento.getNodeName() + "--> " + cadena;
    }
    
    public static void main(String[] args) {
        try {
            // Definir fichero XML
            File ficheroXML = new File("./src/main/resources/dom_181024.xml");
            
            // Verificar existencia del fichero
            if (!ficheroXML.exists()) {
                System.out.println("Error: No se encuentra el fichero dom_181024.xml");
                return;
            }
            
            // Configurar el parser DOM
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.parse(ficheroXML);
            
            // Normalizar el documento
            documento.getDocumentElement().normalize();
            
            // Mostrar elemento raíz
            System.out.println("Elemento raíz: " + documento.getDocumentElement().getNodeName());
            
            // Obtener todos los nodos de primer nivel
            NodeList nodos = documento.getDocumentElement().getChildNodes();
            
            // Procesar cada nodo
            for (int i = 0; i < nodos.getLength(); i++) {
                Node nodo = nodos.item(i);
                
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) nodo;
                    
                    // Mostrar información del elemento
                    System.out.println("\nElemento: " + elemento.getNodeName());
                    
                    // Mostrar atributos si existen
                    String atributos = getAtributos(elemento);
                    if (!atributos.isEmpty()) {
                        System.out.println(atributos);
                    }
                    
                    // Recorrer los hijos del elemento
                    NodeList hijos = elemento.getChildNodes();
                    for (int j = 0; j < hijos.getLength(); j++) {
                        Node hijo = hijos.item(j);
                        
                        if (hijo.getNodeType() == Node.ELEMENT_NODE) {
                            Element elementoHijo = (Element) hijo;
                            
                            System.out.println("  - " + elementoHijo.getNodeName() + ": " + 
                                               elementoHijo.getTextContent().trim());
                            
                            // Mostrar atributos del hijo si existen
                            String atributosHijo = getAtributos(elementoHijo);
                            if (!atributosHijo.isEmpty()) {
                                System.out.println("    " + atributosHijo);
                            }
                            
                            // Verificar si hay elementos repetidos o opcionales
                            if (elemento.getElementsByTagName(elementoHijo.getNodeName()).getLength() > 1) {
                                System.out.println("    (Elemento repetido)");
                            }
                        }
                    }
                }
            }
            
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Error al procesar el fichero XML: " + e.getMessage());
            e.printStackTrace();
        }
    }
}