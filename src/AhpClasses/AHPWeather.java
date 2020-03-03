package AhpClasses;

public class AHPWeather {

    private String cityName = null;
    private double cantSnow;
    private double temp;
    private double humidity;
    private double countSunnyDays;
    private double rain;
    private double countSnowDays;
    private double countCloudyDays;
    private double clouds;
    private double windSpeed;

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public double getCountCloudyDays() {
        return countCloudyDays;
    }

    public void setCountCloudyDays(double countCloudyDays) {
        this.countCloudyDays = countCloudyDays;
    }

    @Override
    public String toString() {
        String result = "";
        result += "Ciudad: " + cityName + "\n";
        result += "Temperatura: " + temp + "\n";
        result += "Humedad: " + humidity + "\n";
        result += "Viento: " + windSpeed + "\n";
        result += "Dias soleados: " + countSunnyDays + "\n";
        result += "Lluvia: " + rain + "\n";
        result += "Nubes: " + clouds + "\n";
        result += "Nieve: " + cantSnow + "\n";
        return result;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getCantSnow() {
        return cantSnow;
    }

    public void setCantSnow(double cantSnow) {
        this.cantSnow = cantSnow;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getCountSunnyDays() {
        return countSunnyDays;
    }

    public void setCountSunnyDays(double countSunnyDays) {
        this.countSunnyDays = countSunnyDays;
    }

    public double getCountSnowDays() {
        return countSnowDays;
    }

    public void setCountSnowDays(double countSnowDays) {
        this.countSnowDays = countSnowDays;
    }

    public double getClouds() {
        return clouds;
    }

    public void setClouds(double clouds) {
        this.clouds = clouds;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

}
