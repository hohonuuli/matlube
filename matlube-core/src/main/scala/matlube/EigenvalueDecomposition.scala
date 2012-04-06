package matlube

/**
 *
 * @author Brian Schlining
 * @since 2012-04-05
 */

trait EigenvalueDecomposition {

    def v(): Matrix

    def realEigenvalues(): Matrix

    def imaginaryEigenvalues(): Matrix

    def d(): Matrix

}
