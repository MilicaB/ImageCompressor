package utils;
import static org.junit.Assert.*;

import org.junit.Test;

import image.compressor.matrix.common.AlgebraicVector;

public class AlgebraicVectorTest {
    private static final double EPS = 0.00001;
    AlgebraicVector vector = new AlgebraicVector(new double[] { 1.0, 2.0, 3.0 });

    @Test
    public void testGetElementPos() {
        double element = vector.getElem(0);
        assertEquals(element, 1.0, 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIGetElemNeg() {
        double element = vector.getElem(vector.getSize());
        assertEquals(element, 1.0, EPS);
    }

    @Test
    public void testAddElement() {
        int size = vector.getSize();
        double element = 4.0;
        vector.addElem(element);
        assertEquals(element, vector.getElem(size), EPS);
        vector.removeElement(element);
    }

    @Test
    public void testRemoveElement() {
        double element = 4.0;
        vector.addElem(element);
        int size = vector.getSize();
        assertEquals(element, vector.getElem(size - 1), EPS);
        vector.removeElement(element);
        assertFalse(vector.contains(element));
        assertEquals(size - 1, vector.getSize());
    }

    @Test
    public void testDot() {
        AlgebraicVector vectorNew = new AlgebraicVector(new double[] { 1.0, 2.0, 1.0 });
        assertEquals(8.0, vector.dot(vectorNew), EPS);
    }

    @Test
    public void testLength() {
        assertEquals(Math.sqrt(14), vector.length(), EPS);
    }
}