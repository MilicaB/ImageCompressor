package image.compressor.matrix.common;

import java.util.HashMap;
import java.util.Map;


public class GramSchmidt {
    private Map<Integer, AlgebraicVector> unitVectors;
    private VectorMatrix matrix;
    private Map<Pair, Double> dotProducts;
    private Map<Integer, AlgebraicVector> ortogonalVectors;
    
/**
 * Constructs object of type GramSchmidt by VectorMatrix object
 * @param matrix 
 */
    public GramSchmidt(VectorMatrix matrix) {
        this.matrix = matrix;
        unitVectors = new HashMap<Integer, AlgebraicVector>();
        dotProducts = new HashMap<Pair, Double>();
        ortogonalVectors = new HashMap<Integer, AlgebraicVector>();
        getOrtogonalVectors();
        getUnitVectors();
    }

    
    public VectorMatrix getMatrix() {
        return matrix;
    }

    public void setMatrix(VectorMatrix matrix) {
        this.matrix = matrix;
    }

    private void getUnitVectors() {
        for (int i = 1; i <= matrix.getCols(); i++) {
            AlgebraicVector vector = ortogonalVectors.get(i);
            double vectorLength = vector.length();
            AlgebraicVector unitVector = vector.multiplyByNum((1.0 / vectorLength));
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
        dotProducts.put(new Pair(eNum, aNum), unitVectors.get(eNum).dot(unitVectors.get(aNum)));
    }

    public AlgebraicVector getUnitVector(int unitVectorNum) {
        return unitVectors.get(unitVectorNum);
    }

    private void getOrtogonalVectors() {
        for (int i = 1; i <= matrix.getCols(); i++) {
            AlgebraicVector vector = matrix.getCol(i - 1);
            for (int j = 1; j < i; j++) {
                vector = vector.subtract(getProjection(vector, j));
            }
            ortogonalVectors.put(i, vector);
        }
    }

    public AlgebraicVector getOrtogonalVector(int index) {
        return ortogonalVectors.get(index);
    }

    private AlgebraicVector getProjection(AlgebraicVector vector, int j) {
        double projection = vector.dot(ortogonalVectors.get(j)) / ortogonalVectors.get(j).dot(ortogonalVectors.get(j));
        return ortogonalVectors.get(j).multiplyByNum(projection);

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
}
