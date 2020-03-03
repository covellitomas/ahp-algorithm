package AhpClasses;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

import static org.codehaus.jackson.annotate.JsonTypeInfo.As.PROPERTY;
import static org.codehaus.jackson.annotate.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value=Truck.class, name = "t"),
        @JsonSubTypes.Type(value=Car.class, name = "c")
})
public interface Vehicle {

    String getName();

}
