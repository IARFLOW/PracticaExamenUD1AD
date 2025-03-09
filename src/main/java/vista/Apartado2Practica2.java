/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.io.*;
import modelo.Escritor;

/**
 *
 * @author ignacioampurdanes
 */
public class Apartado2Practica2 {
    
    public static void main(String[] args) {
        
    
    
        //Definir ficheros a utilizar
        //Archivo de origen con datos básicos
        File fich = new File("./src/main/resources/ex181024.dat");
        //Archivo de destino donde se guardarán los objetos serializados. 
        File fichObj = new File ("./src/main/resources/obj181024.dat");

        //Verificar existencia del fichero de origen.
        //Si no existe, muestra un mensaje de error y termina la ejecución.
        if (!fich.exists()){
                System.out.println("Error: el No se encuntra el fichero ex181024.dat");
                return;
        }
        
        // Eliminar fichero de objeto si existe
        if (fichObj.exists())
            fichObj.delete();
        
        try {
            //Crear fichero de objeto si no existe
            if (!fichObj.exists())
                fichObj.createNewFile();
            
            System.out.println("Lectura de fichero de datos: ");
            
            //Leer datos del fichero de datos y escribir objetos
            //Utilizar try-with-resources para cerrar automaticamente los recursos
            try (DataInputStream dis = new DataInputStream(new FileInputStream(fich));
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichObj))) {
                
                // VAriable para almacenar datos leídos
                String nombre;
                String apellido;
                int nacimiento;
                
                // Leer hasta el final del fichero
                // Se utiliza un bucle infinito y una excepción EOFException para detectar el 
                // final
                try {
                    while (true) {                        
                        //Lee los datos del archivo
                        nombre = dis.readUTF();
                        apellido = dis.readUTF();
                        nacimiento = dis.readInt();
                        
                        //Crear objeto Escritor con los datos leídos
                        Escritor escritor = new Escritor(nombre, apellido, nacimiento);
                        
                        //escribir objeto en fichero (debe ser serializable)
                        oos.writeObject(escritor);
                        
                        // Muestra mensaje indicando que se ha guardado en escritor
                        System.out.println("Escrito guardado: " + nombre + " " + apellido + " (" + nacimiento + ")");
                    }
                } catch (EOFException e) {
                    // Se lanza cuando se llega al final del archivo
                    System.out.println("Fin de lectura de fichero de datos");
                }
            } // Cierra automaticamente los flujos de datos
            
            System.out.println("\nLectura fichero de objetos");
            
            // Otro bloque Try-with-resources para el flujo de objetos 
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichObj))){
            //Leer y visualizar obsjetos guardados
                
                try {
                    //otro bucle infinito para leer objetos hasta el final
                    while (true) {                        
                        //Lee un objeto y lo convierte al tipo Escritor
                        Escritor escritor = (Escritor) ois.readObject();
                        //Muestra el objeto (llama al método toString() de Escritor)
                        System.out.println(escritor);
                    }
                } catch (EOFException e) {
                    //Se lanza cuando se llega al final del archivo de objetos
                    System.out.println("Fin de lectura del fichero de objetos");
                }
            }// Se Cierra automaticamente al flujo de objetos
            
            
        } catch (IOException | ClassNotFoundException e) {
            //Captura excepciones de entrada/salida y de clase no encontrada
            System.out.println("Error: " + e.getMessage());
        }
        
    }
        
}
