/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;


/**
 *
 * @author ignacioampurdanes
 */
public class Apartado1Practicar2 {

    public static void lista(File directorio, BufferedWriter bw) throws IOException {

        FilenameFilter filtro = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                File ff = new File(dir, name);
                return name.startsWith("A") || ff.isDirectory();
            }
        };

        File[] archivos = directorio.listFiles(filtro);

        for (File archivo : archivos) {
            if (archivo.exists() && archivo.isFile()) {
                bw.write("Ruta: " + archivo.getParent() + " Nombre: " + archivo.getName() + "\n");
            } else if (archivo.exists() && archivo.isDirectory()) {
                bw.write("Ruta: " + archivo.getParent() + " Nombre: " + archivo.getName() + "\n");
                lista(archivo, bw);
            }
        }

    }

    public static void main(String[] args) {

        File dir = new File("./src");
        File fich = new File("./src/main/resources/salida.txt");

        if (!dir.exists()) {
            System.out.println("El directorio no existe");
            return;
        }

        if (fich.exists())
            fich.delete();

        try {
            if (!fich.exists())
                fich.createNewFile();

            try (FileWriter fw = new FileWriter(fich);
                    BufferedWriter bw = new BufferedWriter(fw)) {
                lista(dir, bw);
            }

            System.out.println("Listado generado correctamente en: " + fich.getAbsolutePath());

        } catch (IOException e) {
            System.out.println("Error en la escritura");
        }

    }

}
