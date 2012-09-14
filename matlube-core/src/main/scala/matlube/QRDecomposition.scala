package matlube

/**
 * QR Decomposition.
 *
 * For an m-by-n matrix A with m >= n, the QR decomposition is an m-by-n orthogonal matrix Q and an
 * n-by-n upper triangular matrix R so that A = Q*R.
 *
 * The QR decompostion always exists, even if the matrix does not have full rank, so the constructor
 * will never fail. The primary use of the QR decomposition is in the least squares solution of
 * nonsquare systems of simultaneous linear equations. This will fail if isFullRank()
 *
 * @author Brian Schlining
 * @since 2012-04-05
 */

trait QRDecomposition[A <: Matrix[A]] {

    def h(): A

    def q(): A

    def r(): A

}
