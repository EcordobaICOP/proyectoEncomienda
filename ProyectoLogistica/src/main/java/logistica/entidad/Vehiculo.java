/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logistica.entidad;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "Vehículo")
public class Vehiculo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehículoID")
    private int vehiculoID;

    @Column(name = "capacidad_carga")
    private int capacidadCarga;

    @Column(name = "nro_vehículo")
    private int nroVehiculo;

    @Column(name = "patente")
    private int patente;

    @ManyToOne
    @JoinColumn(name = "marcaID")
    private Marca marca;

    // Constructores, getters y setters

    public Vehiculo() {
    }

    public Vehiculo(int capacidadCarga, int nroVehiculo, int patente, Marca marca) {
        this.capacidadCarga = capacidadCarga;
        this.nroVehiculo = nroVehiculo;
        this.patente = patente;
        this.marca = marca;
    }

    public int getVehiculoID() {
        return vehiculoID;
    }

    public void setVehiculoID(int vehiculoID) {
        this.vehiculoID = vehiculoID;
    }

    public int getCapacidadCarga() {
        return capacidadCarga;
    }

    public void setCapacidadCarga(int capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }

    public int getNroVehiculo() {
        return nroVehiculo;
    }

    public void setNroVehiculo(int nroVehiculo) {
        this.nroVehiculo = nroVehiculo;
    }

    public int getPatente() {
        return patente;
    }

    public void setPatente(int patente) {
        this.patente = patente;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    // Resto de constructores, getters y setters

    @Override
    public String toString() {
        return "Vehiculo{" +
               "vehiculoID=" + vehiculoID +
               ", capacidadCarga=" + capacidadCarga +
               ", nroVehiculo=" + nroVehiculo +
               ", patente=" + patente +
               ", marca=" + marca +
               '}';
    }
}
