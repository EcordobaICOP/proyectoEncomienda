package logistica.entidad;

import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import logistica.entidad.Vehiculo;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-10-09T16:41:42", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(ParteDiario.class)
public class ParteDiario_ { 

    public static volatile SingularAttribute<ParteDiario, Integer> horas;
    public static volatile SingularAttribute<ParteDiario, Date> fecha;
    public static volatile SingularAttribute<ParteDiario, Integer> km;
    public static volatile SingularAttribute<ParteDiario, Integer> parteDiarioID;
    public static volatile SingularAttribute<ParteDiario, Vehiculo> vehiculo;

}