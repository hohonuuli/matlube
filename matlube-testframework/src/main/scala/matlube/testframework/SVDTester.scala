package matlube.testframework

import math._
import matlube.{SingularValueDecomposition, Matrix}
import org.junit.Assert._

/**
 * This takes a standard matrix to run tests against:
 * {{{
 *     m = reshape(1:25, 5, 5)'
 *
 *     m =
 *
 *     1     2     3     4     5
 *     6     7     8     9    10
 *    11    12    13    14    15
 *    16    17    18    19    20
 *    21    22    23    24    25
 *
 * }}}
 * @author Brian Schlining
 * @since 2012-08-27
 */
class SVDTester[A <: Matrix[_]](matrix: A) {

    chkArray(matrix, SVDTester.ROW_ARRAY)

    private[this] val tolerance = 0.00000000001

    private[this] val svd = matrix.svd

    // Row oriented 5x5 matrix
    private[this] val expectedU = Array( -0.093593233935277, -0.768921521718267, 0.613863561088329, 0.014306954944307, -0.151548142212888,
        -0.243632030025941, -0.490554211015907, -0.519125936087998, -0.429402252692641, 0.496106810942205,
        -0.393670826116603, -0.212186900313547, -0.364921668301442,  0.812496468810620, -0.081741447102222,
        -0.543709622207266, 0.066180410388814, -0.168233099486434, -0.394013999320547, -0.718644969770620,
        -0.693748418297929, 0.344547721091174, 0.438417142787546, -0.003387171741739, 0.455827748143525)


    private[this] val expectedV = Array(-0.392622809058583, 0.667718001709514, 0.549637829880022, -0.312317352862528,  0.018871329198638,
        -0.419131181682098, 0.352603250895065, -0.515616563617710, 0.339198860036132, -0.564874935426641,
        -0.445639554305613, 0.037488500080615, -0.520550047366741, -0.234116203989522, 0.688634337813632,
        -0.472147926929128, -0.277626250733836, 0.389398466066527, 0.699905239320761, 0.241870813858107,
        -0.498656299552643, -0.592741001548285, 0.097130315037902, -0.492670542504843, -0.384501545443737)


    private[this] val expectedS = Array(74.254053937544626, 0, 0, 0, 0,
        0, 3.366819543755492, 0, 0, 0,
        0, 0, 0.000000000000003, 0, 0,
        0, 0, 0, 0.000000000000001, 0,
        0, 0, 0, 0,0.000000000000000)



    private def chk(a: Double, b: Double) {
        assertEquals(a, b, tolerance)
    }

    private def chkArray(a: A, b: Array[Double]) {
        val r = a.rowArray
        assertEquals("Arrays are different sizes", r.size, b.size)
        for (i <- 0 until r.size) {
            chk(b(i), r(i))
        }
    }

    protected def testU {
        chkArray(svd.u.asInstanceOf[A], expectedU)
    }

    protected def testV {
        chkArray(svd.v.asInstanceOf[A], expectedV)
    }


    protected def testS {
        chkArray(svd.s.asInstanceOf[A], expectedS)
    }

    protected def testSingularValues {

    }

}

object SVDTester {

    val ROW_ARRAY: Array[Double] = (1 to 25).map(_.toDouble).toArray

    def run[A <: Matrix[_]](matrix: A) {
        require(matrix.rows == 5)
        require(matrix.columns == 5)
        // TODO check that the matrix has the correct values
        val svdTester = new SVDTester(matrix)
        svdTester.testU
        svdTester.testV
        svdTester.testS
    }
}
