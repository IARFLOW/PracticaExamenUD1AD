package vista;

// Importaciones necesarias para trabajar con DOM y archivos
import java.io.File;                                  // Para manejo de archivos
import java.io.IOException;                           // Para excepciones de E/S
import javax.xml.parsers.DocumentBuilder;             // Constructor DOM
import javax.xml.parsers.DocumentBuilderFactory;      // Fábrica de constructores
import javax.xml.parsers.ParserConfigurationException; // Excepción de configuración
import org.w3c.dom.Document;                          // Documento XML
import org.w3c.dom.Element;                           // Elemento (etiqueta) XML
import org.w3c.dom.Node;                              // Nodo XML genérico
import org.w3c.dom.NodeList;                          // Lista de nodos
import org.xml.sax.SAXException;                      // Excepción de análisis XML

/**
 * Programa que muestra la información contenida en un fichero XML usando DOM
 * Sigue exactamente el estilo de codificación de la profesora
 * @author EXUD1_MiNombre
 */
public class Apartado4EstarabienDuditativo {
    
    /**
     * Método que obtiene todos los atributos de un elemento XML
     * Este método es IDÉNTICO al que utiliza la profesora en Ejercicio20
     * 
     * @param elemento Elemento del que queremos extraer los atributos
     * @return Cadena con los atributos formateados, o cadena vacía si no hay atributos
     */
    private static String getAtributos(Element elemento) {
        // Variables para construir la respuesta - idénticas a las que usa la profesora
        String valorAtributo = "", nombreAtributo = "", cadena = "";

        // Verificar si el elemento tiene atributos
        if (elemento.hasAttributes()) {
            // Recorrer cada atributo
            for (int i = 0; i < elemento.getAttributes().getLength(); i++) {
                // Concatenar nombre=valor de cada atributo
                cadena += elemento.getAttributes().item(i).getNodeName() + "=" + 
                         elemento.getAttributes().item(i).getNodeValue() + " ";
            }
        }

        // Devolver cadena formateada o vacía
        return (cadena.isEmpty()) ? "" : "Atributos de " + elemento.getNodeName() + "--> " + cadena;
    }
    
    public static void main(String[] args) {
        try {
            // Definir el archivo XML que vamos a leer
            File ficheroXML = new File("./src/main/resources/dom_181024_2.xml");
            
            // Verificar si el archivo existe
            if (!ficheroXML.exists()) {
                System.out.println("El fichero dom_181024.xml no existe");
                return; // Terminar la ejecución si el archivo no existe
            }
            
            /* 
             * IMPORTANTE: Las 3 líneas siguientes son EXACTAMENTE las que aparecen 
             * en la chuleta del examen y en los ejemplos de la profesora.
             * Son obligatorias para configurar el parser DOM correctamente.
             */
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            Document doc = dBuilder.parse(ficheroXML);
            
            // Normalizar el documento XML (buena práctica recomendada por W3C)
            doc.getDocumentElement().normalize();
            
            // Mostrar el nombre del elemento raíz (exactamente como lo hace la profesora)
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            System.out.println("----------------------------");
            
            // Obtener todos los nodos "Pedido" - uso de 'nList' como la profesora
            NodeList nList = doc.getElementsByTagName("Pedido");
            
            // Recorrer cada nodo Pedido - uso de 'temp' como la profesora
            for (int temp = 0; temp < nList.getLength(); temp++) {
                // Obtener el nodo actual
                Node nNode = nList.item(temp);
                
                // Verificar que sea un nodo de tipo elemento
                // Esta verificación es crítica porque puede haber nodos de texto, comentarios, etc.
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    // Convertir el nodo a elemento - nombre similar al de la profesora
                    Element eElementPedido = (Element) nNode;
                    
                    // Mostrar los atributos del pedido usando el método getAtributos
                    // La profesora siempre usa este método primero
                    System.out.println(getAtributos(eElementPedido));
                    
                    // Acceso directo al elemento Cliente - estilo de la profesora
                    // Usa System.out.print con tabulación y concatenación con +
                    System.out.print("\tCliente: "+ eElementPedido.getElementsByTagName("Cliente").item(0).getTextContent().trim());
                    
                    // Mostrar el IVA en la misma línea si existe
                    // Verificamos primero si el elemento existe
                    if (eElementPedido.getElementsByTagName("iva").getLength() > 0) {
                        System.out.print("\tIVA: "+ eElementPedido.getElementsByTagName("iva").item(0).getTextContent().trim() + "%");
                    }
                    
                    // Nueva línea para los detalles
                    System.out.println("\n\tDetalles:");
                    
                    // Procesamos los elementos detalle - acceso directo a una colección de elementos
                    NodeList detalles = eElementPedido.getElementsByTagName("detalle");
                    
                    // Recorrer todos los detalles
                    for (int j = 0; j < detalles.getLength(); j++) {
                        // Convertir a Element - La profesora suele hacer estos cast
                        Element detalle = (Element) detalles.item(j);
                        
                        // Mostrar información del detalle y sus atributos
                        System.out.println("\t\tDetalle " + (j+1) + ": " + getAtributos(detalle));
                        
                        // Acceso directo al elemento producto dentro del detalle
                        // La profesora suele hacer cast a Element cuando necesita acceder a atributos
                        Element producto = (Element) detalle.getElementsByTagName("producto").item(0);
                        
                        // Mostrar nombre del producto y su precio (accediendo al atributo)
                        System.out.print("\t\t\tProducto: " + producto.getTextContent().trim());
                        System.out.print(" (Precio: " + producto.getAttribute("precio") + ")");
                        
                        // Mostrar cantidad
                        System.out.print("\t\t\tCantidad: " + 
                                       detalle.getElementsByTagName("cantidad").item(0).getTextContent().trim());
                        
                        // Verificar si existe comentario (elemento opcional)
                        // La profesora siempre verifica la existencia antes de acceder
                        if (detalle.getElementsByTagName("comentario").getLength() > 0) {
                            System.out.println("\t\t\tComentario: " + 
                                             detalle.getElementsByTagName("comentario").item(0).getTextContent().trim());
                        }
                        
                        System.out.println(); // Línea vacía para mejorar la legibilidad
                    }
                    
                    // Manejo de elementos repetidos (observaciones)
                    // La profesora suele obtener una lista y recorrerla
                    NodeList observaciones = eElementPedido.getElementsByTagName("observaciones");
                    if (observaciones.getLength() > 0) {
                        System.out.println("\tObservaciones:");
                        for (int j = 0; j < observaciones.getLength(); j++) {
                            System.out.println("\t\t- " + observaciones.item(j).getTextContent().trim());
                        }
                    }
                    
                    // Verificar elemento opcional vacío (facturado)
                    // Comprueba si existe el elemento, no su contenido
                    if (eElementPedido.getElementsByTagName("facturado").getLength() > 0) {
                        System.out.println("\tEstado: Facturado");
                    }
                    
                    System.out.println(); // Línea vacía entre pedidos
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: ");
        }
    }
}