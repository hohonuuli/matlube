package matlube.jama

import Jama.{EigenvalueDecomposition => JamaEigenvalueDecomposition}
import matlube.{HasDelegate}

/**
 *
 * @author Brian Schlining
 * @since 2012-04-05
 */

class JEigenvalueDecomposition protected[jama] (val matrix: JMatrix)
        extends matlube.EigenvalueDecomposition[JMatrix]
        with HasDelegate[JamaEigenvalueDecomposition] {

    val delegate = new JamaEigenvalueDecomposition(matrix.delegate)

    def v: JMatrix = new JMatrix(delegate.getV)

    def realEigenvalues: JMatrix = {
        val values = delegate.getRealEigenvalues
        JMatrix(1, values.size, values)
    }

    def imaginaryEigenvalues: JMatrix = {
        val values = delegate.getImagEigenvalues
        JMatrix(1, values.size, values)
    }

    def d: JMatrix = new JMatrix(delegate.getD)
}
