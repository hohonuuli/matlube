package matlube.ejml

import matlube._
import org.ejml.data.{Matrix64F, DenseMatrix64F}
import org.ejml.ops.RandomMatrices


/**
 *
 * @author Brian Schlining
 * @since 2012-03-02
 */

class EMatrix(val delegate: DenseMatrix64F) extends Matrix with HasDelegate[DenseMatrix64F] {

    def unary_- : Matrix = null

    def +(that: Matrix): Matrix = null

    def -(that: Matrix): Matrix = null

    def **(that: Matrix): Matrix = null

    def chol: CholeskyDecomposition = null

    def eig: EigenvalueDecomposition = null

    def lu: LUDecomposition = null

    def qr: QRDecomposition = null

    def svd: SingularValueDecomposition = null

    def /(that: Matrix): Matrix = null

    def \(that: Matrix): Matrix = null

    def *(that: Matrix): Matrix = null

    def t: Matrix = null

    def solve(b: Matrix): Matrix = null

    def solveTranspose(b: Matrix): Matrix = null

    def inverse: Matrix = null

    def apply(i: SelectAll, j: Int): Matrix = null

    def apply(i: Int, j: SelectAll): Matrix = null

    def apply(i0: Int, i1: Int, j0: Int, j1: Int): Matrix = null

    def apply(r: Array[Int], c: Array[Int]): Matrix = null

    def apply(r: SelectAll, c: Array[Int]): Matrix = null

    def apply(r: Array[Int], c: SelectAll): Matrix = null

    def apply(i0: Int, i1: Int, c: Array[Int]): Matrix = null

    def apply(i0: Int, i1: Int, c: SelectAll): Matrix = null

    def apply(r: SelectAll, j0: Int, j1: Int): Matrix = null

    def +=(that: Matrix): Matrix = null

    def -=(that: Matrix): Matrix = null

    def **=(that: Matrix): Matrix = null

    def /=(that: Matrix): Matrix = null

    def \=(that: Matrix): Matrix = null

    def *=[@specialized(Int, Long, Float, Double) A: Numeric](s: A): Matrix = null

    def rows: Int = delegate.numRows

    def columns: Int = delegate.numCols

    def cond: Double = 0.0

    def det: Double = 0.0

    def norm1: Double = 0.0

    def norm2: Double = 0.0

    def normF: Double = 0.0

    def normInf: Double = 0.0

    def rank: Int = 0

    def trace: Double = 0.0

    def apply(i: Int, j: Int): Double = delegate.get(i, j)

    def update[@specialized(Int, Long, Float, Double) A: Numeric](i: Int, j: Int, v: A) {}

    def update[@specialized(Int, Long, Float, Double) A: Numeric](i: SelectAll, j: Int, v: A) {}

    def update[@specialized(Int, Long, Float, Double) A: Numeric](i: Int, j: SelectAll, v: A) {}

    def copy: Matrix = null

    def *[@specialized(Int, Long, Float, Double) A: Numeric](s: A): Matrix = null
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

    def identity(rows: Int, columns: Int): EMatrix = {
        val a = apply(rows, columns, 0D)
        for (i <- 0 until rows; j <- 0 until columns; if i == j) {
            a(i, j) = 1D
        }
        return a
    }

    def ones(rows: Int, columns: Int): EMatrix = apply(rows, columns, 1D)

    def random(rows: Int, columns: Int): EMatrix =
        new EMatrix(RandomMatrices.createRandom(rows, columns, new java.util.Random))

    def nans(rows: Int, columns: Int): EMatrix = apply(rows, columns, Double.NaN)

    def zeros(rows: Int, columns: Int): EMatrix = apply(rows, columns, 0D)
}
