package logistica.entidad;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import logistica.entidad.Paquete;
import logistica.entidad.Viaje;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-10-09T16:41:42", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(ViajePaquete.class)
public class ViajePaquete_ { 

    public static volatile SingularAttribute<ViajePaquete, Integer> viajePaqueteID;
    public static volatile SingularAttribute<ViajePaquete, Viaje> viaje;
    public static volatile SingularAttribute<ViajePaquete, Paquete> paquete;

}