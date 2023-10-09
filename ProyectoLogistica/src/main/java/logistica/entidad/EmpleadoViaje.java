/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logistica.entidad;



import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "EmpleadoViaje")
public class EmpleadoViaje implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "empleadoViajeID")
    private int empleadoViajeID;

    @ManyToOne
    @JoinColumn(name = "viajeID")
    private Viaje viaje;

    @ManyToOne
    @JoinColumn(name = "empleadoID")
    private Empleado empleado;

    // Constructores, getters y setters

    public EmpleadoViaje() {  
    }

    public EmpleadoViaje(Viaje viaje, Empleado empleado) {
        this.viaje = viaje;
        this.empleado = empleado;
    }

    // Resto de constructores, getters y setters

    @Override
    public String toString() {
        return "EmpleadoViaje{" +
               "empleadoViajeID=" + empleadoViajeID +
               '}';
    }

    public int getEmpleadoViajeID() {
        return empleadoViajeID;
    }

    public void setEmpleadoViajeID(int empleadoViajeID) {
        this.empleadoViajeID = empleadoViajeID;
    }

    public Viaje getViaje() {
        return viaje;
    }

    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
}
