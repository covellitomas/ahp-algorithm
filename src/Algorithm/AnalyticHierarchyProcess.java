package Algorithm;

import AhpClasses.*;
import MappedClasses.ForecastQueryMapped;
import MappedClasses.MatrizMapped;
import MappedClasses.WeatherMapped;
import MappedClasses.WeatherQueryMapped;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.*;

public class AnalyticHierarchyProcess {

    private static final int TEMPERATURA = 0;
    private static final int HUMEDAD = 1;
    private static final int DIAS_SOLEADOS = 2;
    private static final int NIEVE = 3;
    private static final int LLUVIA = 4;
    private static final int NUBES = 5;
    private static final int VIENTO = 6;

    // Matriz de comparacion de criterios
    private AHPMatrix matrizComparacionCriterios = null;

    private static final String JSON = "{ \"data\":" +
            "[\"1\", \"7\", \"5\", \"1/3\"," +
            " \"1/7\", \"1\", \"1/3\", \"1/9\"," +
            " \"1/5\", \"3\", \"1\", \"1/7\"," +
            " \"3\", \"9\", \"7\", \"1\"] }";

    /** Escala de comparaciones:
     * 1 = igualmente importante.
     * 3 = moderadamente más importante.
     * 5 = altamente más importante.
     * 7 = fuertemente más importante.
     * 9 = extremadamente más importante. **/

