package matlube

/**
 * Wrapper class so that scalar values can appear to be Matrices on the right-hand side
 * of operations:
 * {{{
 *     val m = // some matrix
 *     val mPlusFive = m + 5
 * }}}
 * @author Brian Schlining
 * @since 2012-04-06
 */

class RightNumberOps[A <: Matrix[A]](val factory: MatrixFactory[A], val matrix: A) {


    private def toMatrix[B : Numeric](value: B): A = {
        val numeric = implicitly[Numeric[B]]
        factory(matrix.rows, matrix.columns, numeric.toDouble(value))
    }

    // HACK: had to cast to A

    def /[B : Numeric](value: B): A = (matrix / toMatrix(value))
    def *[B : Numeric](value: B): A = (matrix ** toMatrix(value))
    def -[B : Numeric](value: B): A = (matrix - toMatrix(value))
    def +[B : Numeric](value: B): A = (matrix + toMatrix(value))
}
