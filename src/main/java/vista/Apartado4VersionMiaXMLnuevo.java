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
                    
                    // Punto 1: Mostrar atributos del pedido (exactamente como la profesora)
                    System.out.println(getAtributos(eElementPedido));
                    
                    // Acceso directo a elementos (como la profesora, sin verificaciones)
                    System.out.print("\tcliente:" + eElementPedido.getElementsByTagName("cliente").item(0).getTextContent().trim());
                    System.out.print("\tproducto:" + eElementPedido.getElementsByTagName("producto").item(0).getTextContent().trim());
                    System.out.print("\tcantidad:" + eElementPedido.getElementsByTagName("cantidad").item(0).getTextContent().trim());
                    System.out.print("\tiva:" + eElementPedido.getElementsByTagName("iva").item(0).getTextContent().trim() + "%");
                    
                    // Punto 2: Verificar atributos del elemento producto (exactamente como ella hace con apodo)
                    if (eElementPedido.getElementsByTagName("producto").item(0).hasAttributes()) {
                        String cadena = "";
                        for (int j = 0; j < eElementPedido.getElementsByTagName("producto").item(0).getAttributes().getLength(); j++) {
                            cadena += eElementPedido.getElementsByTagName("producto").item(0).getAttributes().item(j).getNodeName() + "="
                                    + eElementPedido.getElementsByTagName("producto").item(0).getAttributes().item(j).getNodeValue() + " ";
                        }
                        System.out.print(" Atributos de  " + eElementPedido.getElementsByTagName("producto").item(0).getNodeName() 
                                      + "--> " + cadena);
                    }
                    
                    System.out.println(); // Nueva línea después de la información básica
                    
                    // Punto 3: Para elementos repetidos (como marcas en su ejemplo)
                    for (int j = 0; j < eElementPedido.getElementsByTagName("observacion").getLength(); j++) {
                        System.out.print("\tobservacion " + j + ": " 
                                + eElementPedido.getElementsByTagName("observacion").item(j).getTextContent().trim());
                    }
                    
                    // Punto 4: Para elemento opcional (como delegado en su ejemplo)
                    if (eElementPedido.getElementsByTagName("facturado").getLength() > 0) {
                        System.out.print("\tEs facturado");
                    }
                    
                    System.out.println(); // Línea en blanco entre pedidos
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}