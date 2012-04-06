package matlube

/**
 * Wrapper class so that scala values can appear to be Matrices on the left-hand side of operations:
 * {{{
 *  val m = // some matrix
 *  val mPlusFive = 5 + m
 * }}}
 *
 * @author Brian Schlining
 * @since 2012-04-06
 */

abstract class LeftNumberOps[A <: Matrix, B : Numeric](val value: B) {

    def factory: MatrixFactory[A]

    private val numeric = implicitly[Numeric[B]]
    private def toMatrix(matrix: Matrix) = factory(matrix.rows, matrix.columns, numeric.toDouble(value))

    def /(matrix: Matrix) = toMatrix(matrix) / matrix
    def *(matrix: Matrix) = toMatrix(matrix) * matrix
    def -(matrix: Matrix) = toMatrix(matrix) - matrix
    def +(matrix: Matrix) = toMatrix(matrix) + matrix


}
