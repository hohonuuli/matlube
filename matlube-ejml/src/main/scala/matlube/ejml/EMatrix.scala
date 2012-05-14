package matlube.ejml

import matlube._
import org.ejml.data.{Matrix64F, DenseMatrix64F}
import org.ejml.ops.{NormOps, CommonOps, RandomMatrices}
import org.ejml.alg.dense.linsol.LinearSolverFactory


/**
 *
 * @author Brian Schlining
 * @since 2012-03-02
 */

class EMatrix(val delegate: DenseMatrix64F) extends Matrix[EMatrix] with HasDelegate[DenseMatrix64F] {

    private[this] val defaultOps = EMatrixlib

    def unary_- : EMatrix = {
        val d = delegate.copy()
        CommonOps.changeSign(d)
        new EMatrix(d)
    }

    def copy: EMatrix = new EMatrix(delegate.copy())

    def +(that: Matrix[_]): EMatrix = that match {
        case e: EMatrix => {
            val c = delegate.copy()
            CommonOps.addEquals(c, e.delegate)
            new EMatrix(c)
        }
        case m: Matrix[_] => defaultOps.plus(this, that)
    }

    def -(that: Matrix[_]): EMatrix = that match {
        case e: EMatrix => {
            val c = delegate.copy()
            CommonOps.subEquals(c, e.delegate)
            new EMatrix(c)
        }
        case m: Matrix[_] => defaultOps.minus(this, that)
    }

    def **(that: Matrix[_]): EMatrix = that match {
        case e: EMatrix => {
            val c = delegate.copy()
            CommonOps.elementMult(c, e.delegate)
            new EMatrix(c)
        }
        case m: Matrix[_] => defaultOps.elementTimes(this, that)
    }

    def /(that: Matrix[_]): EMatrix = that match {
        case e: EMatrix => {
            val c = delegate.copy()
            CommonOps.elementDiv(c, e.delegate)
            new EMatrix(c)
        }
        case m: Matrix[_] => throw new UnsupportedOperationException
    }

    def *(that: Matrix[_]): EMatrix = that match {
        case e: EMatrix => {
            val c = new DenseMatrix64F(rows, columns)
            CommonOps.mult(delegate, e.delegate, c)
            new EMatrix(c)
        }
        case _ => throw new UnsupportedOperationException
    }

    def t: EMatrix = {
        val b = delegate.copy()
        CommonOps.transpose(b)
        new EMatrix(b)
    }

    def solve(b: Matrix[_]): EMatrix = b match {
        case e: EMatrix => {
            val c = new DenseMatrix64F(e.rows, e.columns)
            CommonOps.solve(delegate, e.delegate, c)
            new EMatrix(c)
        }
        case _ => throw new UnsupportedOperationException
    }

    def solveTranspose(b: Matrix[_]): EMatrix = null

    def inverse: EMatrix = {
        val c = delegate.copy()
        CommonOps.invert(c)
        new EMatrix(c)
    }

    def apply(i: SelectAll, j: SelectAll): EMatrix = new EMatrix(delegate.copy())

    def apply(i: SelectAll): EMatrix = null

    def apply(indices: Array[Int]): EMatrix = null

    def apply(indices: Seq[Int]): EMatrix = null

    def apply(i: Int, j: Int): Double = delegate.get(i, j)

    def apply(r: Array[Int], j0: Int, j1: Int): EMatrix = null

    def apply(i0: Int, i1: Int, c: Array[Int]): EMatrix = null

    def apply(i0: Int, i1: Int, j0: Int, j1: Int): EMatrix = null

    def apply(r: Array[Int], c: Array[Int]): EMatrix = null

    def apply(r: Seq[Int], c: Seq[Int]): EMatrix = null

    def +=(that: Matrix[_]): EMatrix = that match {
        case e: EMatrix => {
            CommonOps.addEquals(delegate, e.delegate)
            this
        }
        case  _ => throw new UnsupportedOperationException
    }

    def -=(that: Matrix[_]): EMatrix = that match {
        case e: EMatrix => {
            CommonOps.subEquals(delegate, e.delegate)
            this
        }
        case  _ => throw new UnsupportedOperationException
    }

    def **=(that: Matrix[_]): EMatrix = null

    def /=(that: Matrix[_]): EMatrix = null

    def \=(that: Matrix[_]): EMatrix = null

    def *=[@specialized(Int, Long, Float, Double) B: Numeric](s: B): EMatrix = null

    def rows: Int = delegate.getNumRows

    def columns: Int = delegate.getNumCols

    override def length: Int = delegate.getNumElements

    def cond: Double = NormOps.conditionP2(delegate)

    def det: Double = CommonOps.det(delegate)

    def norm1: Double = NormOps.normP1(delegate)

    def norm2: Double = NormOps.normP2(delegate)

    def normF: Double = NormOps.normF(delegate)

    def normInf: Double = NormOps.normPInf(delegate)

    def rank: Int = 0

    def trace: Double = CommonOps.trace(delegate)

    def chol: CholeskyDecomposition[EMatrix] = null

    def eig: EigenvalueDecomposition[EMatrix] = null

    def lu: LUDecomposition[EMatrix] = null

    def qr: QRDecomposition[EMatrix] = null

    def svd: SingularValueDecomposition[EMatrix] = null

    def update[@specialized(Int, Long, Float, Double) A: Numeric](i: Int, j: Int, v: A) {
        val numeric = implicitly[Numeric[A]]
        delegate.set(i, j, numeric.toDouble(v))
    }

    def update[@specialized(Int, Long, Float, Double) A: Numeric](i: SelectAll, j: Int, v: A) {

    }

    def update[@specialized(Int, Long, Float, Double) A: Numeric](i: Int, j: SelectAll, v: A) {}


}

object EMatrix extends MatrixFactory[EMatrix] {
    def apply[@specialized(Int, Long, Float, Double) B: Numeric](rows: Int, columns: Int,
            data: Array[B], orientation: Orientations.Orientation): EMatrix =  {
        orientation match {
            case Orientations.Row => new EMatrix(new DenseMatrix64F(MatrixFactory.rowArrayTo2DArray(rows, columns, data)))
            case Orientations.Column => {
                val a = new DenseMatrix64F(MatrixFactory.rowArrayTo2DArray(columns, rows, data))
                CommonOps.transpose(a)
                new EMatrix(a)
            }
        }
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
