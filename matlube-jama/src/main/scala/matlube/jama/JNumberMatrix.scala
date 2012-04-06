package matlube.jama

import matlube.{MatrixFactory, NumberMatrix}


/**
 * Wraps scala values so that they can appear as matrices
 *
 * @author Brian Schlining
 * @since 2012-04-06
 */

class JNumberMatrix[B : Numeric](value: B) extends NumberMatrix[JMatrix, B](value) {
    def factory: MatrixFactory[JMatrix] = JMatrix
}
