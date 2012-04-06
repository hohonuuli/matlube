package matlube.jama

import Jama.{SingularValueDecomposition => JamaSingularValueDecomposition}
import matlube.{Matrix, SingularValueDecomposition}

/**
 *
 * @author Brian Schlining
 * @since 2012-04-06
 */

class JSingularValueDecomposition (val matrix: JMatrix) extends SingularValueDecomposition {

    private[this] val svd = new JamaSingularValueDecomposition(matrix.delegate)

    def u: Matrix = new JMatrix(svd.getU)

    def v: Matrix = new JMatrix(svd.getV)

    def s: Matrix = new JMatrix(svd.getS)

    def singularValues: Array[Double] = svd.getSingularValues
}
