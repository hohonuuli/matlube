package matlube.ejml

import org.junit.Test

import matlube.test.MatrixTester

/**
 *
 * @author Brian Schlining
 * @since 2012-08-27
 */
class EMatrixTest {

    @Test
       def test() {
           val matrix = EMatrix(2, 2, Array(1, 2, 3, 4))
           MatrixTester.run(matrix)
       }

}
