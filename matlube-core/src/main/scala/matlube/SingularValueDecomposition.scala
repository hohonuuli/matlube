package matlube

/**
 * Singular Value Decomposition.
 *
 * For an m-by-n matrix A with m >= n, the singular value decomposition is an m-by-n orthogonal
 * matrix U, an n-by-n diagonal matrix S, and an n-by-n orthogonal matrix V so that A = U*S*V'.
 *
 * The singular values, sigma[k] = S[k][k], are ordered so that
 * sigma[0] >= sigma[1] >= ... >= sigma[n-1].
 *
 * The singular value decompostion always exists, so the constructor will never fail. The matrix
 * condition number and the effective numerical rank can be computed from this decomposition.
 *
 * @author Brian Schlining
 * @since 2012-04-06
 */
trait SingularValueDecomposition[A <: Matrix[A]] {

    /**
     *
     * @return Left singular values
     */
    def u: A

    /**
     *
     * @return Right singular values
     */
    def v: A

    /**
     *
     * @return Diagonal matrix of singular values
     */
    def s: A

    /**
     *
     * @return The one-dimensional array of singular values
     */
    def singularValues: Array[Double]


}
