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
        }
        
}
