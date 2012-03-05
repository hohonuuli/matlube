package matlube.jama

import org.junit.{Assert, Test}
import org.junit.Assert._
import matlube.Matrix


/**
 *
 * @author Brian Schlining
 * @since 2012-03-04
 */

class MatrixFactoryTest {

    @Test
    def testCreateWithTuple {
        val m0 = JMatrix((1D, 2D, 3D))
        assertTrue(m0.rows == 1 && m0.columns == 3)
        val m1 = JMatrix(((1D, 2D, 3D), (4D, 5D, 6D)))
        assertTrue(m1.rows == 2 && m1.columns == 3)
        val m2 = JMatrix(((1D, 2D, 3D), (4D, 5D, 6D), (7D, 8D, 9D)))
        assertTrue(m2.rows == 3 && m2.columns == 3)
    }

    @Test
    def testCreateWithDefaultValue {
        val m0 = JMatrix(2, 3)
        assertTrue(m0.rows == 2 && m0.columns == 3 && checkAll (m0, 0D))
        val m1 = JMatrix(3, 6, 1D)
        assertTrue(m1.rows == 3 && m1.columns == 6 && checkAll (m1, 1D))
    }

    @Test
    def testCreateWithArray {
        val m0 = JMatrix(2, 3, Array(1D, 2D, 3D, 4D, 5D, 6D))
        assertTrue(m0.rows == 2 && m0.columns == 3)
    }
    
    def checkAll(matrix: Matrix, v: Double): Boolean = {
        var ok = true
        for (i <- 0 until matrix.rows; j <- 0 until matrix.columns) {
            if (ok) {
                ok = matrix(i, j) - v < 0.00001
            }
        }
        ok
    }
}
