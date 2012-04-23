package matlube.jama

import Jama.{CholeskyDecomposition => JamaCholeskyDecomposition}
import matlube.{HasDelegate, Matrix}


/**
 *
 * @author Brian Schlining
 * @since 2012-04-05
 */

class JCholeskyDecomposition protected[jama] (val matrix: JMatrix)
        extends matlube.CholeskyDecomposition[JMatrix]
        with HasDelegate[JamaCholeskyDecomposition] {

    val delegate = new JamaCholeskyDecomposition(matrix.delegate)
    //require(delegate.isSPD, "Matrix is not symetric and positive definite. Can not decompose")

    def lower: JMatrix = new JMatrix(delegate.getL)

    def solve(m: Matrix[_]): JMatrix = m match {
        case j: JMatrix => new JMatrix(delegate.solve(j.delegate))
        case _ => throw new UnsupportedOperationException("Unable to solve using " + m)
    }

}
