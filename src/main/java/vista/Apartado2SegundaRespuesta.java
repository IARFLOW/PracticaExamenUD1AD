package vista;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import modelo.Escritor;

/**
 * Leer datos de un fichero de datos básicos, convertirlos a objetos y visualizarlos
 * @author EXUD1_MiNombre
 */
public class Apartado2SegundaRespuesta {
    
    public static void main(String[] args) {
        // Definir ficheros a utilizar
        File fich = new File("./src/main/resources/ex181024.dat");
        File fichObj = new File("./src/main/resources/obj181024.dat");
        
        // Verificar existencia del fichero de origen
        if (!fich.exists()) {
            System.out.println("Error: No se encuentra el fichero ex181024.dat");
            return;
        }
        
        // Eliminar fichero de objetos si existe
        if (fichObj.exists()) fichObj.delete();
        
        try {
            // Crear fichero de objetos si no existe
            if (!fichObj.exists()) fichObj.createNewFile();
            
            // Leer datos del fichero de datos y escribir objetos
            try (DataInputStream dis = new DataInputStream(new FileInputStream(fich));
                 ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichObj))) {
                
                String nombre, apellido;
                int nacimiento;
                
                // Leer hasta el final del fichero
                try {
                    while (true) {
                        nombre = dis.readUTF();
                        apellido = dis.readUTF();
                        nacimiento = dis.readInt();
                        
                        // Crear objeto Escritor
                        Escritor escritor = new Escritor(nombre, apellido, nacimiento);
                        
                        // Escribir objeto en fichero
                        oos.writeObject(escritor);
                        
                        System.out.println("Escritor guardado: " + nombre + " " + apellido + " (" + nacimiento + ")");
                    }
                } catch (EOFException e) {
                    System.out.println("Fin de lectura del fichero de datos");
                }
            }
            
            System.out.println("\nLectura del fichero de objetos:");
            
            // Leer y visualizar objetos guardados
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichObj))) {
                
                try {
                    while (true) {
                        Escritor escritor = (Escritor) ois.readObject();
                        System.out.println(escritor);
                    }
                } catch (EOFException e) {
                    System.out.println("Fin de lectura del fichero de objetos");
                }
            }
            
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}