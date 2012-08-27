package matlube.ejml


import org.ejml.factory.{QRDecomposition => EjmlQRDecomposition, DecompositionFactory}
import matlube.HasDelegate
import org.ejml.data.DenseMatrix64F

/**
 *
 * @author Brian Schlining
 * @since 2012-08-27
 */
class EQRDecomposition protected[ejml] (val matrix: EMatrix)
        extends matlube.QRDecomposition[EMatrix]
        with HasDelegate[EjmlQRDecomposition[DenseMatrix64F]] {

    val delegate: EjmlQRDecomposition[DenseMatrix64F] = {
        val d = DecompositionFactory.qr(matrix.rows, matrix.columns)
        d.decompose(matrix.delegate)
        d
    }

    def h(): EMatrix = throw new UnsupportedOperationException

    def q(): EMatrix = new EMatrix(delegate.getQ(null, false))

    def r(): EMatrix = new EMatrix(delegate.getR(null, false))


}
