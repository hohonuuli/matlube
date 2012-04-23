package matlube.jama

import Jama.{SingularValueDecomposition => JamaSingularValueDecomposition}
import matlube.{HasDelegate}

/**
 *
 * @author Brian Schlining
 * @since 2012-04-06
 */

class JSingularValueDecomposition protected[jama] (val matrix: JMatrix)
        extends matlube.SingularValueDecomposition[JMatrix]
        with HasDelegate[JamaSingularValueDecomposition] {

    val delegate = new JamaSingularValueDecomposition(matrix.delegate)

    def u: JMatrix = new JMatrix(delegate.getU)

    def v: JMatrix = new JMatrix(delegate.getV)

    def s: JMatrix = new JMatrix(delegate.getS)

    def singularValues: Array[Double] = delegate.getSingularValues
}
