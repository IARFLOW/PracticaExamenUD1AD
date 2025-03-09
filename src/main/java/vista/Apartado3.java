package vista;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Scanner;

/**
 * Programa que visualiza los empleados de un departamento usando RAF
 * @author EXUD1_MiNombre
 */
public class Apartado3 {
    // Definir tamaño de registro y constantes
    static final int TAMANO_REGISTRO = 76; // 4(id) + 60(nombre) + 4(dept) + 8(salario)
    static Scanner teclado = new Scanner(System.in);
    
    public static void main(String[] args) {
        File ficheroRAF = new File("./src/main/resources/raf181024.dat");
        
        // Verificar existencia del fichero
        if (!ficheroRAF.exists()) {
            System.out.println("Error: No se encuentra el fichero raf181024.dat");
            return;
        }
        
        try {
            // Solicitar número de departamento
            int departamento = solicitarDepartamento();
            
            // Visualizar empleados del departamento
            visualizarEmpleadosPorDepartamento(ficheroRAF, departamento);
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Solicita y valida un número de departamento
     */
    private static int solicitarDepartamento() {
        int departamento = -1;
        boolean valido = false;
        
        while (!valido) {
            try {
                System.out.print("Introduce el número de departamento a consultar: ");
                departamento = Integer.parseInt(teclado.nextLine());
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: Debes introducir un número entero.");
            }
        }
        
        return departamento;
    }
    
    /**
     * Visualiza los empleados que pertenecen al departamento especificado
     */
    private static void visualizarEmpleadosPorDepartamento(File fichero, int departamento) {
        try (RandomAccessFile raf = new RandomAccessFile(fichero, "r")) {
            int idEmpleado;
            int deptEmpleado;
            double salarioEmpleado;
            char[] nombreEmpleado = new char[30];
            char aux;
            String nombre;
            boolean encontrado = false;
            
            // Posicionarse al inicio del fichero
            raf.seek(0);
            
            // Recorrer el fichero
            while (raf.getFilePointer() < raf.length()) {
                // Leer ID
                idEmpleado = raf.readInt();
                
                // Leer nombre (30 caracteres)
                for (int i = 0; i < nombreEmpleado.length; i++) {
                    aux = raf.readChar();
                    nombreEmpleado[i] = aux;
                }
                nombre = new String(nombreEmpleado).trim();
                
                // Leer departamento
                deptEmpleado = raf.readInt();
                
                // Leer salario
                salarioEmpleado = raf.readDouble();
                
                // Si coincide el departamento, mostrar
                if (deptEmpleado == departamento) {
                    encontrado = true;
                    System.out.println("ID: " + idEmpleado + 
                                     " Nombre: " + nombre + 
                                     " Departamento: " + deptEmpleado + 
                                     " Salario: " + salarioEmpleado);
                }
            }
            
            if (!encontrado) {
                System.out.println("No se encontraron empleados en el departamento " + departamento);
            }
            
        } catch (Exception e) {
            System.out.println("Error al acceder al fichero RAF: " + e.getMessage());
            e.printStackTrace();
        }
    }
}