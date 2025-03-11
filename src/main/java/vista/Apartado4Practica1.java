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
            File ficheroXML = new File("./src/main/resources/dom_181024.xml");
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
            NodeList nList = doc.getElementsByTagName("Pedido");
            
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElementoPedido = (Element) nNode;
                    System.out.println(getAtributos(eElementoPedido));
                    
                    System.out.print("\tcliente: " + eElementoPedido.getElementsByTagName("cliente").item(0).getTextContent().trim());
                    System.out.print("\tproducto:" + eElementoPedido.getElementsByTagName("producto").item(0).getTextContent().trim());
                    System.out.print("\tcantidad: " + eElementoPedido.getElementsByTagName("cantidad").item(0).getTextContent().trim());
                    System.out.print("\tiva " + eElementoPedido.getElementsByTagName("iva").item(0).getTextContent().trim() + "%");
                    
                    if (eElementoPedido.getElementsByTagName("producto").item(0).hasAttributes()) {
                        String cadena = "";
                        for (int j = 0; j < eElementoPedido.getElementsByTagName("producto").item(0).getAttributes().getLength(); j++) {
                            cadena += eElementoPedido.getElementsByTagName("producto").item(0).getAttributes().item(j).getNodeName() + "="
                                    + eElementoPedido.getElementsByTagName("producto").item(0).getAttributes().item(j).getNodeValue() + " ";
                        }
                        System.out.println(" Atributos de " + eElementoPedido.getElementsByTagName("producto").item(0).getNodeName()
                        +  "-->" + cadena);
                    }
                    System.out.println();
                    for (int j = 0; j < eElementoPedido.getElementsByTagName("observacion").getLength(); j++) {
                        System.out.println("\tobservacion " + j + ": "
                        + eElementoPedido.getElementsByTagName("observacion").item(j).getTextContent().trim());
                    }
                    if (eElementoPedido.getElementsByTagName("facturado").getLength() > 0) {
                        System.out.println("\tEs faturado");
                    }
                    System.out.println();
                }
            }

        } catch (Exception e) {
        }
    }
}
