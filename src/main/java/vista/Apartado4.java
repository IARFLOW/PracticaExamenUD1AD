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
        String valorAtributo = "", nombreAtributo = "", cadena = "";

        if (elemento.hasAttributes()) {
            for (int i = 0; i < elemento.getAttributes().getLength(); i++) {
                // Vamos concatenando de los atributos los pares nombre=valor
                cadena += elemento.getAttributes().item(i).getNodeName() + "=" + 
                         elemento.getAttributes().item(i).getNodeValue() + " ";
            }
        }

        return (cadena.isEmpty()) ? "" : "Atributos de " + elemento.getNodeName() + "--> " + cadena;
    }
    
    public static void main(String[] args) {
        try {
            // Definir fichero XML
            File ficheroXML = new File("./src/main/resources/dom_181024_2.xml");
            
            // Verificar existencia del fichero
            if (!ficheroXML.exists()) {
                System.out.println("Error: No se encuentra el fichero dom_181024.xml");
                return;
            }
            
            // Configurar el parser DOM
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            Document doc = dBuilder.parse(ficheroXML);
            
            // Normalizar el documento
            doc.getDocumentElement().normalize();
            
            // Mostrar elemento raíz
            System.out.println("Elemento raíz: " + doc.getDocumentElement().getNodeName());
            System.out.println("----------------------------");
            
            // Obtener todos los nodos Pedido
            NodeList nodosPedido = doc.getElementsByTagName("Pedido");
            
            // Recorrer cada nodo Pedido
            for (int i = 0; i < nodosPedido.getLength(); i++) {
                Node nodoPedido = nodosPedido.item(i);
                
                if (nodoPedido.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementoPedido = (Element) nodoPedido;
                    
                    // Mostrar información del pedido y sus atributos
                    System.out.println("\nPedido " + (i+1) + ":");
                    System.out.println(getAtributos(elementoPedido));
                    
                    // Mostrar Cliente
                    if (elementoPedido.getElementsByTagName("Cliente").getLength() > 0) {
                        System.out.println("Cliente: " + 
                                         elementoPedido.getElementsByTagName("Cliente").item(0).getTextContent().trim());
                    }
                    
                    // Mostrar detalles del pedido
                    NodeList nodosDetalle = elementoPedido.getElementsByTagName("detalle");
                    System.out.println("Detalles del pedido:");
                    
                    for (int j = 0; j < nodosDetalle.getLength(); j++) {
                        Node nodoDetalle = nodosDetalle.item(j);
                        
                        if (nodoDetalle.getNodeType() == Node.ELEMENT_NODE) {
                            Element elementoDetalle = (Element) nodoDetalle;
                            
                            System.out.println("  Detalle " + (j+1) + ":");
                            System.out.println("  " + getAtributos(elementoDetalle));
                            
                            // Mostrar producto con su precio
                            if (elementoDetalle.getElementsByTagName("producto").getLength() > 0) {
                                Element producto = (Element) elementoDetalle.getElementsByTagName("producto").item(0);
                                System.out.println("    Producto: " + producto.getTextContent().trim());
                                System.out.println("    Precio: " + producto.getAttribute("precio"));
                            }
                            
                            // Mostrar cantidad
                            if (elementoDetalle.getElementsByTagName("cantidad").getLength() > 0) {
                                System.out.println("    Cantidad: " + 
                                                 elementoDetalle.getElementsByTagName("cantidad").item(0).getTextContent().trim());
                            }
                            
                            // Mostrar comentario si existe
                            if (elementoDetalle.getElementsByTagName("comentario").getLength() > 0) {
                                System.out.println("    Comentario: " + 
                                                 elementoDetalle.getElementsByTagName("comentario").item(0).getTextContent().trim());
                            }
                        }
                    }
                    
                    // Mostrar IVA
                    if (elementoPedido.getElementsByTagName("iva").getLength() > 0) {
                        System.out.println("IVA: " + 
                                         elementoPedido.getElementsByTagName("iva").item(0).getTextContent().trim() + "%");
                    }
                    
                    // Mostrar observaciones
                    NodeList observaciones = elementoPedido.getElementsByTagName("observaciones");
                    if (observaciones.getLength() > 0) {
                        System.out.println("Observaciones:");
                        for (int j = 0; j < observaciones.getLength(); j++) {
                            System.out.println("  - " + observaciones.item(j).getTextContent().trim());
                        }
                    }
                    
                    // Verificar si está facturado
                    if (elementoPedido.getElementsByTagName("facturado").getLength() > 0) {
                        System.out.println("Estado: Facturado");
                    }
                }
            }
            
        } catch (ParserConfigurationException e) {
            System.out.println("Error en la configuración del parser XML: " + e.getMessage());
        } catch (SAXException e) {
            System.out.println("Error en el análisis del documento XML: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error de entrada/salida: " + e.getMessage());
        }
    }
}