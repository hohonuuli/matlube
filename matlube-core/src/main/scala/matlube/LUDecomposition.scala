package matlube

/**
 *
 * @author Brian Schlining
 * @since 2012-04-05
 */

trait LUDecomposition {

    def lower(): Matrix

    def upper(): Matrix

    def pivot(): Matrix

    def isSingular(): Boolean

    def determinant(): Double
}
