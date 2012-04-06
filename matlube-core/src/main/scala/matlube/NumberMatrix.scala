package matlube

/**
 * Wrapper class so that scala values can appear to be Matrices
 *
 * @author Brian Schlining
 * @since 2012-04-06
 */

abstract class NumberMatrix[A <: Matrix, B : Numeric](val value: B) {

    def factory: MatrixFactory[A]

    private val numeric = implicitly[Numeric[B]]
    def toMatrix(): A = factory(1, 1, numeric.toDouble(value))
    def toMatrix(m: Int,  n: Int): A = factory(m, n, numeric.toDouble(value))
    private def likeMatrix(m: Matrix): A = toMatrix(m.rows, m.columns)
    def /(matrix: Matrix) = likeMatrix(matrix) / matrix
    def *(matrix: Matrix) = likeMatrix(matrix) * matrix
    def -(matrix: Matrix) = likeMatrix(matrix) - matrix
    def +(matrix: Matrix) = likeMatrix(matrix) + matrix


}
