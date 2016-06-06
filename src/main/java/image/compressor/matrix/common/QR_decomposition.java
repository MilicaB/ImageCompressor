/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package image.compressor.matrix.common;

/**
 *
 * @author Gabi
 */
public class QR_decomposition {
 
    public Matrix getQMatrix( GramSchmidt A){
        int row = A.getMatrix().getRows();
        Matrix Q = new Matrix();
        
        for(int i = 0; i < row; i++){
            double vector[] = A.getUnitVector(i);
            
            for(int j = 0; j < vector.length; j++){
                Q.addElementToMatrix(i, j, vector[j]);
            }
        }
        return Q;
    }
    
    /**
  * 
  * @param n
  * @return 
  */
    public Matrix getRMatrix( GramSchmidt A){
        
        int length = A.getMatrix().getRows();
        Matrix R =  new Matrix(length, length);
        
        for(int i = 0; i < length; i++){
            
            for (int j = i; j < length; j++){
                double num = A.getDotProduct(i, j);
                R.addElementToMatrix(i, j, num);
            }
        }
        
        return R;
    }
    
    
 
}
