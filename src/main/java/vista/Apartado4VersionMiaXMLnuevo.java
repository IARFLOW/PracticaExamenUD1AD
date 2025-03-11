package vista;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Programa que muestra la información contenida en un fichero XML usando DOM
 * @author EXUD1_MiNombre
 */
public class Apartado4VersionMiaXMLnuevo {
    
    private static String getAtributos(Element elemento) {
        String valorAtributo = "", nombreAtributo = "", cadena = "";

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
            File ficheroXML = new File("./src/main/resources/dom_181024.xml");
            
            if (!ficheroXML.exists()) {
                System.out.println("El fichero dom_181024.xml no existe");
                return;
            }
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            Document doc = dBuilder.parse(ficheroXML);
            
            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            System.out.println("----------------------------");
            
            NodeList nList = doc.getElementsByTagName("Pedido");
            
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElementPedido = (Element) nNode;
                    
                    // Mostrar información del pedido
                    System.out.println("PEDIDO " + (temp+1) + ":");
                    System.out.println(getAtributos(eElementPedido));
                    
                    // Acceso directo sin verificaciones - estilo de la profesora
                    System.out.print("\tCliente: " + eElementPedido.getElementsByTagName("cliente").item(0).getTextContent().trim());
                    
                    // Para producto, obtenemos el elemento para acceder al atributo precio
                    Element producto = (Element) eElementPedido.getElementsByTagName("producto").item(0);
                    System.out.print("\tProducto: " + producto.getTextContent().trim());
                    System.out.print(" (Precio: " + producto.getAttribute("precio") + ")");
                    
                    System.out.print("\tCantidad: " + eElementPedido.getElementsByTagName("cantidad").item(0).getTextContent().trim());
                    System.out.println("\tIVA: " + eElementPedido.getElementsByTagName("iva").item(0).getTextContent().trim() + "%");
                    
                    // Para elementos que pueden repetirse - como en Ejercicio20
                    NodeList observaciones = eElementPedido.getElementsByTagName("observacion");
                    if (observaciones.getLength() > 0) {
                        System.out.println("\tObservaciones:");
                        for (int j = 0; j < observaciones.getLength(); j++) {
                            System.out.println("\t\t- " + observaciones.item(j).getTextContent().trim());
                        }
                    }
                    
                    // Para elemento opcional - como en Ejercicio20
                    if (eElementPedido.getElementsByTagName("facturado").getLength() > 0) {
                        System.out.println("\tEstado: Facturado");
                    }
                    
                    System.out.println();
                }
            }
            
        } catch (Exception e) {
            System.out.println("Error: ");
            e.printStackTrace();
        }
    }
}