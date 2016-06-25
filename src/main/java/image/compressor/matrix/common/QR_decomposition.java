/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package image.compressor.matrix.common;

import java.util.ArrayList;
import java.util.Arrays;
/**
 *
 * @author Gabi
 */
public class QR_decomposition {
    private ArrayList<Double> eigenValues;
    private VectorMatrix eigenVectors;
    
    public QR_decomposition(VectorMatrix symmetricRawPicMatrix){
        eigenValues = new ArrayList<Double>();
        GramSchmidt grShOb = new GramSchmidt(symmetricRawPicMatrix);

        VectorMatrix B = new VectorMatrix(symmetricRawPicMatrix);
        VectorMatrix Q = new VectorMatrix(B);
        VectorMatrix R = new VectorMatrix(B);
        grShOb.setMatrix(R.multiply(Q));
        
        for(int i = 0; i < 2; i++){
            Q = getQMatrix(grShOb);
            R = getRMatrix(grShOb);
            grShOb.setMatrix(R.multiply(Q));
            B = B.multiply(Q);
        }
        
        int rows = R.getRows();
        for (int i = 0; i < rows; i++ ){
            eigenValues.add(R.getElement(i, i));
            
        }
        eigenVectors = B.transpose();
    }
    
    public ArrayList<Double> getEigenValues(){
        return eigenValues;
    }
    
    public VectorMatrix getEigenVectors(){
        return eigenVectors;
    }
    
    public VectorMatrix getQMatrix(GramSchmidt B) {
        int rows = B.getMatrix().getRows();
        
        VectorMatrix Q = new VectorMatrix(rows, rows);

        for (int i = 0; i < rows; i++) {
            B.getMatrix().getCol(i).print();
            AlgebraicVector vector = B.getUnitVector(i);
            vector.print();
            
            for (int j = 0; j < rows; j++) {
                Q.addElementToMatrix(i, j, vector.getElem(j));
            }
        }
        
        return Q;
    }

    
    /**
     * 
     * @param n
     * @return
     */
    public VectorMatrix getRMatrix(GramSchmidt B) {
        int length = B.getMatrix().getRows();
        VectorMatrix R = new VectorMatrix(length, length);

        for (int i = 0; i < length; i++) {

            for (int j = i; j < length; j++) {
                double num = B.getDotProduct(i, j);
                R.addElementToMatrix(i, j, num);
            }
        }

        return R;
    }
    
    
    public static void main(String[] args) {
//        VectorMatrix matrix = new VectorMatrix(3, 3);
//        matrix.addElementToMatrix(0, 0, 12);
//        matrix.addElementToMatrix(0, 1, -51);
//        matrix.addElementToMatrix(0, 2, 4);
//        matrix.addElementToMatrix(1, 0, 6);
//        matrix.addElementToMatrix(1, 1, 167);
//        matrix.addElementToMatrix(1, 2, -68);
//        matrix.addElementToMatrix(2, 0, -4);
//        matrix.addElementToMatrix(2, 1, 24);
//        matrix.addElementToMatrix(2, 2, -41);
//        matrix.print();
//        GramSchmidt gs = new GramSchmidt(matrix);
//        for (int i = 1; i <= 3; i++) {
//            System.out.println("Orthogonal vector: ");
//            gs.getOrtogonalVector(i).print();
//            System.out.println("Unit vector: ");
//            gs.getUnitVector(i).print();
//        }
//        for (int i = 1; i < 4; i++) {
//            System.out.println(String.format("Length of: %s: %s", i, gs.getUnitVector(i).length()));
//            for (int j = i + 1; j < 4; j++) {
//                System.out.println(String.format("Dot of: %s, %s: %s", i, j, (int) gs.getDotProduct(i, j)));
//            }
//        }
//        
        
        VectorMatrix matrix = new VectorMatrix(2, 3);
        matrix.addElementToMatrix(0, 0, 2);
        matrix.addElementToMatrix(0, 1, 1);
        matrix.addElementToMatrix(0, 2, 0);
        matrix.addElementToMatrix(1, 0, 0);
        matrix.addElementToMatrix(1, 1, 2);
        matrix.addElementToMatrix(1, 2, 1);
        
        matrix.print();
        
        QR_decomposition q = new QR_decomposition(matrix.multiply(matrix.transpose()));
        ArrayList<Double> eigVal = q.getEigenValues();
        int lenEigVal = eigVal.size();
        for (int i = 0; i < lenEigVal; i++){
            System.out.println(eigVal.get(i));
        }
        q.getEigenVectors().print();
    }

}
