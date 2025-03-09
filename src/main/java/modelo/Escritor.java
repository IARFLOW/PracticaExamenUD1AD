package modelo;

import java.io.Serializable;

/**
 * Clase que representa un escritor de literatura española
 * @author EXUD1_MiNombre
 */
public class Escritor implements Serializable {
    private String nombre;
    private String apellido;
    private int nacimiento;

    public Escritor(String nombre, String apellido, int nacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nacimiento = nacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(int nacimiento) {
        this.nacimiento = nacimiento;
    }

    @Override
    public String toString() {
        return "Escritor{" + "nombre=" + nombre + ", apellido=" + apellido + ", nacimiento=" + nacimiento + '}';
    }
}