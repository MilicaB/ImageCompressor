package image.compressor.matrix.common;

import java.util.Random;

/**
 * Class representing two dimensional matrix with numbers
 */
public class Matrix {
	private double[][] matrix;

	public Matrix(int rows, int cols) {
		matrix = new double[rows][cols];
	}

	public Matrix() {
		this(0, 0);
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
		if (rowPos < matrix.length && colPos < matrix[0].length) {
			matrix[rowPos][colPos] = elem;
		} else {
			throw new IllegalArgumentException(
					String.format("Unable to add element %s to the matrix on position (%s;%s). No such position!", elem,
							rowPos, colPos));
		}
	}

	/**
	 * Fills the matrix with random double numbers
	 */
	public void addRandomNumbers() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] = new Random().nextDouble();
			}
		}
	}

	/**
	 * Gets the element on the given position
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public double getElemenet(int row, int col) {
		if (matrix.length > row && matrix[0].length > col) {
			return matrix[row][col];
		} else {
			throw new IllegalArgumentException(String.format("No such position in the matrix: (%s;%s)", row, col));
		}
	}

	/**
	 * Checks if the matrix is empty
	 *
	 * @return
	 */
	public boolean isEmpty() {
		return matrix.length == 0;
	}

	/**
	 * Gets the number of rows
	 *
	 * @return
	 */
	public int getRows() {
		return matrix.length;
	}

	/**
	 * Gets the number of columns
	 *
	 * @return
	 */
	public int getCols() {
		return isEmpty() ? 0 : matrix[0].length;
	}

	/**
	 * Transposes the current matrix
	 *
	 * @return - the transposed matrix
	 */
	public Matrix transpose() {
		Matrix result;
		if (!isEmpty()) {
			result = new Matrix(matrix.length, matrix[0].length);
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[0].length; j++) {
					result.addElementToMatrix(j, i, matrix[i][j]);
				}
			}
		} else {
			result = new Matrix();
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
	public Matrix add(Matrix m) {
		Matrix result;
		if (getCols() != m.getCols() || getRows() != m.getRows()) {
			result = null;
		} else {
			result = new Matrix(getCols(), getRows());
			for (int i = 0; i < getCols(); i++) {
				for (int j = 0; j < getRows(); j++) {
					result.addElementToMatrix(i, j, matrix[i][j] + m.getElemenet(i, j));
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
	public Matrix subtract(Matrix m) {
		Matrix result;
		if (getCols() != m.getCols() || getRows() != m.getRows()) {
			result = null;
		} else {
			result = new Matrix(getCols(), getRows());
			for (int i = 0; i < getCols(); i++) {
				for (int j = 0; j < getRows(); j++) {
					result.addElementToMatrix(i, j, matrix[i][j] - m.getElemenet(i, j));
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
	public Matrix multiply(Matrix m) {
		Matrix result;
		if (m.getRows() != getCols()) {
			result = null;
		} else {
			result = new Matrix(getRows(), m.getCols());
			for (int i = 0; i < getRows(); i++)
				for (int j = 0; j < m.getCols(); j++)
					for (int k = 0; k < getCols(); k++)
						result.addElementToMatrix(i, i, matrix[i][k] * m.getElemenet(k, j));
		}
		return result;
	}

	/**
	 * Prints the matrix on the console
	 */
	public void print() {
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getCols(); j++) {
				System.out.print(matrix[i][j] + "\t");
			}
			System.out.println();
		}
	}
}
