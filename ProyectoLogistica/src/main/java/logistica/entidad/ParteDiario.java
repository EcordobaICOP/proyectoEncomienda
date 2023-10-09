/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logistica.entidad;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ParteDiario")
public class ParteDiario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parteDiarioID")
    private int parteDiarioID;

    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(name = "horas")
    private int horas;

    @Column(name = "km")
    private int km;

    @ManyToOne
    @JoinColumn(name = "vehículoID")
    private Vehiculo vehiculo;

    // Constructores, getters y setters

    public ParteDiario() {
    }

    public ParteDiario(Date fecha, int horas, int km, Vehiculo vehículo) {
        this.fecha = fecha;
        this.horas = horas;
        this.km = km;
        this.vehiculo = vehículo;
    }

    public int getParteDiarioID() {
        return parteDiarioID;
    }

    public void setParteDiarioID(int parteDiarioID) {
        this.parteDiarioID = parteDiarioID;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
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
        return "ParteDiario{" +
               "parteDiarioID=" + parteDiarioID +
               ", fecha=" + fecha +
               ", horas=" + horas +
               ", km=" + km +
               '}';
    }
}

