package matlube.jama

import matlube.{Matrixlib, DefaultMatrixOps}


/**
 * Implementation of [[matlube.DefaultMatrixOps]] and [[matlube.Matrixlib]]. Remember
 * both those traits requires a [[matlube.MatrixFactory]] to be defined as a factory property;
 * which in our case in the [[matlube.jama.JMatrix]] companion object
 * @author Brian Schlining
 * @since 2012-03-02
 */
object JMatrixlib
        extends DefaultMatrixOps[JMatrix]
        with Matrixlib[JMatrix] {
    
    val factory = JMatrix
}
