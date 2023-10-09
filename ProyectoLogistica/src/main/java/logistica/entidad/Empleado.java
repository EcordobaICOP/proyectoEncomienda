/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logistica.entidad;

/**
 *
 * @author ULTRA
 */
import java.io.Serializable;
import javax.persistence.*;
@Entity
@Table(name = "Empleado")
public class Empleado implements Serializable {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "empleadoID")
    private int empleadoID;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "direccion")
    private int direccion;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "nro_documento")
    private int nroDocumento;

    @Column(name = "nro_teléfono")
    private int nroTelefono;

    @Column(name = "salario")
    private int salario;

    // Constructores, getters y setters

    public Empleado() {
    }

    public Empleado(String apellido, int direccion, String nombre, int nroDocumento, int nroTelefono, int salario) {
        this.apellido = apellido;
        this.direccion = direccion;
        this.nombre = nombre;
        this.nroDocumento = nroDocumento;
        this.nroTelefono = nroTelefono;
        this.salario = salario;
    }

    public int getEmpleadoID() {
        return empleadoID;
    }

    public void setEmpleadoID(int empleadoID) {
        this.empleadoID = empleadoID;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getDireccion() {
        return direccion;
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(int nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public int getNroTelefono() {
        return nroTelefono;
    }

    public void setNroTelefono(int nroTelefono) {
        this.nroTelefono = nroTelefono;
    }

    public int getSalario() {
        return salario;
    }

    public void setSalario(int salario) {
        this.salario = salario;
    }

  

    @Override
    public String toString() {
        return "Empleado{" +
               "empleadoID=" + empleadoID +
               ", apellido='" + apellido + '\'' +
               ", direccion=" + direccion +
               ", nombre='" + nombre + '\'' +
               ", nroDocumento=" + nroDocumento +
               ", nroTelefono=" + nroTelefono +
               ", salario=" + salario +
               '}';
    }
}

