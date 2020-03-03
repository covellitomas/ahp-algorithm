package AhpClasses;

import Jama.Matrix;

import java.util.List;

public class AHPMatrix extends Matrix {

    public AHPMatrix(int i, int i1) {
        super(i, i1);
    }

    public AHPMatrix(double[][] matrix) {
        super(matrix);
    }

    @Override
    public String toString() {
        String s = "";
        for (Integer i=0; i<this.getRowDimension(); i++) {
            for (Integer j = 0; j < this.getColumnDimension(); j++) {
                s = s + this.get(i, j) + "  ";
            }
            s = s + "\n";
        }
        return s;
    }

    /** El resultado nos devuelve un arreglo de tantas filas como dimension en las filas o columnas
     ** (ya que siempre es cuadrada) tenga la matriz que nos pasan por parametros. **/
    private AHPMatrix getArregloSumaColumnas() {
        AHPMatrix result = new AHPMatrix(this.getColumnDimension(),1);

        double suma = 0;
        for (int i=0; i<this.getRowDimension(); i++) {
            for (int j = 0; j < this.getColumnDimension(); j++) {
                suma = suma + this.get(j, i);
            }
            result.set(i, 0, suma);
            suma = 0;
        }

        return result;
    }

    /** Normaliza la matriz m de acuerdo al arregloSumaColumnas **/
    public AHPMatrix getMatrizNormalizada() {
        AHPMatrix arregloSumaColumnas = getArregloSumaColumnas();
        AHPMatrix result = new AHPMatrix(this.getColumnDimension(), this.getColumnDimension());

        for (int i=0; i<this.getRowDimension(); i++) {
            for (int j = 0; j < this.getColumnDimension(); j++) {
                result.set(i,j, this.get(i,j)/arregloSumaColumnas.get(j,0));
            }
        }

        return result;
    }

    /** Calcula el arreglo ponderacion de la matriz normalizada enviada por parametros **/
    public AHPMatrix getArregloPonderacion() {
        AHPMatrix matrizNormalizada = this.getMatrizNormalizada();
        AHPMatrix result = new AHPMatrix(matrizNormalizada.getColumnDimension(), 1);
        double suma = 0;
        for (int i=0; i<matrizNormalizada.getRowDimension(); i++) {
            for (int j = 0; j < matrizNormalizada.getColumnDimension(); j++) {
                suma = suma + matrizNormalizada.get(i, j);
            }
            result.set(i, 0, suma/matrizNormalizada.getRowDimension());
            suma = 0;
        }
        return result;
    }

    /** Genera la matriz con cada columna formada por el arreglo ponderación de cada alternativa
     * comparada a todas las otras, respecto a determinado criterio **/
    public static AHPMatrix generarMatrizCriterioAlternativa(List<AHPMatrix> listaArreglosPonderados, int colDimension) {
        // De filas tengo la cantidad de alternativas, y de columnas la cantidad de criterios.
        int rowDimension = listaArreglosPonderados.get(0).getRowDimension();
        AHPMatrix result = new AHPMatrix(rowDimension, colDimension);
        for (int i=0; i<result.getRowDimension(); i++)
            for (int j=0; j<result.getColumnDimension(); j++) {
                result.set(i, j, listaArreglosPonderados.get(j).get(i, 0));
            }
        return result;
    }

    public AHPMatrix mult(Matrix m) {
        Matrix matrizMultiplicacion = this.times(m);
        AHPMatrix result = new AHPMatrix(matrizMultiplicacion.getRowDimension(), matrizMultiplicacion.getColumnDimension());
        for(int i=0; i<result.getRowDimension(); i++) {
            for (int j = 0; j < result.getColumnDimension(); j++)
                result.set(i, j, matrizMultiplicacion.get(i, j));
        }
        return result;
    }

    public int getMejorAlternativa() {
        double mayor = 0;
        int mayorIndex = -1;
        for (int i=0; i<this.getRowDimension(); i++) {
            double elem = this.get(i, 0);
            if (elem > mayor) {
                mayor = elem;
                mayorIndex = i;
            }
        }
        // Retornamos el indice más 1, porque el primer indice progrmando es el 0 (y no 1).
        return mayorIndex;
    }

    /*
    public void setMatrizComparacionCriterios(int fila, int columna, String preferencia) {
        switch (preferencia) {
            case "Igual":
                compareCriteriaMatrix.set(fila,columna,1);
                compareCriteriaMatrix.set(columna,fila,1);
                break;
            case "Moderada":
                compareCriteriaMatrix.set(fila,columna,3);
                compareCriteriaMatrix.set(columna,fila,1/3);
                break;
            case "Alta":
                compareCriteriaMatrix.set(fila,columna,5);
                compareCriteriaMatrix.set(columna,fila,1/5);
                break;
            case "Fuerte":
                compareCriteriaMatrix.set(fila,columna,7);
                compareCriteriaMatrix.set(columna,fila,1/7);
                break;
            case "Extrema":
                compareCriteriaMatrix.set(fila,columna,9);
                compareCriteriaMatrix.set(columna,fila,1/9);
                break;
        }
    }*/

}
