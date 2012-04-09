package matlube.ejml

import matlube.{Matrixlib, DefaultMatrixOps}


/**
 *
 * @author Brian Schlining
 * @since 2012-04-09
 */

class EMatrixlib extends DefaultMatrixOps[EMatrix] with Matrixlib[EMatrix] {
    val factory = EMatrix
}
