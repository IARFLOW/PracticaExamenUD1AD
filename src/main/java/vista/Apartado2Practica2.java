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
        
        File fich = new File("./src/main/resources/ex181024.dat");
        File fichObj = new File("./src/main/resources/obj181024.dat");
        
        if (!fich.exists()) {
            System.out.println("Error: No se encuentra el fichero ex181024.dat");
            return;
        }   
            
        if (fichObj.exists())
            fichObj.delete();
        
        try {
            if(!fichObj.exists())
                fichObj.createNewFile();
            try (DataInputStream dis = new DataInputStream(new FileInputStream(fich));
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichObj))) {
                
                String nombre;
                String apellido;
                int nacimiento;
                
                try {
                    while (true) {                        
                        nombre = dis.readUTF();
                        apellido = dis.readUTF();
                        nacimiento = dis.readInt();
                        
                        Escritor escritor = new Escritor(nombre, apellido, nacimiento);
                        
                        oos.writeObject(escritor);
                        
                        System.out.println("Escritor guardado: " + nombre + " " + apellido + "("+ nacimiento + ")");
                    }
                } catch (EOFException e) {
                    System.out.println("Fin de lectura de datos");
                }
            }
            System.out.println("Lectira fichero objetos");
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichObj))){
                try {
                    while (true) {                        
                        Escritor escritor = (Escritor) ois.readObject();
                        System.out.println(escritor);
                    }
                } catch (EOFException e) {
                    System.out.println("Fin de lectura de objetos");
                }
            }
            
            
        }catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
