package matlube.ejml

import matlube.testframework.SVDTester
import matlube.Orientations
import org.junit.{Ignore, Test}

/**
 *
 * @author Brian Schlining
 * @since 2012-08-27
 */
class ESVDTest {

    @Test
    @Ignore
    def test() {
        val matrix = EMatrix(5, 5, SVDTester.ROW_ARRAY, Orientations.Row)
        SVDTester.run(matrix)
    }

}
