/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.io.*;

/**
 *
 * @author ignacioampurdanes
 */
public class Apartado1Practicar {

    /**
     * Método recursivo que recorre un directorio y sus subdirectorios
     * escribiendo la información de los archivos y carpetas en un archivo de texto
     */
    public static void lista(File directorio, BufferedWriter bw) throws IOException {

        // Define un filtro para seleccionar solo archivos .java y directorios
        // NOTA: Hay un error en el filtro, debería ser endsWith en lugar de startsWith
        FilenameFilter filtro = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                File ff = new File(dir, name);
                return name.endsWith(".java") || ff.isDirectory();
            }
        };

        // Obtiene la lista de archivos y directorios que cumplen con el filtro
        File[] archivos = directorio.listFiles(filtro);

        // Recorre cada archivo o directorio encontrado
        for (File archivo : archivos) {
            if (archivo.exists() && archivo.isFile()) {
                // Si es un archivo, escribe su ruta y nombre en el archivo de salida
                bw.write("Ruta: " + archivo.getParent() + " Nombre: " + archivo.getName() + "\n");
            } else if (archivo.exists() && archivo.isDirectory()) {
                // Si es un directorio, escribe su ruta y nombre
                bw.write("Ruta: " + archivo.getParent() + " Nombre " + archivo.getName() + "\n");
                // Llamada recursiva para explorar el contenido del directorio
                lista(archivo, bw);
            }
        }
    }

    /**
     * Método principal que inicia el proceso de listado de archivos
     */
    public static void main(String[] args) {

        // Define el directorio a explorar (carpeta src)
        File dir = new File("./src");
        // Define el archivo donde se guardará el resultado
        File fich = new File("./src/main/resources/salida.txt");

        // Comprueba si el directorio existe
        if (!dir.exists()) {
            System.out.println("El directorio no existe");
            return;
        }

        // Si el archivo de salida ya existe, lo elimina
        if (fich.exists())
            fich.delete();

        try {
            // Crea un nuevo archivo de salida
            if (!fich.exists())
                fich.createNewFile();

            // Crea los objetos necesarios para escribir en el archivo
            // Usa try-with-resources para cerrar automáticamente los recursos
            try (FileWriter fw = new FileWriter(fich);
                    BufferedWriter bw = new BufferedWriter(fw)) {
                // Llama al método lista para comenzar el recorrido recursivo
                lista(dir, bw);
            }

            // Muestra un mensaje de éxito
            System.out.println("Listado generado correctamente en: " + fich.getAbsolutePath());

        } catch (IOException e) {
            // Captura y muestra errores de entrada/salida
            System.out.println("Error en la escritura");
        }
    }
}