    public static void prueba() {
        try {
            Document d = Jsoup.connect("https://www.tripadvisor.com/Attractions-g312848-Activities-c61-San_Carlos_de_Bariloche_Province_of_Rio_Negro_Patagonia.html").timeout(6000).get();
            //File file = new File("/Users/tomas/Downloads/Investigacion Operativa/hoteles-el-congo.html");
            //Document d = Jsoup.parse(file, "utf-8");

            System.out.println(d.outerHtml());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws IOException {

        String s1 = "A";
        String s2 = s1;
        s1 = s1 + " B";
        System.out.println(s1);
        System.out.println(s2);

        /*String JSON = "{ \"data\":" +
                "[\"1\", \"7\", \"5\", \"1/3\"," +
                " \"1/7\", \"1\", \"1/3\", \"1/9\"," +
                " \"1/5\", \"3\", \"1\", \"1/7\"," +
                " \"3\", \"9\", \"7\", \"1\"] }";

        ObjectMapper jackson = new ObjectMapper();

        MatrizMapped list = jackson.readValue(JSON, MatrizMapped.class);

        //AnalyticHierarchyProcess.prueba();

        /*
        AnalyticHierarchyProcess ahp = new AnalyticHierarchyProcess();

        System.out.println("JSON Criterios: \n" + JSON + "\n");
        AHPMatrix arregloPonderacionCriterios = ahp.getArregloPonderacionCriterios(JSON);

        String firstJsonWeather = ahp.getJsonWeather("/Users/tomas/Downloads/Investigacion Operativa/Clima/ejemploBusqueda.txt");
        System.out.println("JSON Alternativa Clima: \n" + firstJsonWeather + "\n");

        String secondJsonWeather = ahp.getJsonWeather("/Users/tomas/Downloads/Investigacion Operativa/Clima/ejemploBusquedaBaires.txt");
        System.out.println("JSON Alternativa Clima: \n" + secondJsonWeather + "\n");

        List<AHPWeather> listaAlternativas = new ArrayList<>();

        AHPWeather firstClima = ahp.getAlternativaClima(firstJsonWeather);
        System.out.println("Clima parseado: \n" + firstClima);
        listaAlternativas.add(firstClima);

        AHPWeather secondClima = ahp.getAlternativaClima(secondJsonWeather);
        System.out.println("Clima parseado: \n" + secondClima);
        listaAlternativas.add(secondClima);

        boolean quiereCalor = true;
        // Si, quiere nieve, asumimos que quiere frio.
        if (arregloPonderacionCriterios.getMejorAlternativa() == NIEVE)
            quiereCalor = false;

        List<AHPMatrix> listaAlternativasCriterios = new ArrayList<>();
        // Genero una matriz de pesos de alternativas, para cada criterio.
        for (int i=0; i<arregloPonderacionCriterios.getRowDimension(); i++) {
            listaAlternativasCriterios.add(ahp.creaMatrizAlternativa(i, quiereCalor, listaAlternativas));
        }

        AHPMatrix result = ahp.resolveAlgorithm(arregloPonderacionCriterios, listaAlternativasCriterios);

        System.out.println("La mejor alternativa es la: " + (result.getMejorAlternativa()+1));
        */
    }

    public AHPMatrix creaMatrizAlternativa(int criterio, boolean quiereCalor, List<AHPWeather> listaAlternativas) {
        int cantidadAlternativas = listaAlternativas.size();
        AHPMatrix result = new AHPMatrix(cantidadAlternativas, cantidadAlternativas);
        switch (criterio) {
            case TEMPERATURA:
                for (int i=0; i<cantidadAlternativas; i++)
                    for (int j=0; j<cantidadAlternativas; j++) {
                        double temp1 = listaAlternativas.get(i).getTemp();
                        double temp2 = listaAlternativas.get(j).getTemp();
                        if (temp1 > temp2)
                            result.set(i, j, getPeso(temp1, temp2, quiereCalor, TEMPERATURA));
                        else
                            result.set(i, j, (double)1/getPeso(temp2, temp1, quiereCalor, TEMPERATURA));
                    }
                break;
            case HUMEDAD:
                for (int i=0; i<cantidadAlternativas; i++)
                    for (int j=0; j<cantidadAlternativas; j++) {
                        double hum1 = listaAlternativas.get(i).getHumidity();
                        double hum2 = listaAlternativas.get(j).getHumidity();
                        if (hum1 > hum2)
                            result.set(i, j, getPeso(hum1, hum2, true, HUMEDAD));
                        else
                            result.set(i, j, (double)1/getPeso(hum2, hum1, true, HUMEDAD));
                    }
                break;
            case DIAS_SOLEADOS:
                for (int i=0; i<cantidadAlternativas; i++)
                    for (int j=0; j<cantidadAlternativas; j++) {
                        double dia1 = listaAlternativas.get(i).getCountSunnyDays();
                        double dia2 = listaAlternativas.get(j).getCountSunnyDays();
                        if (dia1 > dia2)
                            result.set(i, j, getPeso(dia1, dia2, true, DIAS_SOLEADOS));
                        else
                            result.set(i, j, (double)1/getPeso(dia2, dia1, true, DIAS_SOLEADOS));
                    }
                break;
            case LLUVIA:
                for (int i=0; i<cantidadAlternativas; i++)
                    for (int j=0; j<cantidadAlternativas; j++) {
                        double ll1 = listaAlternativas.get(i).getRain();
                        double ll2 = listaAlternativas.get(j).getRain();
                        if (ll1 > ll2)
                            result.set(i, j, getPeso(ll1, ll2, true, LLUVIA));
                        else
                            result.set(i, j, (double)1/getPeso(ll2, ll1, true, LLUVIA));
                    }
                break;
            case NUBES:
                for (int i=0; i<cantidadAlternativas; i++)
                    for (int j=0; j<cantidadAlternativas; j++) {
                        double n1 = listaAlternativas.get(i).getClouds();
                        double n2 = listaAlternativas.get(j).getClouds();
                        if (n1 > n2)
                            result.set(i, j, getPeso(n1, n2, true, NUBES));
                        else
                            result.set(i, j, (double)1/getPeso(n2, n1, true, NUBES));
                    }
                break;
            case NIEVE:
                for (int i=0; i<cantidadAlternativas; i++)
                    for (int j=0; j<cantidadAlternativas; j++) {
                        double snow1 = listaAlternativas.get(i).getCantSnow();
                        double snow2 = listaAlternativas.get(j).getCantSnow();
                        if (snow1 > snow2)
                            result.set(i, j, getPeso(snow1, snow2, quiereCalor, NIEVE));
                        else
                            result.set(i, j, (double)1/getPeso(snow2, snow1, quiereCalor, NIEVE));
                    }
                break;
            case VIENTO:
                for (int i=0; i<cantidadAlternativas; i++)
                    for (int j=0; j<cantidadAlternativas; j++) {
                        double v1 = listaAlternativas.get(i).getWindSpeed();
                        double v2 = listaAlternativas.get(j).getWindSpeed();
                        if (v1 > v2)
                            result.set(i, j, getPeso(v1, v2, true, VIENTO));
                        else
                            result.set(i, j, (double)1/getPeso(v2, v1, true, VIENTO));
                    }
                break;
        }
        return result;
    }

    public String getJsonWeather(String path) {
        File file = new File(path);
        BufferedReader reader = null;

        String text = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            text = reader.readLine();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    public AHPWeather getAlternativaClima(String jsonWeather) {
        WeatherQueryMapped wq = null;
        try {
            wq = new ObjectMapper().readValue(jsonWeather, WeatherQueryMapped.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return getSumaTotalCriterios(wq);
    }

    // Calculamos la suma total de cada criterio, para esta alternativa.
    public AHPWeather getSumaTotalCriterios(WeatherQueryMapped wq) {
        AHPWeather clima = new AHPWeather();
        clima.setCityName(wq.getCity().getName());
        double sumaTemperatura = 0;
        double sumaHumedad = 0;
        double sumaLluvia = 0;
        double sumaDiasSoleados = 0;
        double sumaNieve = 0;
        double sumaNubes = 0;
        double sumaVelocidadViento = 0;

        for (ForecastQueryMapped w: wq.getList()) {
            sumaTemperatura += w.getMain().getTemp();
            sumaHumedad += w.getMain().getHumidity();
            sumaNieve += w.getSnow() != null ? w.getSnow().getCant() : 0;
            sumaLluvia += w.getRain() != null ? w.getRain().getCant() : 0;
            sumaNubes += w.getClouds().getAll();
            sumaVelocidadViento += w.getWind().getSpeed();

            for (WeatherMapped wm: w.getWeather()) {
                double id = wm.getId();
                // Explcacion en: https://openweathermap.org/weather-conditions
                if (id == 800 || id ==801)
                    sumaDiasSoleados++;
            }
        }

        clima.setTemp(sumaTemperatura);
        clima.setHumidity(sumaHumedad);
        clima.setCantSnow(sumaNieve);
        clima.setClouds(sumaNubes);
        clima.setRain(sumaLluvia);
        clima.setCountSunnyDays(sumaDiasSoleados);
        clima.setWindSpeed(sumaVelocidadViento);

        return clima;
    }


    /**
     * Para calcular el peso de las diferentes alternativas se calcula a traves de una tecnica de porcentajes.
     *      - Si los valores son iguales -> 1 en cada casilla.
     *      - Si el porcentaje de val2 respecto a val1 es entre 75 y 100 -> 1/3 en val2, y 3 en val1.
     *      - Si el porcentaje de val2 respecto a val1 es entre 50 y 75 -> 1/5 en val2, y 5 en val1.
     *      - Si el porcentaje de val2 respecto a val1 es entre 25 y 50 -> 1/7 en val2, y 7 en val1.
     *      - Si el porcentaje de val2 respecto a val1 es entre 0 y 25 -> 1/9 en val2, y 9 en val1.
     * Si quiereCalor es true, el peso retornado equivale a val1 y el inverso a val2. Sino, el caso contrario.
     * criterio nos indica con que criterio estamos trabajando.
     */
    public double getPeso(double val1, double val2, boolean quiereCalor, int criterio) {
        if (val1 == val2)
            return 1;

        // Calculo una regla de 3 simple.
        double porcentajeVal2RespectoVal1 = 100*val2/val1;

        double result = 0;

        if (porcentajeVal2RespectoVal1 >= 0 && porcentajeVal2RespectoVal1 < 25)
            result = 9;
        else if (porcentajeVal2RespectoVal1 >= 25 && porcentajeVal2RespectoVal1 < 50)
            result = 7;
        else if (porcentajeVal2RespectoVal1 >= 50 && porcentajeVal2RespectoVal1 < 75)
            result = 5;
        else
            result = 3;

        if ((quiereCalor) || (!quiereCalor && criterio == NIEVE))
            return result;
        else
            return (double)1/result;
    }

    /**
     *
     * @param jsonCriterios Json con la matriz de pesos de cada criterio
     * @return Retorna el arreglo ponderacion para los criterios establecidos.
     */
    public AHPMatrix getArregloPonderacionCriterios(String jsonCriterios) {

        MatrizMapped mp = null;
        try {
            mp = new ObjectMapper().readValue(jsonCriterios, MatrizMapped.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Matriz de comparacion de criterios
        matrizComparacionCriterios = mp.getData();
        System.out.println(matrizComparacionCriterios);

        AHPMatrix arregloPonderacionCriterios = matrizComparacionCriterios.getArregloPonderacion();
        return arregloPonderacionCriterios;
    }

    /** Resuelve el algoritmo AHP
     * @param arregloPonderacionCriterios Equivale al arreglo ponderacion de los criterios seteado por el usuario
     * @param listaMatrizAlternativas Lista con las matrices de alternativas para cada criterio
     * @return Retorna la matriz solucion AHP
     */
    public AHPMatrix resolveAlgorithm(AHPMatrix arregloPonderacionCriterios, List<AHPMatrix> listaMatrizAlternativas) {

        List<AHPMatrix> listaArregloPonderacion = new ArrayList<>();
        for (AHPMatrix m: listaMatrizAlternativas) {
            AHPMatrix arrPonderacion = m.getArregloPonderacion();
            listaArregloPonderacion.add(arrPonderacion);
        }
        AHPMatrix alternativesCriteriaMatrix = AHPMatrix.generarMatrizCriterioAlternativa(listaArregloPonderacion, arregloPonderacionCriterios.getRowDimension());
        // Multiplicamos la matriz de alternativas de acuerdo a cada criterio por el vector de criterios ponderados.
        AHPMatrix ahpResult = alternativesCriteriaMatrix.mult(arregloPonderacionCriterios);
        return ahpResult;

    }


}
