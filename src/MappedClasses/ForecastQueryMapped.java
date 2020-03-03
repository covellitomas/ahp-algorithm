package MappedClasses;

import java.util.List;

public class ForecastQueryMapped {

    private double dt;
    private MainQueryMapped main;
    private List<WeatherMapped> weather;
    private CloudMapped clouds;
    private WindMapped wind;
    private Object sys;
    private String dt_txt;
    private SnowMapped snow;
    private RainMapped rain;

    public RainMapped getRain() {
        return rain;
    }

    public double getDt() {
        return dt;
    }

    public MainQueryMapped getMain() {
        return main;
    }

    public List<WeatherMapped> getWeather() {
        return weather;
    }

    public CloudMapped getClouds() {
        return clouds;
    }

    public WindMapped getWind() {
        return wind;
    }

    public Object getSys() {
        return sys;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public SnowMapped getSnow() {
        return snow;
    }
}
