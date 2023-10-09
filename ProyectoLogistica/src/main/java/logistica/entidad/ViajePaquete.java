/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logistica.entidad;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "ViajePaquete")
public class ViajePaquete implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "viajePaqueteID")
    private int viajePaqueteID;

    @ManyToOne
    @JoinColumn(name = "viajeID")
    private Viaje viaje;

    @ManyToOne
    @JoinColumn(name = "paqueteID")
    private Paquete paquete;

    // Constructores, getters y setters

    public ViajePaquete() {
    }

    public ViajePaquete(Viaje viaje, Paquete paquete) {
        this.viaje = viaje;
        this.paquete = paquete;
    }

    public int getViajePaqueteID() {
        return viajePaqueteID;
    }

    public void setViajePaqueteID(int viajePaqueteID) {
        this.viajePaqueteID = viajePaqueteID;
    }

    public Viaje getViaje() {
        return viaje;
    }

    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }

    public Paquete getPaquete() {
        return paquete;
    }

    public void setPaquete(Paquete paquete) {
        this.paquete = paquete;
    }

    // Resto de constructores, getters y setters

    @Override
    public String toString() {
        return "ViajePaquete{" +
               "viajePaqueteID=" + viajePaqueteID +
               '}';
    }
}
