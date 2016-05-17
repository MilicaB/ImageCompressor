/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package image.compressor.matrix.common;

import Jama.Matrix;
import Jama.EigenvalueDecomposition;

import java.lang.*;
import java.util.LinkedList;
import java.util.List;

import pitt.search.semanticvectors.vectors.Vector;

/**
 *
 * @author Gabi
 */
public class SVD {
<<<<<<< HEAD
    
     public static double normCol(double [] a ){
            double sum = 0;
            for( int i = 0; i < a.length; i++){
                double num = a[i];
                sum += num*num;
            }
            return Math.sqrt(sum);
        }
        
        /**
         * From eigen values to singular
         * @param eigen array with eigen values
         * @param savedEigenValues the number of searched singular values
         * @return array with singular values
         */
        public static double [] singularValues( double [] eigen, int savedEigenValues){
            double [] singular = new double [ savedEigenValues];
            
            for ( int i = 0; i < savedEigenValues; i++ ){
//                singular[i] = Math.sqrt(eigen[eigen.length -1 - i]);
                singular[i] = Math.sqrt(eigen[eigen.length - savedEigenValues + i]);
                System.out.println(singular[i]);

            }
            
            return singular;
        }
        
        
        /**
         * Compression of Matrix with SVD algorithm
         * 
         * @param rawPicMatrix
         * @param percent
         * @return 
         */
        public static Matrix SVD( Matrix rawPicMatrix, int percent){
            
            Matrix A = rawPicMatrix; 
            int rowCount = rawPicMatrix.getRowDimension();
            int colCount = rawPicMatrix.getColumnDimension();
            Matrix symmetricRawPicMatrix = rawPicMatrix.transpose().times(rawPicMatrix);
            
            EigenvalueDecomposition a = symmetricRawPicMatrix.eig();
            double [] eigenValues= a.getRealEigenvalues();
          
            
            int nonzeroVal = 0;
            for ( int i = 0; i < colCount; i++){
                if ( eigenValues[i] > 0){ 
                    nonzeroVal++;
                }
            }
            
            int saved =(int)(nonzeroVal * ( 1.0- percent/100.0));
//            int saved=nonzeroVal;
            double [] singular = singularValues(eigenValues, saved);
            
            //
            //
            //problems??
            //   ||
            //   ||
            //  \  /
            //   \/
            
            Matrix eigenVectors = a.getV();
            
            
            Matrix U = new  Matrix(rowCount, saved);
            Matrix temp = A.times(eigenVectors);//eigenVectors.transpose()
            int tempRow = temp.getRowDimension();
            int tempCol = temp.getColumnDimension();
            for(int i = 0; i < rowCount; i++){
                for(int j = 0; j < saved; j++){
                    U.set(i, j, temp.get(i, tempCol - saved+j)/singular[j]);
                }
            }
            
            Matrix singularMatrix = new Matrix(saved, saved);
            for(int i = 0; i < saved; i++){
                singularMatrix.set(i, i, singular[i]);
            }
            
            int eigVCol = eigenVectors.getColumnDimension();
            int eigVRow = eigenVectors.getRowDimension();
            Matrix transposedEigV = eigenVectors.transpose();
            Matrix newEigenVectors = new Matrix(saved, eigVCol);
            for ( int i = 0; i < saved; i++){
                for ( int j = 0; j < eigVCol; j++){
                    newEigenVectors.set(i,j,transposedEigV.get(eigVRow - saved + i, j));
                }
            }
            
            Matrix result = U.times(singularMatrix).times(newEigenVectors);
            A.minus(result).print(20, 19);
            return result;
        }
    
=======

	public static double normCol(double[] a) {
		double sum = 0;
		for (int i = 0; i < a.length; i++) {
			double num = a[i];
			sum += num * num;
		}
		return Math.sqrt(sum);
	}

	/**
	 * From eigen values to singular
	 * 
	 * @param eigen
	 *            array with eigen values
	 * @param savedEigenValues
	 *            the number of searched singular values
	 * @return array with singular values
	 */
	public static double[] singularValues(double[] eigen, int savedEigenValues) {
		double[] singular = new double[savedEigenValues];
		for (int i = 0; i < savedEigenValues; i++) {
			singular[i] = Math.sqrt(eigen[eigen.length - 1 - i]);
		}

		return singular;
	}

	/**
	 * Compression of Matrix with SVD algorithm
	 * 
	 * @param rawPicMatrix
	 * @param percent
	 * @return
	 */
	public static Matrix SVD(Matrix rawPicMatrix, int percent) {

		Matrix A = rawPicMatrix;
		int rowCount = rawPicMatrix.getRowDimension();
		int colCount = rawPicMatrix.getColumnDimension();
		Matrix symmetricRawPicMatrix = rawPicMatrix.transpose().times(A);

		EigenvalueDecomposition a = symmetricRawPicMatrix.eig();
		double[] eigenValues = a.getRealEigenvalues();

		int nonzeroVal = 0;
		for (int i = 0; i < colCount; i++) {
			if (eigenValues[i] > 0) {
				nonzeroVal++;
			}
		}

		int saved = (int) (nonzeroVal);
		double[] singular = singularValues(eigenValues, saved);

		Matrix eigenVectors = switchCols(a.getV());
//		Matrix eigenVectors=a.getV();

		Matrix U = new Matrix(rowCount, saved);
		Matrix temp = A.times(eigenVectors);

		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < saved; j++) {
				U.set(i, j, temp.get(i, j) / singular[j]);
			}
		}

		Matrix singularMatrix = new Matrix(saved, colCount);
		int compressed = (int) (saved * (1.0 - percent / 100.0));
		for (int i = 0; i < compressed; i++) {
			System.out.println(singular[i]);
			singularMatrix.set(i, i, singular[i]);
		}

		Matrix times = U.times(singularMatrix);
		Matrix result = times.times(eigenVectors);
		return result;
	}

	private static Matrix switchCols(Matrix v) {
		int rows = v.getRowDimension();
		int cols = v.getColumnDimension();
		double[][] result = new double[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				result[i][j] = v.get(i, cols - j - 1);
			}
		}
		List<Vector> vectors = new LinkedList<Vector>();
		
		return new Matrix(result);
	}
>>>>>>> e01542f51b184f5a0dc2d108157ccf820888fc3f
}
