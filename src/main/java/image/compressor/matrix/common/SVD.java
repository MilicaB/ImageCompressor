/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package image.compressor.matrix.common;


import Jama.Matrix;
import Jama.EigenvalueDecomposition;
import java.lang.*;

/**
 *
 * @author Gabi
 */
public class SVD {
    
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
    
}
