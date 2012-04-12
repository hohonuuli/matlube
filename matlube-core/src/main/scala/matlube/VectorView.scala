package matlube

import scala.math._
/**
 * VectorView provides row or column oriented access to the individual vectors within a Matrix.
 * For this libaries purposes a vector is simple a matrix where one of the dimensions is 1.
 * THe only difference is that vectors can be accessed using a single idices, for example:
 * {{{
 *  val m0 = // (1, 2, 3, 4, 5, 6) // a 1 x 6 vector
 *  val m1 = m0.t // a 6 x 1 vector
 *  m0(0)         // get the first value in m0
 *  m1(0)         // get the first value in m1
 * }}}
 * @author Brian Schlining
 * @since 2012-03-05
 */
class VectorView(val matrix: Matrix, val orientation: Orientations.Value) {
    require(matrix != null)
    require(orientation != null)

    def size = orientation match {
        case Orientations.Row => matrix.columns
        case Orientations.Column => matrix.rows
    }

    def dimensions = orientation match {
        case Orientations.Row => (1, matrix.columns)
        case Orientations.Column => (matrix.rows, 1)
    }

    def apply(i: Int): Matrix = orientation match {
        case Orientations.Row => if (matrix.rows == 1) {
                matrix
            }
            else {
                matrix(i, ::)
            }
        case Orientations.Column => if (matrix.columns == 1) {
                matrix
            }
            else {
                matrix(::, i)
            }
    }

    def iterator(i: Int): Iterator[Double] =  new Iterator[Double] {
            private[this] val matrixView = apply(i)
            private[this] var currentIdx = 0;
            private[this] val maxIdx = size - 1
            def hasNext: Boolean = currentIdx < maxIdx

            def next(): Double = {
                val n = matrixView(currentIdx)
                currentIdx += 1
                n
            }
        }


    /**
     * Map each vector (i.e. row or column) in a matrix to a single value. The
     * axis that's mapped depends on the orientation you specify.
     * @param fn Converts a matrix (actually a vector, a matrix with one of the dimensions == 1)
     *  to some value
     * @tparam A The type to be returned
     * @return An Array containing the coverted values.
     */
    def map[A : ClassManifest](fn: (Matrix) => A): Array[A] = {
        val numVectors = size
        val array = Array.ofDim[A](numVectors)
        for (i <- 0 until numVectors) {
            array(i) = fn(apply(i))
        }
        array
    }

}
