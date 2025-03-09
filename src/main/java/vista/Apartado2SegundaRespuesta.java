// Define el paquete al que pertenece esta clase
package vista;

// Importa las clases necesarias para trabajar con archivos, flujos de datos y serialización
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
// Importa la clase Escritor del paquete modelo
import modelo.Escritor;

/**
 * Leer datos de un fichero de datos básicos, convertirlos a objetos y
 * visualizarlos
 * 
 * @author EXUD1_MiNombre
 */
public class Apartado2SegundaRespuesta {

    public static void main(String[] args) {
        // Definir ficheros a utilizar
        // Archivo de origen con datos básicos
        File fich = new File("./src/main/resources/ex181024.dat");
        // Archivo de destino donde se guardarán los objetos serializados
        File fichObj = new File("./src/main/resources/obj181024.dat");

        // Verificar existencia del fichero de origen
        // Si no existe, muestra un mensaje de error y termina la ejecución
        if (!fich.exists()) {
            System.out.println("Error: No se encuentra el fichero ex181024.dat");
            return;
        }

        // Eliminar fichero de objetos si existe para crear uno nuevo
        if (fichObj.exists())
            fichObj.delete();

        try {
            // Crear fichero de objetos si no existe
            if (!fichObj.exists())
                fichObj.createNewFile();
            
            // Leer datos del fichero de datos y escribir objetos
            // Utiliza try-with-resources para cerrar automáticamente los recursos
            try (DataInputStream dis = new DataInputStream(new FileInputStream(fich)); // Para leer datos primitivos
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichObj))) { // Para escribir
                                                                                                      // objetos

                // Variables para almacenar los datos leídos
                String nombre, apellido;
                int nacimiento;

                // Leer hasta el final del fichero
                // Se utiliza un bucle infinito y una excepción EOFException para detectar el
                // final
                try {
                    while (true) {
                        // Lee los datos del archivo
                        nombre = dis.readUTF(); // Lee cadena en formato UTF-8
                        apellido = dis.readUTF(); // Lee cadena en formato UTF-8
                        nacimiento = dis.readInt(); // Lee un valor entero

                        // Crear objeto Escritor con los datos leídos
                        Escritor escritor = new Escritor(nombre, apellido, nacimiento);

                        // Escribir objeto en fichero (debe ser serializable)
                        oos.writeObject(escritor);

                        // Muestra mensaje indicando que se ha guardado un escritor
                        System.out.println("Escritor guardado: " + nombre + " " + apellido + " (" + nacimiento + ")");
                    }
                } catch (EOFException e) {
                    // Se lanza cuando se llega al final del archivo
                    System.out.println("Fin de lectura del fichero de datos");
                }
            } // Cierra automáticamente los flujos de datos

            System.out.println("\nLectura del fichero de objetos:");

            // Leer y visualizar objetos guardados
            // Otro bloque try-with-resources para el flujo de objetos
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichObj))) {

                try {
                    // Otro bucle infinito para leer objetos hasta el final
                    while (true) {
                        // Lee un objeto y lo convierte al tipo Escritor
                        Escritor escritor = (Escritor) ois.readObject();
                        // Muestra el objeto (llama al método toString() de Escritor)
                        System.out.println(escritor);
                    }
                } catch (EOFException e) {
                    // Se lanza cuando se llega al final del archivo de objetos
                    System.out.println("Fin de lectura del fichero de objetos");
                }
            } // Cierra automáticamente el flujo de objetos

        } catch (IOException | ClassNotFoundException e) {
            // Captura excepciones de entrada/salida y de clase no encontrada
            System.out.println("Error: " + e.getMessage());
        }
    }
}