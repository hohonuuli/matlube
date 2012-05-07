package matlube.jama

import matlube.test.MatrixTester
import org.junit.Test

/**
 *
 * @author Brian Schlining
 * @since 2012-05-04
 */

class JMatrixTest {

    @Test
    def test() {
        val matrix = JMatrix(2, 2, Array(1, 2, 3, 4))
        MatrixTester.run(matrix)
    }

}
