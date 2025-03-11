package vista;

import java.io.File;              // Para manejar archivos
import java.io.RandomAccessFile;  // Clase específica para archivos de acceso aleatorio
import java.util.Scanner;         // Para leer entrada del usuario

/**
 * Programa que visualiza los empleados de un departamento específico
 * usando un fichero de acceso aleatorio (RAF)
 * 
 * EXPLICACIÓN GENERAL:
 * - Los archivos RAF (Random Access File) permiten acceder directamente a cualquier parte del archivo
 *   sin tener que leer secuencialmente desde el principio.
 * - Cada registro en un RAF tiene un tamaño fijo, lo que nos permite calcular la posición exacta
 *   de cualquier registro conociendo su índice.
 * - En este caso, cada registro de empleado ocupa 76 bytes en total:
 *   - ID (int): 4 bytesss
 *   - Nombre (String de 30 chars): 30 chars * 2 bytes = 60 bytes
 *   - Departamento (int): 4 bytes
 *   - Salario (double): 8 bytes
 * 
 * @author EXUD1_MiNombre
 */
public class Apartado3Comentarios {
    // Tamaño del registro completo en bytes
    static int tamfile = 76;  // 4(id) + 60(nombre: 30 chars*2) + 4(departamento) + 8(salario)
    
    // Scanner para entrada de datos del usuario - se define como estático para usarlo en los métodos
    static Scanner teclado = new Scanner(System.in);
    
    /**
     * Método principal que coordina la ejecución del programa
     */
    public static void main(String[] args) {
        // Definimos el archivo RAF con su ruta relativa (comienza con ./ para indicar directorio actual)
        File ficheroRAF = new File("./src/main/resources/raf181024.dat");
        
        // Verificamos que el archivo existe antes de intentar leerlo
        if (!ficheroRAF.exists()) {
            System.out.println("Error: No se encuentra el fichero raf181024.dat");
            return;  // Terminamos la ejecución si el archivo no existe
        }
        
        // Solicitamos al usuario que introduzca un número de departamento
        int departamento = solicitarDepartamento();
        
        // Visualizamos los empleados que pertenecen al departamento solicitado
        visualizarEmpleadosPorDepartamento(ficheroRAF, departamento);
    }
    
    /**
     * Solicita y valida un número de departamento al usuario
     * - Implementa un bucle do-while para repetir la solicitud hasta obtener un valor válido
     * - Captura excepciones para manejar entradas no numéricas
     * 
     * @return Número de departamento válido (entero positivo)
     */
    private static int solicitarDepartamento() {
        int departamento = -1;        // Valor inicial inválido para forzar la entrada del bucle
        boolean entradaValida = false; // Flag para controlar la salida del bucle
        
        // Repetir hasta obtener un valor válido
        do {
            try {
                // Solicitar entrada al usuario
                System.out.print("Introduce el número del departamento a consultar: ");
                
                // Leer un número entero - usamos nextLine() en lugar de nextInt() para poder capturar cualquier entrada
                departamento = Integer.parseInt(teclado.nextLine());
                
                // Validar que el número sea positivo (requisito lógico para un departamento)
                if (departamento < 0) {
                    System.out.println("El número de departamento debe ser positivo.");
                } else {
                    entradaValida = true;  // Si llegamos aquí, la entrada es válida
                }
            } catch (NumberFormatException e) {
                // Capturar la excepción si el usuario introduce algo que no es un número
                System.out.println("Error: Debes introducir un número entero.");
            }
        } while (!entradaValida);  // Continuar mientras no tengamos una entrada válida
        
        System.out.println(); // Línea en blanco para mejorar la presentación
        return departamento;  // Devolver el departamento válido
    }
    
