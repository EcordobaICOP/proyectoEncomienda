/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logistica.entidad;
 import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author ULTRA
 */
   
@Entity
@Table(name = "Cliente")
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "clienteID")
    private Integer clienteID;
    
    @Column(name = "apellido")
    private String apellido;
    
    @Column(name = "correoElectronico")
    private String correoElectronico;
    
    @Column(name = "dirección")
    private Integer dirección;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "nro_documento")
    private Integer nroDocumento;
    
    @Column(name = "nro_teléfono")
    private Integer nroTeléfono;

    // Constructor, getters y setters

    public Cliente() {
    }

    public Cliente(Integer clienteID, String apellido, String correoElectronico, Integer dirección, String nombre, Integer nroDocumento, Integer nroTeléfono) {
        this.clienteID = clienteID;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.dirección = dirección;
        this.nombre = nombre;
        this.nroDocumento = nroDocumento;
        this.nroTeléfono = nroTeléfono;
    }

    public Integer getClienteID() {
        return clienteID;
    }

    public void setClienteID(Integer clienteID) {
        this.clienteID = clienteID;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public Integer getDirección() {
        return dirección;
    }

    public void setDirección(Integer dirección) {
        this.dirección = dirección;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(Integer nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public Integer getNroTeléfono() {
        return nroTeléfono;
    }

    public void setNroTeléfono(Integer nroTeléfono) {
        this.nroTeléfono = nroTeléfono;
    }
    
    @Override
    public String toString() {
    return "Cliente{" +
           "clienteID=" + clienteID +
           ", apellido='" + apellido + '\'' +
           ", correoElectronico='" + correoElectronico + '\'' +
           ", dirección=" + dirección +
           ", nombre='" + nombre + '\'' +
           ", nroDocumento=" + nroDocumento +
           ", nroTeléfono=" + nroTeléfono +
           '}';
}
}

