package image.compressor.matrix.common;

import java.util.ArrayList;
import java.util.Collections;

public class VectorMatrix {
    private ArrayList<AlgebraicVector> matrix;

    public VectorMatrix(int rows, int cols) {
        matrix = new ArrayList<AlgebraicVector>(Collections.nCopies(cols, new AlgebraicVector(rows)));
    }

    public VectorMatrix(AlgebraicVector... algebraicVectors) {
        if (!checkVectorsSize(algebraicVectors)) {
            throw new IllegalArgumentException("The size of the vectors should be equal!");
        }
        matrix = new ArrayList<AlgebraicVector>();
        for (AlgebraicVector algebraicVector : algebraicVectors) {
            matrix.add(algebraicVector);
        }
    }

    private boolean checkVectorsSize(AlgebraicVector[] algebraicVectors) {
        boolean allSizesMatch = true;
        int size = algebraicVectors[0].getSize();
        for (AlgebraicVector algebraicVector : algebraicVectors) {
            if (algebraicVector.getSize() != size) {
                allSizesMatch = false;
                break;
            }
        }
        return allSizesMatch;
    }

    /**
     * Adds new element to the matrix
     * 
     * @param rowPos
     *            - row where the new element will be added
     * @param colPos
     *            - column where the new element will be added
     * @param elem
     *            - element to be added
     */
    public void addElementToMatrix(int rowPos, int colPos, double elem) {
        if (rowPos < matrix.get(0).getSize() && colPos < matrix.size()) {
            matrix.get(colPos).setElem(rowPos, elem);
        } else {
            throw new IllegalArgumentException(
                    String.format("Unable to add element %s to the matrix on position (%s;%s). No such position!", elem,
                            rowPos, colPos));
        }
    }

    /**
     * Gets the element on the given position
     * 
     * @param row
     * @param col
     * @return
     */
    public double getElement(int row, int col) {
        if (row < matrix.get(0).getSize() && col < matrix.size()) {
            return matrix.get(col).getElem(row);
        } else {
            throw new IllegalArgumentException(String.format("No such position in the matrix: (%s;%s)", row, col));
        }
    }

    /**
     * Gets the number of rows
     *
     * @return
     */
    public int getRows() {
        return matrix.get(0).getSize();
    }

    /**
     * Gets the number of columns
     *
     * @return
     */
    public int getCols() {
        return matrix.size();
    }

    /**
     * Transposes the current matrix
     *
     * @return - the transposed matrix
     */
    public VectorMatrix transpose() {
        VectorMatrix result;
        int rows = this.getRows();
        int cols = this.getCols();
        result = new VectorMatrix(cols, rows);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                result.addElementToMatrix(col, row, getElement(row, col));
            }
        }
        return result;
    }

    /**
     * Adds matrix to the current one
     * 
     * @param m
     *            - matrix to add
     * @return - null - if the matrices are with different dimensions<br>
     *         - the resulting matrix otherwise
     */
    public VectorMatrix add(VectorMatrix m) {
        VectorMatrix result;
        int cols = getCols();
        int rows = getRows();
        if (cols != m.getCols() || rows != m.getRows()) {
            result = null;
        } else {
            result = new VectorMatrix(rows, cols);
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    result.addElementToMatrix(row, col, getElement(row, col) + m.getElement(row, col));
                }
            }
        }
        return result;
    }

    /**
     * Subtracts matrix from the current one
     * 
     * @param m
     *            - matrix to subtract
     * @return - null - if the matrices are with different dimensions<br>
     *         - the resulting matrix otherwise
     */
    public VectorMatrix subtract(VectorMatrix m) {
        VectorMatrix result;
        int cols = getCols();
        int rows = getRows();
        if (cols != m.getCols() || rows != m.getRows()) {
            result = null;
        } else {
            result = new VectorMatrix(rows, cols);
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    result.addElementToMatrix(row, col, getElement(row, col) - m.getElement(row, col));
                }
            }
        }
        return result;
    }

    /**
     * Multiplies matrix to the current one
     * 
     * @param m
     *            - matrix to multiply
     * @return - null - if the matrices are with different dimensions<br>
     *         - the resulting matrix otherwise
     */
    public VectorMatrix multiply(VectorMatrix m) {
        VectorMatrix result;
        if (m.getRows() != getCols()) {
            result = null;
        } else {
            result = new VectorMatrix(getRows(), m.getCols());
            for (int i = 0; i < getRows(); i++)
                for (int j = 0; j < m.getCols(); j++)
                    for (int k = 0; k < getCols(); k++)
                        result.addElementToMatrix(i, i, getElement(i, k) * m.getElement(k, j));
        }
        return result;
    }

    /**
     * Prints the matrix on the console
     */
    public void print() {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                System.out.print(getElement(i, j) + "\t");
            }
            System.out.println();
        }
    }

    /**
     * Gets single row from the matrix
     * 
     * @param rowNum
     * @return
     */
    public AlgebraicVector getRow(int rowNum) {
        AlgebraicVector row = new AlgebraicVector(matrix.size());
        for (int i = 0; i < getCols(); i++) {
            row.setElem(i, getElement(rowNum, i));
        }
        return row;
    }

    /**
     * Gets single column from the matrix
     * 
     * @param colNum
     * @return
     */
    public AlgebraicVector getCol(int colNum) {
        return new AlgebraicVector(matrix.get(colNum));
    }

}
