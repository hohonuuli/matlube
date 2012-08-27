package matlube.ejml

import org.ejml.factory.{EigenDecomposition, DecompositionFactory}
import matlube.HasDelegate
import org.ejml.data.DenseMatrix64F
import org.ejml.ops.EigenOps

/**
 *
 * @author Brian Schlining
 * @since 2012-08-27
 */
class EEigenvalueDecomposition protected[ejml] (val matrix: EMatrix)
        extends matlube.EigenvalueDecomposition[EMatrix]
        with HasDelegate[EigenDecomposition[DenseMatrix64F]]{

    val delegate = {
        val d = DecompositionFactory.eig(matrix.rows, true)
        d.decompose(matrix.delegate)
        d
    }

    def v: EMatrix = new EMatrix(EigenOps.createMatrixV(delegate))

    def realEigenvalues: EMatrix = throw new UnsupportedOperationException

    def imaginaryEigenvalues: EMatrix = throw new UnsupportedOperationException

    def d: EMatrix = new EMatrix(EigenOps.createMatrixD(delegate))
}
