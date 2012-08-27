package matlube.ejml


import org.ejml.factory.{SingularValueDecomposition => EjmlSingularValueDecomposition, DecompositionFactory}
import matlube.HasDelegate
import org.ejml.data.DenseMatrix64F

/**
 *
 * @author Brian Schlining
 * @since 2012-08-27
 */
class ESingularValueDecomposition protected[ejml] (val matrix: EMatrix)
        extends matlube.SingularValueDecomposition[EMatrix]
        with HasDelegate[EjmlSingularValueDecomposition[DenseMatrix64F]]{

    val delegate = {
        val d = DecompositionFactory.svd(matrix.rows, matrix.columns, true, true, false)
        d.decompose(matrix.delegate)
        d
    }

    /**
     *
     * @return Left singular values
     */
    def u: EMatrix = new EMatrix(delegate.getU(null, false))

    /**
     *
     * @return Right singular values
     */
    def v: EMatrix = new EMatrix(delegate.getV(null, false))

    /**
     *
     * @return Diagonal matrix of singular values. Internally this calls
     *         SingularValueDecomposition.getW in EJML
     */
    def s: EMatrix = new EMatrix(delegate.getW(null))

    /**
     *
     * @return The one-dimensional array of singular values
     */
    def singularValues: Array[Double] = delegate.getSingularValues
}