    /**
     * Visualiza todos los empleados que pertenecen al departamento especificado
     * - Lee el archivo RAF registro por registro
     * - Para cada registro, verifica si el departamento coincide con el solicitado
     * - Muestra los datos de los empleados que pertenecen al departamento
     * 
     * @param fichero Fichero RAF que contiene los datos de empleados
     * @param departamento Número de departamento a consultar
     */
    private static void visualizarEmpleadosPorDepartamento(File fichero, int departamento) {
        // Variable para rastrear si encontramos al menos un empleado en el departamento
        boolean encontrado = false;
        
        // Usar try-with-resources para asegurar que el archivo se cierre correctamente
        // "r" indica que abrimos el archivo en modo lectura (read)
        try (RandomAccessFile raf = new RandomAccessFile(fichero, "r")) {
            // Variables para almacenar los datos de cada registro
            int idEmpleado;              // ID del empleado (4 bytes)
            int deptEmpleado;            // Departamento del empleado (4 bytes)
            double salarioEmpleado;      // Salario del empleado (8 bytes)
            char[] nombreEmpleado = new char[30];  // Nombre del empleado (30 caracteres)
            char aux;                    // Variable auxiliar para leer los caracteres uno a uno
            String nombre;               // String para almacenar el nombre completo
            
            // Posicionarse al inicio del fichero (posición 0)
            raf.seek(0);
            
            // Cabecera informativa
            System.out.println("Empleados del departamento " + departamento + ":");
            System.out.println("----------------------------------------");
            
            // Recorrer el fichero registro a registro
            // raf.getFilePointer() devuelve la posición actual en el archivo
            // raf.length() devuelve el tamaño total del archivo en bytes
            while (raf.getFilePointer() < raf.length()) {
                // 1. Leer ID del empleado (4 bytes)
                idEmpleado = raf.readInt();
                
                // 2. Leer nombre (60 bytes = 30 caracteres * 2 bytes cada uno)
                // En Java, los caracteres Unicode ocupan 2 bytes por carácter
                for (int i = 0; i < nombreEmpleado.length; i++) {
                    aux = raf.readChar();  // Leer un carácter (2 bytes)
                    nombreEmpleado[i] = aux;  // Almacenarlo en el array
                }
                // Convertir el array de caracteres a String y eliminar espacios en blanco al inicio y final
                nombre = new String(nombreEmpleado).trim();
                
                // 3. Leer departamento (4 bytes)
                deptEmpleado = raf.readInt();
                
                // 4. Leer salario (8 bytes)
                salarioEmpleado = raf.readDouble();
                
                // Verificar si el departamento del empleado coincide con el solicitado
                if (deptEmpleado == departamento) {
                    encontrado = true;  // Marcamos que hemos encontrado al menos un empleado
                    
                    // Mostrar los datos del empleado con formato
                    // %-5d: entero alineado a la izquierda con 5 espacios
                    // %-30s: string alineado a la izquierda con 30 espacios
                    // %.2f: decimal con 2 decimales
                    System.out.printf("ID: %-5d | Nombre: %-30s | Departamento: %-5d | Salario: %.2f€\n", 
                                     idEmpleado, nombre, deptEmpleado, salarioEmpleado);
                }
                
                // No necesitamos usar seek() para avanzar al siguiente registro porque
                // los métodos readInt(), readChar() y readDouble() ya avanzan el puntero
                // automáticamente después de leer cada valor
            }
            
            // Si no se encontraron empleados en el departamento solicitado
            if (!encontrado) {
                System.out.println("No se encontraron empleados en el departamento " + departamento);
            }
            
        } catch (Exception e) {
            // Capturar cualquier excepción que pueda ocurrir al leer el archivo
            System.out.println("Error al acceder al fichero RAF: " + e.getMessage());
        }
        
        // No es necesario cerrar el archivo explícitamente porque try-with-resources lo hace automáticamente
    }
}

/*
 * RESUMEN DE OPERACIONES RAF:
 * 
 * 1. Abrir el archivo:
 *    RandomAccessFile raf = new RandomAccessFile(fichero, "r") // "r" para lectura, "rw" para lectura/escritura
 * 
 * 2. Posicionarse en un punto específico:
 *    raf.seek(posición);  // La posición es un número de bytes desde el inicio del archivo
 * 
 * 3. Leer datos:
 *    - raf.readInt()     // Lee un entero (4 bytes)
 *    - raf.readChar()    // Lee un carácter (2 bytes)
 *    - raf.readDouble()  // Lee un double (8 bytes)
 *    - raf.readUTF()     // Lee una cadena UTF (longitud variable)
 * 
 * 4. Escribir datos (modo "rw"):
 *    - raf.writeInt(valor)     // Escribe un entero
 *    - raf.writeChar(valor)    // Escribe un carácter
 *    - raf.writeDouble(valor)  // Escribe un double
 *    - raf.writeUTF(valor)     // Escribe una cadena UTF
 * 
 * 5. Determinar la posición actual:
 *    raf.getFilePointer()  // Devuelve la posición actual en bytes
 * 
 * 6. Determinar el tamaño del archivo:
 *    raf.length()  // Devuelve el tamaño total del archivo en bytes
 * 
 * 7. Cerrar el archivo:
 *    raf.close()  // No necesario con try-with-resources
 */