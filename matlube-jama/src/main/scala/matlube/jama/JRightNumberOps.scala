package matlube.jama

import matlube.{MatrixFactory, RightNumberOps}


/**
 *
 * @author Brian Schlining
 * @since 2012-04-06
 */

class JRightNumberOps(matrix: JMatrix) extends RightNumberOps[JMatrix](matrix) {
    def factory: MatrixFactory[JMatrix] = JMatrix
}
