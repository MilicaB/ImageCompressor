package image.compressor.matrix.common;


import Jama.QRDecomposition;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
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
        public static double [] singularValues( ArrayList<Double> eigen, int savedEigenValues){
            double [] singular = new double [ savedEigenValues];
            
            for ( int i = 0; i < savedEigenValues; i++ ){
                singular[i] = Math.sqrt(eigen.get(eigen.size() - savedEigenValues + i));
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
        public static VectorMatrix compress( VectorMatrix rawPicMatrix, int percent){
            
            VectorMatrix A = rawPicMatrix;
            int rowCount = rawPicMatrix.getRows();
            int colCount = rawPicMatrix.getCols();
            VectorMatrix symmetricRawPicMatrix;
            
            if(rowCount < colCount){
                symmetricRawPicMatrix = rawPicMatrix.transpose().multiply(rawPicMatrix);
            }
            else{
                symmetricRawPicMatrix = rawPicMatrix.multiply(rawPicMatrix.transpose());
            }

            
            //EigenvalueDecomposition a = symmetricRawPicMatrix.eig();
            //double [] eigenValues= a.getRealEigenvalues();
            QR_decomposition qr = new QR_decomposition(symmetricRawPicMatrix);
            
            ArrayList<Double> eigenValues = qr.getEigenValues();
 
            int nonzeroVal = 0;
            for ( int i = 0; i < colCount; i++){
                if ( eigenValues.get(i) > 0){ 
                    nonzeroVal++;
                }
            }
            
            int saved =(int)(nonzeroVal * ( 1.0- percent/100.0));
            double [] singular = singularValues(eigenValues, saved);
            
            VectorMatrix eigenVectors = qr.getEigenVectors();
           
            
            VectorMatrix U = new  VectorMatrix(rowCount, saved);
            VectorMatrix temp = A.multiply(eigenVectors);//eigenVectors.transpose()
            int tempCol = temp.getCols();
            for(int i = 0; i < rowCount; i++){
                for(int j = 0; j < saved; j++){
                    U.addElementToMatrix(i, j, temp.getElement(i, j)/singular[j]);
                }
            }
            
            VectorMatrix singularMatrix = new VectorMatrix(saved, saved);
            for(int i = 0; i < saved; i++){
                singularMatrix.addElementToMatrix(i, i, singular[i]);
            }
            
            int eigVCol = eigenVectors.getCols();
            int eigVRow = eigenVectors.getRows();
            VectorMatrix transposedEigV = eigenVectors.transpose();
            VectorMatrix newEigenVectors = new VectorMatrix(saved, eigVCol);
            for ( int i = 0; i < saved; i++){
                for ( int j = 0; j < eigVCol; j++){
                    newEigenVectors.addElementToMatrix(i,j,transposedEigV.getElement(i, j));
                }
            }
            
            VectorMatrix result = U.multiply(singularMatrix).multiply(newEigenVectors);
            return result;
        }
    
}
