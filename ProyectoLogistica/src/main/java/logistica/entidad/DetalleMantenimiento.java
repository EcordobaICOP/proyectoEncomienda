/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logistica.entidad;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "DetalleMantenimiento")
public class DetalleMantenimiento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detalleMantenimientoID")
    private int id;

    @Column(name = "tipoServicio")
    private String tipoServicio;

    // Puedes agregar más atributos y métodos getter y setter según sea necesario

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    // Puedes agregar más métodos y propiedades según sea necesario
}
