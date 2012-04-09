package matlube.jama

import Jama.{CholeskyDecomposition => JamaCholeskyDecomposition}
import matlube.{HasDelegate, Matrix, CholeskyDecomposition}


/**
 *
 * @author Brian Schlining
 * @since 2012-04-05
 */

class JCholeskyDecomposition protected[jama] (val matrix: JMatrix) extends CholeskyDecomposition
        with HasDelegate[JamaCholeskyDecomposition] {

    val delegate = new JamaCholeskyDecomposition(matrix.delegate)
    //require(delegate.isSPD, "Matrix is not symetric and positive definite. Can not decompose")

    def lower: Matrix = new JMatrix(delegate.getL)

    def solve(m: Matrix): Matrix = m match {
        case j: JMatrix => new JMatrix(delegate.solve(j.delegate))
        case _ => throw new UnsupportedOperationException("Unable to solve using " + m)
    }

}
