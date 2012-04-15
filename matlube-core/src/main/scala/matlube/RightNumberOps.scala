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

class RightNumberOps[A <: Matrix](val factory: MatrixFactory[A], val matrix: Matrix) {


    private def toMatrix[B : Numeric](value: B): A = {
        val numeric = implicitly[Numeric[B]]
        factory(matrix.rows, matrix.columns, numeric.toDouble(value))
    }

    def /[B : Numeric](value: B) = matrix / toMatrix(value)
    def *[B : Numeric](value: B) = matrix ** toMatrix(value)
    def -[B : Numeric](value: B) = matrix - toMatrix(value)
    def +[B : Numeric](value: B) = matrix + toMatrix(value)

}
