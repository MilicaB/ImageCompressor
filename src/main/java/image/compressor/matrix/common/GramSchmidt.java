package image.compressor.matrix.common;

import java.util.HashMap;
import java.util.Map;

public class GramSchmidt {
	private Map<Integer, double[]> unitVectors;
	private Matrix matrix;
	private Map<Pair, Double> dotProducts;

	public GramSchmidt(Matrix matrix) {
		this.setMatrix(matrix);
		unitVectors = new HashMap<Integer, double[]>();
		dotProducts = new HashMap<Pair, Double>();
		getUnitVectors();
	}

	public Matrix getMatrix() {
		return matrix;
	}

	public void setMatrix(Matrix matrix) {
		this.matrix = matrix;
	}

	private void getUnitVectors() {
		// TODO Auto-generated method stub

	}

	public double getDotProduct(int eNum, int aNum) {
		return dotProducts.get(new Pair(eNum, aNum));
	}

	private void evalDotProduct(int eNum, int aNum) {
		double prod = 0;
		for (int i = 0; i < getMatrix().getRows(); i++) {
			prod += unitVectors.get(eNum)[i] * getMatrix().getCol(aNum)[i];
		}

		dotProducts.put(new Pair(eNum, aNum), prod);
	}

	public double[] getUnitVector(int unitVectorNum) {
		double[] vector = null;
		return vector;
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
