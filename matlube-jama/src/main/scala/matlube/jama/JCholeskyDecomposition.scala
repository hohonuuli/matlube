package matlube.jama

import Jama.{CholeskyDecomposition => JamaCholeskyDecomposition}
import matlube.{Matrix, CholeskyDecomposition}


/**
 *
 * @author Brian Schlining
 * @since 2012-04-05
 */

class JCholeskyDecomposition(val matrix: JMatrix) extends CholeskyDecomposition {

    private val chol = new JamaCholeskyDecomposition(matrix.delegate)
    require(chol.isSPD, "Matrix is not symetric and positive definite. Can not decompose")

    def lower(): Matrix = new JMatrix(chol.getL)

    def solve(m: Matrix): Matrix = m match {
        case j: JMatrix => new JMatrix(chol.solve(j.delegate))
        case _ => throw new UnsupportedOperationException("Unable to solve using " + m)
    }

}
