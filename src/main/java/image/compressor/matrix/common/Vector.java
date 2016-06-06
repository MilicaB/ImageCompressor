package image.compressor.matrix.common;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;

public class Vector {
	private ArrayList<Double> vector;

	public Vector() {
		vector = new ArrayList<Double>();
	}

	public Vector(double[] v1) {
		this.vector = new ArrayList<Double>(Arrays.asList(ArrayUtils.toObject(v1)));
	}

	public Double getElem(int elementIndex) {
		if (elementIndex > vector.size()) {
			throw new IllegalArgumentException(String.format("No such element: %s!", elementIndex));
		}
		return vector.get(elementIndex);
	}

	public int getSize() {
		return vector.size();
	}

	public void addElem(double elem) {
		vector.add(elem);
	}

	public double getDotProduct(Vector other) {
		if (other.getSize() != vector.size()) {
			throw new IllegalArgumentException("Unable to calculate dot product of vectors with different dimentions!");
		}
		double result = 0;
		for (int i = 0; i < vector.size(); i++) {
			result += vector.get(i) * other.getElem(i);
		}
		return result;
	}

	public double getSum(Vector other) {
		if (other.getSize() != vector.size()) {
			throw new IllegalArgumentException("Unable to calculate sum of vectors with different dimentions!");
		}
		double result = 0;
		for (int i = 0; i < vector.size(); i++) {
			result += vector.get(i) + other.getElem(i);
		}
		return result;
	}

	public Vector muliplyByNumber(double number) {
		Vector result = new Vector();
		for (int i = 0; i < vector.size(); i++) {
			result.addElem(vector.get(i) * number);
		}
		return result;
	}
}
