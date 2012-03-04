package matlube.jama


import matlube.{Orientations, MatrixFactory}


/**
 *
 * @author Brian Schlining
 * @since 2012-03-02
 */

class JDefaultMatrixFactory extends MatrixFactory[JMatrix] {

    def apply(data: Product): JMatrix = null

    def apply(rows: Int, columns: Int): JMatrix = null

    def apply(rows: Int, columns: Int, fillValue: Double): JMatrix = null

    def apply(rows: Int, columns: Int, data: Array[Double], orientation: Orientations.Orientation): JMatrix = orientation match {
        case Orientations.Row =>

    }
}
