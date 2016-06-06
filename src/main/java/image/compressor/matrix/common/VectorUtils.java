package image.compressor.matrix.common;

public class VectorUtils {
    public static double getDotProduct(double[] v1, double[] v2) {
        if (v1.length != v2.length) {
            throw new IllegalArgumentException("Unable to calculate dot product of vectors with different dimentions!");
        }
        double result = 0;
        for (int i = 0; i < v1.length; i++) {
            result += v1[i] * v2[i];
        }
        return result;
    }

    public static double[] getSum(double[] v1, double[] v2) {
        if (v1.length != v2.length) {
            throw new IllegalArgumentException("Unable to calculate dot product of vectors with different dimentions!");
        }
        double[] result = new double[v1.length];
        for (int i = 0; i < v1.length; i++) {
            result[i] += v1[i] + v2[i];
        }
        return result;
    }
    
    public static double[] getSub(double[] v1, double[] v2) {
        if (v1.length != v2.length) {
            throw new IllegalArgumentException("Unable to calculate dot product of vectors with different dimentions!");
        }
        double[] result = new double[v1.length];
        for (int i = 0; i < v1.length; i++) {
            result[i] += v1[i] - v2[i];
        }
        return result;
    }

    public static double[] multiplyByNumber(double[] v1, double number) {
        double[] result = new double[v1.length];
        for (int i = 0; i < v1.length; i++) {
            result[i] += v1[i] * number;
        }
        return result;
    }

    public static double getVectorLength(double[] v1) {
        return Math.sqrt(getDotProduct(v1, v1));
    }
}
