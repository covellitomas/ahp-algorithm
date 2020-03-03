package MappedClasses;

import org.codehaus.jackson.annotate.JsonProperty;

public class RainMapped {

    private double cant = 0;

    @JsonProperty("3h")
    public double getCant() {
        return cant;
    }

}
