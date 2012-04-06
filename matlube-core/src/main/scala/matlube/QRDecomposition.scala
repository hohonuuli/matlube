package matlube

/**
 *
 * @author Brian Schlining
 * @since 2012-04-05
 */

trait QRDecomposition {

    def h(): Matrix

    def q(): Matrix

    def r(): Matrix

}
