package matlube.jama

import matlube.{MatrixFactory, DefaultMatrixOps}


/**
 *
 * @author Brian Schlining
 * @since 2012-03-02
 */
object JDefaultMatrixOps extends DefaultMatrixOps[JMatrix](new JMatrixFactory)
