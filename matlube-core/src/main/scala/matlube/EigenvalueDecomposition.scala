package matlube

/**
 * Eigenvalues and eigenvectors of a real matrix.
 *
 * If A is symmetric, then A = V*D*V' where the eigenvalue matrix D is diagonal and the eigenvector
 * matrix V is orthogonal. I.e. A = V.times(D.times(V.transpose())) and V.times(V.transpose())
 * equals the identity matrix.
 *
 * If A is not symmetric, then the eigenvalue matrix D is block diagonal with the real eigenvalues
 * in 1-by-1 blocks and any complex eigenvalues, lambda + i*mu, in 2-by-2 blocks,
 * [lambda, mu; -mu, lambda]. The columns of V represent the eigenvectors in the sense that
 * A*V = V*D, i.e. A.times(V) equals V.times(D). The matrix V may be badly conditioned, or even
 * singular, so the validity of the equation A = V*D*inverse(V) depends upon V.cond().
 *
 * @author Brian Schlining
 * @since 2012-04-05
 */

trait EigenvalueDecomposition[A <: Matrix[_]] {

    def v: A

    def realEigenvalues: A

    def imaginaryEigenvalues: A

    def d: A

}
