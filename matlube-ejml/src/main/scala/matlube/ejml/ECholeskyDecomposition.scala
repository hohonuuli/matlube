package matlube.ejml

import org.ejml.factory.{CholeskyDecomposition => EjmlCholeskyDecomposition, DecompositionFactory}
import matlube.{Matrix, HasDelegate}
import org.ejml.data.DenseMatrix64F

/**
 *
 * @author Brian Schlining
 * @since 2012-08-27
 */
class ECholeskyDecomposition protected[ejml] (val matrix: EMatrix)
        extends matlube.CholeskyDecomposition[EMatrix]
        with HasDelegate[EjmlCholeskyDecomposition[DenseMatrix64F]] {

    val delegate: EjmlCholeskyDecomposition[DenseMatrix64F] = {
        val d = DecompositionFactory.chol(matrix.rows , true)
        d.decompose(matrix.delegate)
        d
    }

    def lower(): EMatrix = new EMatrix(delegate.getT(null))

    def solve(m: Matrix[_]): EMatrix = throw new UnsupportedOperationException
}
