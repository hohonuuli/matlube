package matlube.jama

import matlube.{Matrixlib, DefaultMatrixOps}


/**
 * Implementation of DefaultMatrixOps. Remember DefaultMatrixOps requires a [[matlube.MatrixFactory]]
 * as an argument; which in our case in the [[matlube.jama.JMatrix]] companion object
 * @author Brian Schlining
 * @since 2012-03-02
 */
object JMatrixlib extends DefaultMatrixOps[JMatrix] with Matrixlib[JMatrix] {
    val factory = JMatrix
}
