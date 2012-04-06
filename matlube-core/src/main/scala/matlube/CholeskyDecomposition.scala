package matlube

/**
 * Cholesky Decomposition.
 *
 * For a symmetric, positive definite matrix A, the Cholesky decomposition is an lower triangular
 * matrix L so that A = L*L'.
 *
 * If the matrix is not symmetric or positive definite, the constructor returns a partial
 * decomposition and sets an internal flag that may be queried by the isSPD() method.
 *
 * @author Brian Schlining
 * @since 2012-04-05
 */

trait CholeskyDecomposition {

    def lower(): Matrix

    def solve(m: Matrix): Matrix

}
