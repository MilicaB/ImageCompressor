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

    public VectorMatrix getQMatrix(GramSchmidt A) {
        int row = A.getMatrix().getRows();
        VectorMatrix Q = new VectorMatrix();

        for (int i = 0; i < row; i++) {
            AlgebraicVector vector = A.getUnitVector(i);

            for (int j = 0; j < vector.getSize(); j++) {
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
    public VectorMatrix getRMatrix(GramSchmidt A) {
        int length = A.getMatrix().getRows();
        VectorMatrix R = new VectorMatrix(length, length);

        for (int i = 0; i < length; i++) {

            for (int j = i; j < length; j++) {
                double num = A.getDotProduct(i, j);
                R.addElementToMatrix(i, j, num);
            }
        }

        return R;
    }

    public static void main(String[] args) {
        VectorMatrix matrix = new VectorMatrix(3, 3);
        matrix.addElementToMatrix(0, 0, 12);
        matrix.addElementToMatrix(0, 1, -51);
        matrix.addElementToMatrix(0, 2, 4);
        matrix.addElementToMatrix(1, 0, 6);
        matrix.addElementToMatrix(1, 1, 167);
        matrix.addElementToMatrix(1, 2, -68);
        matrix.addElementToMatrix(2, 0, -4);
        matrix.addElementToMatrix(2, 1, 24);
        matrix.addElementToMatrix(2, 2, -41);
        matrix.print();
        GramSchmidt gs = new GramSchmidt(matrix);
        for (int i = 1; i <= 3; i++) {
            System.out.println("Orthogonal vector: ");
            gs.getOrtogonalVector(i).print();
            System.out.println("Unit vector: ");
            gs.getUnitVector(i).print();
        }
        for (int i = 1; i < 4; i++) {
            System.out.println(String.format("Length of: %s: %s", i, gs.getUnitVector(i).length()));
            for (int j = i + 1; j < 4; j++) {
                System.out.println(String.format("Dot of: %s, %s: %s", i, j, (int) gs.getDotProduct(i, j)));
            }
        }
        
        
    }

}
