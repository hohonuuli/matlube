package matlube.ejml

import matlube._
import org.ejml.data.{Matrix64F, DenseMatrix64F}
import org.ejml.ops.RandomMatrices


/**
 *
 * @author Brian Schlining
 * @since 2012-03-02
 */

class EMatrix(val delegate: DenseMatrix64F) extends Matrix[EMatrix] with HasDelegate[DenseMatrix64F] {

    def unary_- : EMatrix = null

    def copy: EMatrix = null

    def +(that: Matrix[_]): EMatrix = null

    def -(that: Matrix[_]): EMatrix = null

    def **(that: Matrix[_]): EMatrix = null

    def /(that: Matrix[_]): EMatrix = null

    //def \(that: Matrix[_]): EMatrix = null

    def *(that: Matrix[_]): EMatrix = null

    def t: EMatrix = null

    def solve(b: Matrix[_]): EMatrix = null

    def solveTranspose(b: Matrix[_]): EMatrix = null

    def inverse: EMatrix = null

    def apply(i: SelectAll, j: SelectAll): EMatrix = null

    def apply(i: SelectAll): EMatrix = null

    def apply(indices: Array[Int]): EMatrix = null

    def apply(indices: Seq[Int]): EMatrix = null

    def apply(i: Int, j: Int): Double = Double.NaN

    def apply(r: Array[Int], j0: Int, j1: Int): EMatrix = null

    def apply(i0: Int, i1: Int, c: Array[Int]): EMatrix = null

    def apply(i0: Int, i1: Int, j0: Int, j1: Int): EMatrix = null

    def apply(r: Array[Int], c: Array[Int]): EMatrix = null

    def apply(r: Seq[Int], c: Seq[Int]): EMatrix = null

    def +=(that: Matrix[_]): EMatrix = null

    def -=(that: Matrix[_]): EMatrix = null

    def **=(that: Matrix[_]): EMatrix = null

    def /=(that: Matrix[_]): EMatrix = null

    def \=(that: Matrix[_]): EMatrix = null

    def *[@specialized(Int, Long, Float, Double) B: Numeric](s: B): EMatrix = null

    def *=[@specialized(Int, Long, Float, Double) B: Numeric](s: B): EMatrix = null

    def rows: Int = 0

    def columns: Int = 0

    def cond: Double = 0.0

    def det: Double = 0.0

    def norm1: Double = 0.0

    def norm2: Double = 0.0

    def normF: Double = 0.0

    def normInf: Double = 0.0

    def rank: Int = 0

    def trace: Double = 0.0

    def chol: CholeskyDecomposition[EMatrix] = null

    def eig: EigenvalueDecomposition[EMatrix] = null

    def lu: LUDecomposition[EMatrix] = null

    def qr: QRDecomposition[EMatrix] = null

    def svd: SingularValueDecomposition[EMatrix] = null

    def update[@specialized(Int, Long, Float, Double) A: Numeric](i: Int, j: Int, v: A) {}

    def update[@specialized(Int, Long, Float, Double) A: Numeric](i: SelectAll, j: Int, v: A) {}

    def update[@specialized(Int, Long, Float, Double) A: Numeric](i: Int, j: SelectAll, v: A) {}
}

object EMatrix extends MatrixFactory[EMatrix] {
    def apply[@specialized(Int, Long, Float, Double) B: Numeric](rows: Int, columns: Int,
            data: Array[B], orientation: Orientations.Orientation): EMatrix = orientation match {
        case Orientations.Row => new EMatrix(new DenseMatrix64F(MatrixFactory.rowArrayTo2DArray(rows, columns, data)))
    }

    def apply(data: Product): EMatrix = {
        def size = MatrixFactory.productSize(data)
        def array = MatrixFactory.toArray[Double](data)
        new EMatrix(new DenseMatrix64F(MatrixFactory.rowArrayTo2DArray(size._1, size._2, array)))
    }

    def apply(rows: Int, columns: Int): EMatrix = apply(rows, columns, 0D)

    def apply(rows: Int, columns: Int, fillValue: Double): EMatrix =
        new EMatrix(new DenseMatrix64F(Array.tabulate(rows, columns) {(u, v) => fillValue}))

    def eye(rows: Int, columns: Int): EMatrix = {
        val a = apply(rows, columns, 0D)
        for (i <- 0 until rows; j <- 0 until columns; if i == j) {
            a(i, j) = 1D
        }
        a
    }


    def apply[@specialized(Int, Long, Float, Double)B: Numeric](data: Array[B], orientation: Orientations.Orientation): EMatrix = null

    def ones(rows: Int, columns: Int): EMatrix = apply(rows, columns, 1D)

    def rand(rows: Int, columns: Int): EMatrix =
        new EMatrix(RandomMatrices.createRandom(rows, columns, new java.util.Random))

    def nans(rows: Int, columns: Int): EMatrix = apply(rows, columns, Double.NaN)

    def zeros(rows: Int, columns: Int): EMatrix = apply(rows, columns, 0D)


}
