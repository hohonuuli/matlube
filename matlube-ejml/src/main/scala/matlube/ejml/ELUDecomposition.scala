package matlube.ejml

import org.ejml.factory.{LUDecomposition => EjmlLUDecomposition, DecompositionFactory}
import matlube.HasDelegate
import org.ejml.data.DenseMatrix64F

/**
 *
 * @author Brian Schlining
 * @since 2012-08-27
 */
class ELUDecomposition protected[ejml] (val matrix: EMatrix)
        extends matlube.LUDecomposition[EMatrix]
        with HasDelegate[EjmlLUDecomposition[DenseMatrix64F]] {

    val delegate = {
        val d = DecompositionFactory.lu(matrix.rows, matrix.columns)
        d.decompose(matrix.delegate)
        d
    }

    def lower: EMatrix = new EMatrix(delegate.getLower(null))

    def upper: EMatrix = new EMatrix(delegate.getUpper(null))

    def pivot: EMatrix = new EMatrix(delegate.getPivot(null))

    def isSingular: Boolean = delegate.isSingular

    def determinant: Double = delegate.computeDeterminant()
}
