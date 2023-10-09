package logistica.entidad;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import logistica.entidad.Marca;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-10-09T16:41:42", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Vehiculo.class)
public class Vehiculo_ { 

    public static volatile SingularAttribute<Vehiculo, Marca> marca;
    public static volatile SingularAttribute<Vehiculo, Integer> vehiculoID;
    public static volatile SingularAttribute<Vehiculo, Integer> capacidadCarga;
    public static volatile SingularAttribute<Vehiculo, Integer> nroVehiculo;
    public static volatile SingularAttribute<Vehiculo, Integer> patente;

}