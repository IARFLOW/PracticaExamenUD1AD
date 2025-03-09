package vista;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import modelo.Escritor;

/**
 * Programa que lee datos de un fichero de datos básicos y los convierte a objetos
 * @author EXUD1_MiNombre
 */
public class Apartado2 {
    
    public static void main(String[] args) {
        // Definir ficheros a utilizar
        File ficheroDatos = new File("./src/main/resources/ex181024.dat");
        File ficheroObjetos = new File("./src/main/resources/obj181024.dat");
        
        // Verificar existencia del fichero de origen
        if (!ficheroDatos.exists()) {
            System.out.println("Error: No se encuentra el fichero ex181024.dat");
            return;
        }
        
        // Eliminar fichero de objetos si existe
        if (ficheroObjetos.exists()) {
            ficheroObjetos.delete();
        }
        
        try {
            // Crear fichero de objetos
            ficheroObjetos.createNewFile();
            
            // Leer datos y convertir a objetos
            try (DataInputStream dis = new DataInputStream(new FileInputStream(ficheroDatos));
                 ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ficheroObjetos))) {
                
                String nombre;
                String apellido;
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
                    }
                } catch (IOException e) {
                    // Final de fichero, es normal
                }
            }
            
            // Leer y visualizar objetos guardados
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheroObjetos))) {
                
                System.out.println("Contenido del fichero de objetos:");
                
                try {
                    while (true) {
                        Escritor escritor = (Escritor) ois.readObject();
                        System.out.println(escritor);
                    }
                } catch (IOException e) {
                    // Final de fichero, es normal
                }
            }
            
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}