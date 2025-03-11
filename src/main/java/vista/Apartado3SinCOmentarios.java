package vista;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Scanner;

/**
 * Programa que visualiza los empleados de un departamento específico
 * usando un fichero de acceso aleatorio (RAF)
 * 
 * @author EXUD1_MiNombre
 */
public class Apartado3SinCOmentarios {
    static int tamfile = 76;
    static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        File ficheroRAF = new File("./src/main/resources/raf181024.dat");

        if (!ficheroRAF.exists()) {
            System.out.println("Error: No se encuentra el fichero raf181024.dat");
            return;
        }

        int departamento = solicitarDepartamento();
        visualizarEmpleadosPorDepartamento(ficheroRAF, departamento);
    }

    private static int solicitarDepartamento() {
        int departamento = -1;
        boolean entradaValida = false;

        do {
            try {
                System.out.print("Introduce el número del departamento a consultar: ");
                departamento = Integer.parseInt(teclado.nextLine());

                if (departamento < 0) {
                    System.out.println("El número de departamento debe ser positivo.");
                } else {
                    entradaValida = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debes introducir un número entero.");
            }
        } while (!entradaValida);

        System.out.println();
        return departamento;
    }

    private static void visualizarEmpleadosPorDepartamento(File fichero, int departamento) {
        boolean encontrado = false;

        try (RandomAccessFile raf = new RandomAccessFile(fichero, "r")) {
            int idEmpleado;
            int deptEmpleado;
            double salarioEmpleado;
            char[] nombreEmpleado = new char[30];
            char aux;
            String nombre;

            raf.seek(0);

            System.out.println("Empleados del departamento " + departamento + ":");
            System.out.println("----------------------------------------");

            while (raf.getFilePointer() < raf.length()) {
                idEmpleado = raf.readInt();

                for (int i = 0; i < nombreEmpleado.length; i++) {
                    aux = raf.readChar();
                    nombreEmpleado[i] = aux;
                }
                nombre = new String(nombreEmpleado).trim();

                deptEmpleado = raf.readInt();
                salarioEmpleado = raf.readDouble();

                if (deptEmpleado == departamento) {
                    encontrado = true;
                    System.out.printf("ID: %-5d | Nombre: %-30s | Departamento: %-5d | Salario: %.2f€\n",
                            idEmpleado, nombre, deptEmpleado, salarioEmpleado);
                }
            }

            if (!encontrado) {
                System.out.println("No se encontraron empleados en el departamento " + departamento);
            }

        } catch (Exception e) {
            System.out.println("Error al acceder al fichero RAF: " + e.getMessage());
        }
    }
}