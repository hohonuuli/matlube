package matlube.jama

import Jama.{QRDecomposition => JamaQRDecomposition}
import matlube.{HasDelegate, Matrix}

/**
 *
 * @author Brian Schlining
 * @since 2012-04-05
 */

class JQRDecomposition protected[jama] (val matrix: JMatrix) extends matlube.QRDecomposition
        with HasDelegate[JamaQRDecomposition] {

    val delegate = new JamaQRDecomposition(matrix.delegate)
    require(delegate.isFullRank, "Matrix was not full rank. Can not decompose.")

    def h: Matrix = new JMatrix(delegate.getH)

    def q: Matrix = new JMatrix(delegate.getQ)

    def r: Matrix = new JMatrix(delegate.getR)
}
