package AhpClasses;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import static java.util.Objects.requireNonNull;

public class Truck implements Vehicle {

    private String name;

    @JsonCreator
    public Truck(@JsonProperty("name") final String name) {
        this.name = requireNonNull(name);
    }

    @Override
    public String getName() {
        return name;
    }
}
