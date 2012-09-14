package matlube.ejml

import matlube._
import org.ejml.data.{Matrix64F, DenseMatrix64F}
import org.ejml.ops.{SingularOps, NormOps, CommonOps, RandomMatrices}


/**
 *
 * @author Brian Schlining
 * @since 2012-03-02
 */

class EMatrix(val delegate: DenseMatrix64F) extends Matrix[EMatrix] with HasDelegate[DenseMatrix64F] {

    private[this] val defaultOps = EMatrixlib

    val factory = EMatrix // EMatrix object is the factory

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
            val c = new DenseMatrix64F(columns, e.columns)
            CommonOps.solve(delegate, e.delegate, c)
            new EMatrix(c)
        }
        case _ => throw new UnsupportedOperationException
    }

    def solveTranspose(b: Matrix[_]): EMatrix = throw new UnsupportedOperationException

    def inverse: EMatrix = {
        val c = delegate.copy()
        CommonOps.invert(c)
        new EMatrix(c)
    }

    def apply(i: SelectAll, j: SelectAll): EMatrix = new EMatrix(delegate.copy())

    def apply(i: SelectAll): EMatrix = {
        val n = rows * columns
        val d = rowArray
        EMatrix(n, 1, d, Orientations.Row)
    }


    def apply(indices: Array[Int]): EMatrix = {
        val array = (for (i <- indices) yield apply(i))
        EMatrix(1, array.size, array)
    }

    def apply(indices: Seq[Int]): EMatrix = apply(indices.toArray)

    def apply(i: Int, j: Int): Double = delegate.get(i, j)

    def apply(r: Array[Int], j0: Int, j1: Int): EMatrix = {
        EMatrix(Array.tabulate[Double](r.size, j1 - j0 + 1) {
            (u, v) =>
                this(r(u), j0 + v)
        })
    }

    //    def apply(i0: Int, i1: Int, c: Array[Int]): EMatrix = null
    //
    //    def apply(i0: Int, i1: Int, j0: Int, j1: Int): EMatrix = null

    def apply(r: Array[Int], c: Array[Int]): EMatrix =  {
        EMatrix(Array.tabulate[Double](r.size, c.size) {
            (u, v) =>
                this(r(u), c(v))
        })
    }

    def apply(r: Seq[Int], c: Seq[Int]): EMatrix =  {
        EMatrix(Array.tabulate[Double](r.size, c.size) {
            (u, v) =>
                this(r(u), c(v))
        })
    }


    def rowArray: Array[Double] = iterator.map(_.toDouble).toArray
    def columnArray: Array[Double] = delegate.getData

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

    def **=(that: Matrix[_]): EMatrix = throw new UnsupportedOperationException


    def /=(that: Matrix[_]): EMatrix = throw new UnsupportedOperationException

    def \=(that: Matrix[_]): EMatrix = throw new UnsupportedOperationException

    def *=[@specialized(Int, Long, Float, Double) B: Numeric](s: B): EMatrix = throw new UnsupportedOperationException

    def rows: Int = delegate.getNumRows

    def columns: Int = delegate.getNumCols

    override def length: Int = delegate.getNumElements

    def cond: Double = NormOps.conditionP2(delegate)

    def det: Double = CommonOps.det(delegate)

    def norm1: Double = NormOps.normP1(delegate)

    def norm2: Double = NormOps.normP2(delegate)

    def normF: Double = NormOps.normF(delegate)

    def normInf: Double = NormOps.normPInf(delegate)

    def rank: Int = SingularOps.rank(svd.delegate, 0.000000001)

    def trace: Double = CommonOps.trace(delegate)

    def chol: ECholeskyDecomposition = new ECholeskyDecomposition(this)

    def eig: EEigenvalueDecomposition = new EEigenvalueDecomposition(this)

    def lu: ELUDecomposition = new ELUDecomposition(this)

    def qr: EQRDecomposition = new EQRDecomposition(this)

    def svd: ESingularValueDecomposition = new ESingularValueDecomposition(this)

    def update[@specialized(Int, Long, Float, Double) A: Numeric](i: Int, j: Int, v: A) {
        val numeric = implicitly[Numeric[A]]
        delegate.set(i, j, numeric.toDouble(v))
    }

    def update[@specialized(Int, Long, Float, Double) A: Numeric](i: SelectAll, j: Int, v: A) {
        val numeric = implicitly[Numeric[A]]
        for (ii <- 0 until rows) {
            delegate.set(ii, j, numeric.toDouble(v))
        }
    }

    def update[@specialized(Int, Long, Float, Double) A: Numeric](i: Int, j: SelectAll, v: A) {
        val numeric = implicitly[Numeric[A]]
        for (jj <- 0 until columns) {
            delegate.set(i, jj, numeric.toDouble(v))
        }
    }

    override def toString: String = {
        val max = 5
        val s = getClass.getSimpleName + "(" + rows + "x" + columns + ")[" +
                iterator.take(max).mkString(",")
        if (rows * columns > max) {
            s + ",...]"
        }
        else {
            s + "]"
        }
    }

}

object EMatrix extends MatrixFactory[EMatrix] {
    def apply[@specialized(Int, Long, Float, Double) B: Numeric](rows: Int, columns: Int,
            data: Array[B], orientation: Orientations.Orientation = Orientations.Row): EMatrix =  {
        orientation match {
            case Orientations.Row => new EMatrix(new DenseMatrix64F(MatrixFactory.rowArrayTo2DArray(rows, columns, data)))
            case Orientations.Column => {
                val a = new DenseMatrix64F(MatrixFactory.rowArrayTo2DArray(columns, rows, data))
                CommonOps.transpose(a)
                new EMatrix(a)
            }
        }
    }

    def apply(data: Array[Array[Double]]): EMatrix = new EMatrix(new DenseMatrix64F(data))

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

    def apply(data: Product): EMatrix = {
        def size = MatrixFactory.productSize(data)
        def array = MatrixFactory.toArray(data)
        new EMatrix(new DenseMatrix64F(MatrixFactory.rowArrayTo2DArray(size._1, size._2, array)))
    }


    def apply[@specialized(Int, Long, Float, Double)B: Numeric](data: Array[B], orientation: Orientations.Orientation): EMatrix = null

    def ones(rows: Int, columns: Int): EMatrix = apply(rows, columns, 1D)

    def rand(rows: Int, columns: Int): EMatrix =
        new EMatrix(RandomMatrices.createRandom(rows, columns, new java.util.Random))

    def nans(rows: Int, columns: Int): EMatrix = apply(rows, columns, Double.NaN)

    def zeros(rows: Int, columns: Int): EMatrix = apply(rows, columns, 0D)


}
