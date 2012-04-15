package matlube.jama

import Jama.{SingularValueDecomposition => JamaSingularValueDecomposition}
import matlube.{Matrix, SingularValueDecomposition}

/**
 *
 * @author Brian Schlining
 * @since 2012-04-06
 */

class JSingularValueDecomposition protected[jama] (val matrix: JMatrix)
        extends SingularValueDecomposition[JMatrix] {

    private[this] val svd = new JamaSingularValueDecomposition(matrix.delegate)

    def u: JMatrix = new JMatrix(svd.getU)

    def v: JMatrix = new JMatrix(svd.getV)

    def s: JMatrix = new JMatrix(svd.getS)

    def singularValues: Array[Double] = svd.getSingularValues
}
