package matlube.jama

import Jama.{EigenvalueDecomposition => JamaEigenvalueDecomposition}
import matlube.{HasDelegate, Matrix, EigenvalueDecomposition}

/**
 *
 * @author Brian Schlining
 * @since 2012-04-05
 */

class JEigenvalueDecomposition protected[jama] (val matrix: JMatrix) extends EigenvalueDecomposition
        with HasDelegate[JamaEigenvalueDecomposition] {

    val delegate = new JamaEigenvalueDecomposition(matrix.delegate)

    def v: Matrix = new JMatrix(delegate.getV)

    def realEigenvalues: Matrix = {
        val values = delegate.getRealEigenvalues
        JMatrix(1, values.size, values)
    }

    def imaginaryEigenvalues: Matrix = {
        val values = delegate.getImagEigenvalues
        JMatrix(1, values.size, values)
    }

    def d: Matrix = new JMatrix(delegate.getD)
}
