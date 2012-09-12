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

class LeftNumberOps[A <: Matrix[_], B : Numeric](val factory: MatrixFactory[A], val value: B) {

    private val numeric = implicitly[Numeric[B]]
    private def toMatrix(matrix: A): A = factory(matrix.rows, matrix.columns, numeric.toDouble(value))

    // HACK: had to cast to A

    def /(matrix: A): A = (toMatrix(matrix) / matrix).asInstanceOf[A]
    def *(matrix: A): A = (toMatrix(matrix) ** matrix).asInstanceOf[A]
    def -(matrix: A): A = (toMatrix(matrix) - matrix).asInstanceOf[A]
    def +(matrix: A): A = (toMatrix(matrix) + matrix).asInstanceOf[A]


}
