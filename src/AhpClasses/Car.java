package AhpClasses;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import static java.util.Objects.requireNonNull;

public class Car implements Vehicle {

    private String name;

    @JsonCreator
    public Car(@JsonProperty("name") final String name) {
        this.name = requireNonNull(name);
    }

    @Override
    public String getName() {
        return name;
    }

}
