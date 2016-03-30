package image.compressor.matrix.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import Jama.Matrix;
import Jama.EigenvalueDecomposition;
import java.lang.*;

public class MatrixReader {
    
    /**
     * Function that takes directory of a picture with pgm format as a argument
     * and return the matrix of that pic
     * @param fileName - name of the Picture file that will be compressed
     * @return the matrix of the picture
     */
	public static Matrix readMatrixFromPgmFile(String fileName) {
		Matrix result;
		FileInputStream fileInputStream = null;
		Scanner scan = null;
		try {
			fileInputStream = new FileInputStream(fileName);
			scan = new Scanner(fileInputStream);
			// Discard the magic number
			scan.nextLine();
			// Discard the comment line
			scan.nextLine();
			// Read pic width, height and max value
			int picWidth = scan.nextInt();
			int picHeight = scan.nextInt();
			// Discard the max value
			// TODO militsa - is this max value needed??!!
			scan.nextInt();
			result = new Matrix(picHeight, picWidth);
			for (int i = 0; i < picHeight; i++) {
				for (int j = 0; j < picWidth; j++) {
					result.set(i, j, scan.nextInt());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			result = new Matrix(null);
		} finally {
			try {
				fileInputStream.close();
				scan.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
        
        
        public static double normCol(double [] a ){
            double sum = 0;
            for( int i = 0; i < a.length; i++){
                double num = a[i];
                sum += num*num;
            }
            return Math.sqrt(sum);
        }
        
        public static Matrix SVD(Matrix rawPicMatrix, int percent){
            
            int rowCount = rawPicMatrix.getRowDimension();
            int colCount = rawPicMatrix.getColumnDimension();
            Matrix symmetricRawPicMatrix = rawPicMatrix.transpose().times(rawPicMatrix);
            
            EigenvalueDecomposition a = symmetricRawPicMatrix.eig();
            double [] eigenValues= a.getRealEigenvalues();
//            for(int i = 0; i<eigenValues.length; i++){
//                System.out.println(eigenValues[i]);
//            }
            int nonzeroVal = 0;
            for ( int i = 0; i < colCount; i++){
                if ( eigenValues[i] != 0){
                    nonzeroVal++;
                }
            }
            int saved = nonzeroVal * ( 1- percent/100);
            Matrix eigenVectors = a.getV();

            double [] singular = new double [saved];
            for(int i = 0; i < saved; i++){
                singular[i] = Math.sqrt(eigenValues[i]);
            }
            
            
            Matrix transposedEigenVectors = eigenVectors.transpose();
            double [][] U = new double [rowCount][saved];
            Matrix temp = rawPicMatrix.times(eigenVectors);
            for(int i = 0; i < rowCount; i++){
                for(int j = 0; j < saved; j++){
                    U[i][j] = temp.get(i, j)/singular[i];
                }
            }
            Matrix u = new Matrix(U);
            
            u.print(5, 2);
            
            
            return symmetricRawPicMatrix;
        }

	public static void main(String[] args) {
		//sample usage
//		Matrix a = readMatrixFromPgmFile("E:\\Pictures\\baboon.ascii.pgm");
//                SVD(a, 85);
            double[][] A = {{1,2}, {2, 1}};
            Matrix a = new Matrix (A);
//            EigenvalueDecomposition b = a.eig();
//            Matrix eigenVectors = b.getV();
//            eigenVectors.print(4, 2);
            SVD(a, 0).print(5,2);
	}

}
