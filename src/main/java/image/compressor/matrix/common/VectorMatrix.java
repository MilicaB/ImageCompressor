package image.compressor.matrix.common;

import java.util.ArrayList;

public class VectorMatrix {
    private ArrayList<AlgebraicVector> matrix;

    public VectorMatrix(int rows, int cols) {
        matrix = new ArrayList<AlgebraicVector>();
        for (int i = 0; i < cols; i++) {
            AlgebraicVector algebraicVector = new AlgebraicVector(rows);
            matrix.add(algebraicVector);
        }
    }

    public VectorMatrix(double matrix[][]) {
        this.matrix = new ArrayList<AlgebraicVector>();
        for (int i = 0; i < matrix.length; i++) {
            AlgebraicVector algebraicVector = new AlgebraicVector(matrix[i]);
            this.matrix.add(algebraicVector);
        }

    }

    public VectorMatrix(AlgebraicVector... algebraicVectors) {
        if (!checkVectorsSize(algebraicVectors)) {
            throw new IllegalArgumentException("The size of the vectors should be equal!");
        }
        createMatrixFromArray(algebraicVectors);
    }

    public VectorMatrix(VectorMatrix copy) {
        this(copy.getRows(), copy.getCols());
        int rows = this.getRows();
        int cols = this.getCols();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.addElementToMatrix(i, j, copy.getElement(i, j));
            }
        }
    }

    private void createMatrixFromArray(AlgebraicVector... algebraicVectors) {
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
                for (int j = 0; j < m.getCols(); j++) {
                    double elem = 0;
                    for (int k = 0; k < getCols(); k++) {
                        elem += getElement(i, k) * m.getElement(k, j);
                    }
                    result.addElementToMatrix(i, j, elem);
                }
        }
        return result;
    }

    /**
     * Prints the matrix on the console
     */
    public void print() {
        System.out.println("[");
        for (int i = 0; i < matrix.size() - 1; i++) {
            matrix.get(i).print();
            System.out.print(",\n");
        }
        matrix.get(matrix.size() - 1).print();
        System.out.println("]");
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((matrix == null) ? 0 : matrix.hashCode());
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
        VectorMatrix other = (VectorMatrix) obj;
        return matrix.equals(other.matrix);
    }
}
