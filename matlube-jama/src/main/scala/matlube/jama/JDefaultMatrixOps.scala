package matlube.jama

import matlube.{DefaultMatrixOps}


/**
 * Implementation of DefaultMatrixOps. Remember DefaultMatrixOps requires a [[matlube.MatrixFactory]]
 * as an argument; which in our case in the [[matlube.jama.JMatrix]] companion object
 * @author Brian Schlining
 * @since 2012-03-02
 */
object JDefaultMatrixOps extends DefaultMatrixOps[JMatrix](JMatrix)
