/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author ignacioampurdanes
 */
public class Apartado4Practica1 {

    private static String getAtributos(Element elemnto) {
        String valorAtributo = "", nombreAtributo = "", cadena = "";

        if (elemnto.hasAttributes()) {
            for (int i = 0; i < elemnto.getAttributes().getLength(); i++) {
                cadena += elemnto.getAttributes().item(i).getNodeName() + "="
                        + elemnto.getAttributes().item(i).getNodeValue() + " ";
            }
        }
        return (cadena.isEmpty()) ? "" : "Atributos de " + elemnto.getNodeName() + "-->" + cadena;
    }

    public static void main(String[] args) {
        try {
            File ficheroXML = new File("./src/main/resources/dom_181024_2");
            if (!ficheroXML.exists()) {
                System.out.println("Error: El fichero dom_181024_2 no se encuentra");
                return;
            }
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            Document doc = dBuilder.parse(ficheroXML);
            
            doc.getDocumentElement().normalize();
            
            System.out.println("Elemento raiz: " + doc.getDocumentElement().getNodeName());
            System.out.println("-------------------------");
            NodeList nodosPedido = doc.getElementsByTagName("Pedido");
            
            for (int i = 0; i < nodosPedido.getLength(); i++) {
                Node nodoPedido = nodosPedido.item(i);
                if (nodoPedido.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementoPedido = (Element) nodoPedido;
                    System.out.println("\nPedido " + (i+1) + ":");
                }
            }

        } catch (Exception e) {
        }
    }
}
