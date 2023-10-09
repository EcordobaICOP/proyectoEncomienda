/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logistica.entidad;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "Viaje")
public class Viaje implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "viajeID")
    private int viajeID;

    @Column(name = "destino")
    private String destino;

    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(name = "origen")
    private String origen;

    @ManyToOne
    @JoinColumn(name = "vehículoID")
    private Vehiculo vehiculo;

    @OneToMany(mappedBy = "viaje")
    private List<ViajePaquete> viajePaquetes;

    // Constructores, getters y setters

    public Viaje() {
    }

    public Viaje(String destino, Date fecha, String origen) {
        this.destino = destino;
        this.fecha = fecha;
        this.origen = origen;
    }

    public int getViajeID() {
        return viajeID;
    }

    public void setViajeID(int viajeID) {
        this.viajeID = viajeID;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public List<ViajePaquete> getViajePaquetes() {
        return viajePaquetes;
    }

    public void setViajePaquetes(List<ViajePaquete> viajePaquetes) {
        this.viajePaquetes = viajePaquetes;
    }

   

    @Override
    public String toString() {
        return "Viaje{" +
               "viajeID=" + viajeID +
               ", destino='" + destino + '\'' +
               ", fecha=" + fecha +
               ", origen='" + origen + '\'' +
               '}';
    }
}

