/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 *
 * @author ignacioampurdanes
 */
public class Apartado2Practicar {

    public static void main(String[] args) {

        File fich = new File("./src/main/resources/ex181024.dat");
        File fichObj = new File("./src/main/resources/obj181024.dat");
        if (!fich.exists()) {
            System.out.println("Error: No se puede mostrar el fichero ex181024.dat");
            return;
        }
        if (fichObj.exists()) {
            fichObj.delete();
        }
        try {
            if (!fichObj.exists())
                fichObj.createNewFile();

            System.out.println("Lectura de ficheros de datos:");

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
                        System.out.println("Escritor guardado: " + nombre + " " + apellido + " (" + nacimiento + ")");
                    }
                } catch (EOFException e) {
                    System.out.println("Fin de lectura del fichero de datos");
                }
            }
            System.out.println("\nLectura del fichero de objetos:");

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
