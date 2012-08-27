package matlube.jama


import matlube.testframework.SVDTester
import matlube.Orientations
import org.junit.{Ignore, Test}

/**
 *
 * @author Brian Schlining
 * @since 2012-08-27
 */
class JSVDTest {

    @Test
    @Ignore
    def test() {
        val matrix = JMatrix(5, 5, SVDTester.ROW_ARRAY, Orientations.Row)
        SVDTester.run(matrix)
    }

}
