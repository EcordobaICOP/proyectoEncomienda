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
@Table(name = "Marca")
public class Marca implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "marcaID")
    private int marcaID;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "tipo")
    private String tipo;

    @OneToMany(mappedBy = "marca")
    private List<Vehiculo> vehiculos;

    // Constructores, getters y setters

    public Marca() {
    }

    public Marca(String modelo, String tipo) {
        this.modelo = modelo;
        this.tipo = tipo;
    }

    public int getMarcaID() {
        return marcaID;
    }

    public void setMarcaID(int marcaID) {
        this.marcaID = marcaID;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    

    @Override
    public String toString() {
        return "Marca{" +
               "marcaID=" + marcaID +
               ", modelo='" + modelo + '\'' +
               ", tipo='" + tipo + '\'' +
               '}';
    }
}
