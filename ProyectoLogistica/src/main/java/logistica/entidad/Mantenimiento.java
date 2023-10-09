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
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "Mantenimiento")
public class Mantenimiento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mantenimientoID")
    private int mantenimientoID;

    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(name = "km")
    private int km;

    @ManyToOne
    @JoinColumn(name = "vehículoID")
    private Vehiculo vehiculo;

    // Constructores, getters y setters

    public Mantenimiento() {
    }

    public Mantenimiento(Date fecha, int km, Vehiculo vehiculo) {
        this.fecha = fecha;
        this.km = km;
        this.vehiculo = vehiculo;
    }

    public int getMantenimientoID() {
        return mantenimientoID;
    }

    public void setMantenimientoID(int mantenimientoID) {
        this.mantenimientoID = mantenimientoID;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    // Resto de constructores, getters y setters

    @Override
    public String toString() {
        return "Mantenimiento{" +
               "mantenimientoID=" + mantenimientoID +
               ", fecha=" + fecha +
               ", km=" + km +
               ", vehiculo=" + vehiculo +
               '}';
    }
}

