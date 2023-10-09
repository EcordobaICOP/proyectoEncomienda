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
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "Paquete")
public class Paquete implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paqueteID")
    private int paqueteID;

    @Column(name = "código_paquete")
    private int codigoPaquete;

    @Column(name = "descripción")
    private String descripcion;

    @Column(name = "domicilioEntrega")
    private String domicilioEntrega;

    @Column(name = "estado")
    private String estado;

    @ManyToOne
    @JoinColumn(name = "clienteID")
    private Cliente emisor;

    @ManyToOne
    @JoinColumn(name = "receptorID", updatable = false) // Esta relación es de solo lectura
    private Cliente receptor;

    @OneToMany(mappedBy = "paquete", cascade = CascadeType.ALL)
    private List<ViajePaquete> viajes;

    // Constructores, getters y setters

    public Paquete() {
    }

    public Paquete(int codigoPaquete, String descripcion, String domicilioEntrega, String estado, Cliente emisor, Cliente receptor) {
        this.codigoPaquete = codigoPaquete;
        this.descripcion = descripcion;
        this.domicilioEntrega = domicilioEntrega;
        this.estado = estado;
        this.emisor = emisor;
        this.receptor = receptor;
    }

    public int getPaqueteID() {
        return paqueteID;
    }

    public void setPaqueteID(int paqueteID) {
        this.paqueteID = paqueteID;
    }

    public int getCodigoPaquete() {
        return codigoPaquete;
    }

    public void setCodigoPaquete(int codigoPaquete) {
        this.codigoPaquete = codigoPaquete;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDomicilioEntrega() {
        return domicilioEntrega;
    }

    public void setDomicilioEntrega(String domicilioEntrega) {
        this.domicilioEntrega = domicilioEntrega;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cliente getEmisor() {
        return emisor;
    }

    public void setEmisor(Cliente emisor) {
        this.emisor = emisor;
    }

    public Cliente getReceptor() {
        return receptor;
    }

    public void setReceptor(Cliente receptor) {
        this.receptor = receptor;
    }

    public List<ViajePaquete> getViajes() {
        return viajes;
    }

    public void setViajes(List<ViajePaquete> viajes) {
        this.viajes = viajes;
    }

    

    @Override
    public String toString() {
        return "Paquete{" +
               "paqueteID=" + paqueteID +
               ", codigoPaquete=" + codigoPaquete +
               ", descripcion='" + descripcion + '\'' +
               '}';
    }
}
