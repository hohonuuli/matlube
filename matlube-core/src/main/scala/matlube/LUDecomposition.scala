package matlube

/**
 * LU Decomposition.
 *
 * For an m-by-n matrix A with m >= n, the LU decomposition is an m-by-n unit lower triangular
 * matrix L, an n-by-n upper triangular matrix U, and a permutation vector piv of length m so that
 * A(piv,:) = L*U. If m < n, then L is m-by-m and U is m-by-n.
 *
 * The LU decompostion with pivoting always exists, even if the matrix is singular, so the
 * constructor will never fail. The primary use of the LU decomposition is in the solution of
 * square systems of simultaneous linear equations. This will fail if isNonsingular() returns false.
 *
 * @author Brian Schlining
 * @since 2012-04-05
 */

trait LUDecomposition {

    def lower: Matrix

    def upper: Matrix

    def pivot: Matrix

    def isSingular: Boolean

    def determinant: Double
}
