package matlube

/**
 *
 * @author Brian Schlining
 * @since 2012-04-05
 */

trait CholeskyDecomposition {

    def lower(): Matrix

    def solve(m: Matrix): Matrix

}
