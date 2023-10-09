package logistica.entidad;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import logistica.entidad.Vehiculo;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-10-09T16:41:42", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Marca.class)
public class Marca_ { 

    public static volatile SingularAttribute<Marca, String> tipo;
    public static volatile SingularAttribute<Marca, Integer> marcaID;
    public static volatile SingularAttribute<Marca, String> modelo;
    public static volatile ListAttribute<Marca, Vehiculo> vehiculos;

}