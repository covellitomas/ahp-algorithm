package MappedClasses;

import org.codehaus.jackson.annotate.JsonProperty;

public class SnowMapped {

    private double cant;

    @JsonProperty("3h")
    public double getCant() {
        return cant;
    }
}
