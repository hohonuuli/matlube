package matlube.jama

import matlube.LUDecomposition
import Jama.{LUDecomposition => JamaLUDecomposition}

/**
 *
 * @author Brian Schlining
 * @since 2012-04-05
 */

class JLUDecomposition(val matrix: JMatrix) extends LUDecomposition {

    private val lu = new JamaLUDecomposition(matrix.delegate)

    def lower = new JMatrix(lu.getL)

    def upper = new JMatrix(lu.getU)

    def pivot = {
        val pivotArray = lu.getDoublePivot
        JMatrix(1, pivotArray.size, pivotArray)
    }

    def isSingular = !lu.isNonsingular

    def determinant = lu.det()
}
