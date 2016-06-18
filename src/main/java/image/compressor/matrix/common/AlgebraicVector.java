package image.compressor.matrix.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.lang.ArrayUtils;

/**
 *
 * @author Militsa
 *
 *         Sample algebraic vector class
 */
public class AlgebraicVector {
    private ArrayList<Double> vector;

    public AlgebraicVector() {
        vector = new ArrayList<Double>();
    }

    public AlgebraicVector(int size) {
        double[] array = new double[size];
        Arrays.fill(array, 0);
        createArrayListFromArray(array);
    }

    private void createArrayListFromArray(double[] array) {
        this.vector = new ArrayList<Double>(Arrays.asList(ArrayUtils.toObject(array)));
    }

    public AlgebraicVector(double[] v1) {
        createArrayListFromArray(v1);
    }

    public AlgebraicVector(AlgebraicVector algebraicVector) {
        vector = new ArrayList<Double>(algebraicVector.vector);
    }

    /**
     * Gets element from the vector
     * 
     * @param elementIndex
     * @return
     */
    public Double getElem(int elementIndex) {
        if (elementIndex >= vector.size()) {
            throw new IllegalArgumentException(String.format("No such element: %s!", elementIndex));
        }
        return vector.get(elementIndex);
    }

    /**
     * Sets element in the vector
     * 
     * @param elementIndex
     * @return
     */
    public Double setElem(int elementIndex, double element) {
        if (elementIndex > vector.size()) {
            throw new IllegalArgumentException(String.format("No such index: %s!", elementIndex));
        }
        return vector.set(elementIndex, element);
    }

    /**
     * Gets the number of elements in the vector
     * 
     * @return
     */
    public int getSize() {
        return vector.size();
    }

    /**
     * Gets the length of the vector
     * 
     * @return
     */
    public double length() {
        return Math.sqrt(dot(this));
    }

    /**
     * Adds element in the vector
     * 
     * @param elem
     */
    public void addElem(double elem) {
        vector.add(elem);
    }

    /**
     * Check if given element is part of the vector
     * 
     * @param elem
     * @return
     */
    public boolean contains(double elem) {
        return vector.contains(elem);
    }

    /**
     * Removes given element from the vector
     * 
     * @param elem
     */
    public void removeElement(double elem) {
        if (!vector.contains(elem)) {
            throw new IllegalArgumentException(String.format("No such element: %s in the vector!", elem));
        }
        vector.remove(elem);
    }

    /**
     * Calculates the dot product of current vector and another vector
     * 
     * @param other
     * @return - the value of the dot product
     */
    public double dot(AlgebraicVector other) {
        if (other.getSize() != vector.size()) {
            throw new IllegalArgumentException("Unable to calculate dot product of vectors with different dimentions!");
        }
        double result = 0;
        for (int i = 0; i < vector.size(); i++) {
            result += vector.get(i) * other.getElem(i);
        }
        return result;
    }

    /**
     * Calculates the sum of the current vector and another vector
     * 
     * @param other
     * @return - the result vector of the sum
     */
    public AlgebraicVector sum(AlgebraicVector other) {
        if (other.getSize() != vector.size()) {
            throw new IllegalArgumentException("Unable to calculate sum of vectors with different dimentions!");
        }
        AlgebraicVector result = new AlgebraicVector();
        for (int i = 0; i < vector.size(); i++) {
            result.addElem(vector.get(i) + other.getElem(i));
        }
        return result;
    }

    /**
     * Multiplies the current vector with number
     * 
     * @param number
     * @return - the result vector of the multiplication
     */
    public AlgebraicVector multiplyByNum(double number) {
        AlgebraicVector result = new AlgebraicVector();
        for (int i = 0; i < vector.size(); i++) {
            result.addElem(vector.get(i) * number);
        }
        return result;
    }

    /**
     * Subtracts vector from the current one
     * 
     * @param other
     * @return - the result vector of the subtraction
     */
    public AlgebraicVector subtract(AlgebraicVector other) {
        if (other.getSize() != vector.size()) {
            throw new IllegalArgumentException("Unable to substract vectors with different dimentions!");
        }
        AlgebraicVector result = new AlgebraicVector();
        for (int i = 0; i < vector.size(); i++) {
            result.addElem(vector.get(i) - other.getElem(i));
        }
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
        AlgebraicVector other = (AlgebraicVector) obj;
        return other.vector.equals(vector);
    }
    
    public void print(){
        System.out.print("[");
        for(int i=0;i<vector.size()-1;i++){
            System.out.print(vector.get(i)+",");
        }
        System.out.print(vector.get(vector.size()-1) + "]\n");
    }
}
