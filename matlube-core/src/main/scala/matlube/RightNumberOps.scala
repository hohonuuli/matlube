package matlube

/**
 * Wrapper class so that scala values can appear to be Matrices on the right-hand side
 * of operations:
 * {{{
 *     val m = // some matrix
 *     val mPlusFive = m + 5
 * }}}
 * @author Brian Schlining
 * @since 2012-04-06
 */

abstract class RightNumberOps[A <: Matrix](val matrix: Matrix) {

    def factory: MatrixFactory[A]

    private def toMatrix[B : Numeric](value: B): A = {
        val numeric = implicitly[Numeric[B]]
        factory(matrix.rows, matrix.columns, numeric.toDouble(value))
    }

    def /[B : Numeric](value: B) = matrix / toMatrix(value)
    def *[B : Numeric](value: B) = matrix * toMatrix(value)
    def -[B : Numeric](value: B) = matrix - toMatrix(value)
    def +[B : Numeric](value: B) = matrix + toMatrix(value)

}
