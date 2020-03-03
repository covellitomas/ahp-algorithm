package MappedClasses;

import java.util.List;

public class WeatherQueryMapped {

    private Integer cod;
    private double message;
    private double cnt;
    private List<ForecastQueryMapped> list;

    public CityMapped getCity() {
        return city;
    }

    private CityMapped city;

    public Integer getCod() {
        return cod;
    }

    public double getMessage() {
        return message;
    }

    public double getCnt() {
        return cnt;
    }

    public List<ForecastQueryMapped> getList() {
        return list;
    }
}
