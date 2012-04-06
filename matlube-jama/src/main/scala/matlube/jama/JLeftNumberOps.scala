package matlube.jama

import matlube.{MatrixFactory, LeftNumberOps}


/**
 * Wraps scalar values so that they can appear as matrices
 *
 * @author Brian Schlining
 * @since 2012-04-06
 */

class JLeftNumberOps[B : Numeric](value: B) extends LeftNumberOps[JMatrix, B](value) {
    def factory: MatrixFactory[JMatrix] = JMatrix
}
