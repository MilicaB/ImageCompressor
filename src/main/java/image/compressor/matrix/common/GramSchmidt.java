package image.compressor.matrix.common;

import java.util.HashMap;
import java.util.Map;

public class GramSchmidt {
    private Map<Integer, double[]> unitVectors;
    private Matrix matrix;
    private Map<Pair, Double> dotProducts;
    private Map<Integer, double[]> ortogonalVectors;

    public GramSchmidt(Matrix matrix) {
        this.setMatrix(matrix);
        unitVectors = new HashMap<Integer, double[]>();
        dotProducts = new HashMap<Pair, Double>();
        ortogonalVectors = new HashMap<Integer, double[]>();
        getOrtogonalVectors();
        getUnitVectors();
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    private void getUnitVectors() {
        for (int i = 1; i <= matrix.getCols(); i++) {
            double[] vector = ortogonalVectors.get(i);
            double vectorLength = VectorUtils.getVectorLength(vector);
            double[] unitVector = VectorUtils.multiplyByNumber(vector, (1.0 / vectorLength));
            unitVectors.put(i, unitVector);
        }
    }

    public double getDotProduct(int eNum, int aNum) {
        Pair key = new Pair(eNum, aNum);
        if (!dotProducts.containsKey(key)) {
            evalDotProduct(eNum, aNum);
        }
        return dotProducts.get(key);
    }

    private void evalDotProduct(int eNum, int aNum) {
        double prod = 0;
        for (int i = 0; i < getMatrix().getRows(); i++) {
            prod += unitVectors.get(eNum)[i] * getMatrix().getCol(aNum)[i];
        }

        dotProducts.put(new Pair(eNum, aNum), prod);
    }

    public double[] getUnitVector(int unitVectorNum) {
        return unitVectors.get(unitVectorNum);
    }

    private void getOrtogonalVectors() {
        for (int i = 1; i <= matrix.getCols(); i++) {
            double[] vector = matrix.getCol(i-1);
            for (int j = 1; j < i; j++) {
                vector = VectorUtils.getSub(vector, getProjection(vector, j));
            }
            ortogonalVectors.put(i, vector);
        }
    }
    
    private double[] getProjection(double[] vector,int j){
        double projection = VectorUtils.getDotProduct(vector, ortogonalVectors.get(j))
                / VectorUtils.getDotProduct(ortogonalVectors.get(j), ortogonalVectors.get(j));
        return VectorUtils.multiplyByNumber(ortogonalVectors.get(j), projection);
        
    }

    private class Pair {
        private int first;
        private int second;

        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }

        private GramSchmidt getOuterType() {
            return GramSchmidt.this;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + first;
            result = prime * result + second;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Pair other = (Pair) obj;
            if (!getOuterType().equals(other.getOuterType()))
                return false;
            if (first != other.first)
                return false;
            if (second != other.second)
                return false;
            return true;
        }
    }

    public static void main(String[] args) {
        Matrix matrix = new Matrix(2,2);
        matrix.addElementToMatrix(0, 0, 3);
        matrix.addElementToMatrix(0, 1, 2);
        matrix.addElementToMatrix(1, 0, 1);
        matrix.addElementToMatrix(1, 1, 2);
        GramSchmidt gs = new GramSchmidt(matrix);
        double[] unitVector1 = gs.getUnitVector(1);
        double[] unitVector2 = gs.getUnitVector(2);
        System.out.println((int)VectorUtils.getDotProduct(unitVector1, unitVector2));
    }
}
