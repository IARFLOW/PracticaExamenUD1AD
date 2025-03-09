package vista;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * Programa que genera un listado de ficheros con opción de filtro
 * @author EXUD1_MiNombre
 */
public class Apartado1 {
    
    public static void lista(File directorio, BufferedWriter bw) throws IOException {
        // Creamos el filtro como lo hace la profesora
        FilenameFilter filtro = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                File ff = new File(dir, name);
                return name.startsWith("A") || ff.isDirectory();
            }
        };
        
        // Aplicamos el filtro, como en Enunciado7.java
        File[] archivos = directorio.listFiles(filtro);
        
        // Replicando exactamente su estructura de bucle
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
        // Definir dir a listar y fichero de salida
        File dir = new File("./src");
        File fich = new File("./src/main/resources/salida.txt");
        
        // Verificar existencia del dir origen
        if (!dir.exists()) {
            System.out.println("El dir no existe");
            return;
        }
        
        // Eliminar fichero de salida si existe
        if (fich.exists()) fich.delete();
        
        try {
            // Crear fichero de salida
            if (!fich.exists()) fich.createNewFile();
            
            // Escribir listado en fichero, usando try-with-resources como lo hace ella
            try (FileWriter fw = new FileWriter(fich); 
                 BufferedWriter bw = new BufferedWriter(fw)) {
                
                // Llamar al método lista
                lista(dir, bw);
            }
            
            System.out.println("Listado generado correctamente en: " + fich.getAbsolutePath());
            
        } catch (IOException e) {
            System.out.println("Error en la escritura");
        }
    }
}