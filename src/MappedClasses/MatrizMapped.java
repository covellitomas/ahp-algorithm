package MappedClasses;

import AhpClasses.AHPMatrix;

import java.util.*;

public class MatrizMapped {

    private Object data;

    public AHPMatrix getData() {
        //add the custom name here
        //use full HashMap if you need more than one property
        Map<String, Object> map = Collections.singletonMap("data", data);

        List<String> aux = (List<String>) map.get("data");
        int dimension = (int)Math.sqrt(aux.size());

        AHPMatrix result = new AHPMatrix(dimension, dimension);
        int auxIndex = 0;
        for (int i=0; i<dimension; i++) {
            for (int j = 0; j<dimension; j++) {
                result.set(i, j, getDouble(aux.get(auxIndex)));
                auxIndex++;
            }
        }

        return result;
    }

    private double getDouble(String number) {
        double num = 0;
        switch (number) {
            case "1/3":
                num = (double)1/3;
                break;
            case "1/5":
                num = (double)1/5;
                break;
            case "1/7":
                num = (double)1/7;
                break;
            case "1/9":
                num = (double)1/9;
                break;
            default:
                num = Double.parseDouble(number);
        }
        return num;
    }

}
