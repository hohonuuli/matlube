package matlube.jama

import Jama.{QRDecomposition => JamaQRDecomposition}
import matlube.Matrix

/**
 *
 * @author Brian Schlining
 * @since 2012-04-05
 */

class JQRDecomposition(val matrix: JMatrix) extends matlube.QRDecomposition {

    private[this] val qr = new JamaQRDecomposition(matrix.delegate)
    require(qr.isFullRank, "Matrix was not full rank. Can not decompose.")

    def h: Matrix = new JMatrix(qr.getH)

    def q: Matrix = new JMatrix(qr.getQ)

    def r: Matrix = new JMatrix(qr.getR)
}
