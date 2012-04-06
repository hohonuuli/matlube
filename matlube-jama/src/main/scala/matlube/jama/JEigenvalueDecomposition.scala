package matlube.jama

import Jama.{EigenvalueDecomposition => JamaEigenvalueDecomposition}
import matlube.{Matrix, EigenvalueDecomposition}

/**
 *
 * @author Brian Schlining
 * @since 2012-04-05
 */

class JEigenvalueDecomposition(val matrix: JMatrix) extends EigenvalueDecomposition {

    private[this] val eig = new JamaEigenvalueDecomposition(matrix.delegate)

    def v: Matrix = new JMatrix(eig.getV)

    def realEigenvalues: Matrix = {
        val values = eig.getRealEigenvalues
        JMatrix(1, values.size, values)
    }

    def imaginaryEigenvalues: Matrix = {
        val values = eig.getImagEigenvalues
        JMatrix(1, values.size, values)
    }

    def d: Matrix = new JMatrix(eig.getD)
}
