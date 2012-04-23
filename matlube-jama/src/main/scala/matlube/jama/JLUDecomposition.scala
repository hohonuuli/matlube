package matlube.jama

import Jama.{LUDecomposition => JamaLUDecomposition}
import matlube.{HasDelegate}

/**
 *
 * @author Brian Schlining
 * @since 2012-04-05
 */

class JLUDecomposition protected[jama] (val matrix: JMatrix)
        extends matlube.LUDecomposition[JMatrix]
        with HasDelegate[JamaLUDecomposition] {

    val delegate = new JamaLUDecomposition(matrix.delegate)

    def lower = new JMatrix(delegate.getL)

    def upper = new JMatrix(delegate.getU)

    def pivot = {
        val pivotArray = delegate.getDoublePivot
        JMatrix(1, pivotArray.size, pivotArray)
    }

    def isSingular = !delegate.isNonsingular

    def determinant = delegate.det()
}
