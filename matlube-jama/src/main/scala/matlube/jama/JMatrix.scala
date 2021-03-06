package matlube.jama

import Jama.{Matrix => JamaMatrix}
import matlube._

class JMatrix(val delegate: JamaMatrix)
        extends Matrix[JMatrix]
        with HasDelegate[JamaMatrix] {

    val rows = delegate.getRowDimension
    val columns = delegate.getColumnDimension
    private[this] val defaultOps = JMatrixlib


    def rowArray: Array[Double] = delegate.getRowPackedCopy
    def columnArray: Array[Double] = delegate.getColumnPackedCopy


    val factory = JMatrix // JMatrix object is the factory
    /**
     * @return A distinct copy of the matrix
     */
    def copy = new JMatrix(new JamaMatrix(delegate.getArrayCopy()))

    def apply(i: Int, j: Int): Double = delegate.get(i, j)

    def apply(i: SelectAll, j: SelectAll): JMatrix = this

    def apply(i: SelectAll): JMatrix = JMatrix(rows * columns, 1, delegate.getColumnPackedCopy)

    def apply(indices: Array[Int]): JMatrix =  {
        val array = (for (i <- indices) yield apply(i))
        JMatrix(1, array.size, array)
    }

    def apply(indices: Seq[Int]): JMatrix =  {
        val array = (for (i <- indices) yield apply(i)).toArray
        JMatrix(1, array.size, array)
    }

    /**
     * Get a submatrix
     * @param r An array of row indices
     * @param c An array of column indices
     * @return A(r, c)
     */
    def apply(r: Array[Int], c: Array[Int]): JMatrix = {
        JMatrix(Array.tabulate[Double](r.size, c.size) {
            (u, v) =>
                this(r(u), c(v))
        })
    }

    def apply(r: Seq[Int], c: Seq[Int]): JMatrix = {
        JMatrix(Array.tabulate[Double](r.size, c.size) {
            (u, v) =>
                this(r(u), c(v))
        })
    }

    /**
     * Get a submatrix
     * @param r An array of row indices
     * @param j0   Initial column index
     * @param j1   Final column index
     * @return A(r, j0:j1)
     */
    def apply(r: Array[Int], j0: Int, j1: Int): JMatrix = {
        JMatrix(Array.tabulate[Double](r.size, j1 - j0 + 1) {
            (u, v) =>
                this(r(u), j0 + v)
        })
    }


    def update[@specialized(Int, Long, Float, Double) A: Numeric](i: SelectAll, j: Int, v: A) {
        val numeric = implicitly[Numeric[A]]
        val dv = numeric.toDouble(v)
        for (m <- 0 until rows) {
            update(m, j, dv)
        }
    }

    def update[@specialized(Int, Long, Float, Double) A: Numeric](i: Int, j: SelectAll, v: A) {
        val numeric = implicitly[Numeric[A]]
        val dv = numeric.toDouble(v)
        for (n <- 0 until columns) {
            update(i, n, dv)
        }
    }

    /**
     * Set/change a value in the matrix. A(i, j) = v
     * @param i The row index
     * @param j The column index
     * @param v The value to change
     * @return
     */
    def update[@specialized(Int, Long, Float, Double) A: Numeric](i: Int, j: Int, v: A) {
        val numeric = implicitly[Numeric[A]]
        delegate.set(i, j, numeric.toDouble(v))
    }


    /**
     * Unary minus
     * @return    -A
     */
    def unary_- : JMatrix = new JMatrix(delegate.uminus())

    /**
     * C = A + B
     * @param that    another matrix
     * @return     A + B
     */
    def +(that: Matrix[_]): JMatrix = that match {
        case j: JMatrix => new JMatrix(delegate.plus(j.delegate))
        case m: Matrix[_] => defaultOps.plus(this, m)
        case _ => throw new UnsupportedOperationException
    }


    def +=(that: Matrix[_]): JMatrix = {
        that match {
            case j: JMatrix => delegate.plusEquals(j.delegate)
            case _ => throw new UnsupportedOperationException
        }
        this
    }

    /**
     * C = A - B
     * @param that (B)    another matrix
     * @return     A - B
     */
    def -(that: Matrix[_]): JMatrix = that match {
        case j: JMatrix => new JMatrix(delegate.minus(j.delegate))
        case m: Matrix[_] => defaultOps.minus(this, m)
        case _ => throw new UnsupportedOperationException
    }

    /**
     * A = A - B
     * @param that (B)    another matrix
     * @return     A - B
     */
    def -=(that: Matrix[_]): JMatrix = {
        that match {
            case j: JMatrix => delegate.minusEquals(j.delegate)
            case _ => throw new UnsupportedOperationException
        }
        this
    }

    /**
     * Element-by-element multiplication, C = A.*B
     */
    def **(that: Matrix[_]): JMatrix = that match {
        case j: JMatrix => new JMatrix(delegate.arrayTimes(j.delegate))
        case m: Matrix[_] => defaultOps.elementTimes(this, m)
        case _ => throw new UnsupportedOperationException
    }

    /**
     * Element-by-element multiplication in place, A = A.*B
     */
    def **=(that: Matrix[_]): JMatrix = {
        that match {
            case j: JMatrix => delegate.arrayTimesEquals(j.delegate)
            case _ => throw new UnsupportedOperationException
        }
        this
    }

    /**
     * Element-by-element right division, C = A./B
     */
    def /(that: Matrix[_]): JMatrix = that match {
        case j: JMatrix => new JMatrix(delegate.arrayRightDivide(j.delegate))
        case _ => throw new UnsupportedOperationException
    }

    /**
     * Element-by-element right division in place, A = A./B
     */
    def /=(that: Matrix[_]): JMatrix = {
        that match {
            case j: JMatrix => delegate.arrayRightDivideEquals(j.delegate)
            case _ => throw new UnsupportedOperationException
        }
        this
    }

    /**
     * Element-by-element left division, C = A.\B
     * NOTE: Replace this usage with the more matlab-like call to 'solve'
     */
    //    def \(that: Matrix[_]): JMatrix = that match {
    //        case j: JMatrix => new JMatrix(delegate.arrayLeftDivide(j.delegate))
    //        case _ => throw new UnsupportedOperationException
    //    }

    /**
     * Element-by-element left division in place, A = A.\B
     */
    def \=(that: Matrix[_]): JMatrix = {
        that match {
            case j: JMatrix => delegate.arrayLeftDivideEquals(j.delegate)
            case _ => throw new UnsupportedOperationException
        }
        this
    }

    /**
     * Multiply a matrix by a scalar in place, A = s*A
     */
    def *=[@specialized(Int, Long, Float, Double) B: Numeric](s: B): JMatrix = {
        def numeric = implicitly[Numeric[B]]
        delegate.timesEquals(numeric.toDouble(s))
        this
    }

    /**
     * Linear algebraic matrix multiplication, A * B
     */
    def *(that: Matrix[_]): JMatrix = that match {
        case j: JMatrix => new JMatrix(delegate.times(j.delegate))
        case _ => throw new UnsupportedOperationException
    }

    /**
     * Matrix condition (2 norm)
     * @return     ratio of largest to smallest singular value.
     */
    def cond: Double = delegate.cond()

    /**
     * Matrix determinant
     */
    def det: Double = delegate.det()

    /**
     * Access the internal two-dimensional array.
     */
    def array: Array[Array[Double]] = delegate.getArray()

    /**
     * Access a copy of the internal two-dimensional array
     */
    def arrayCopy: Array[Array[Double]] = delegate.getArrayCopy()

    def rowPackedCopy: Array[Double] = delegate.getRowPackedCopy()

    /**
     * One norm
     * @return    maximum column sum.
     */
    def norm1: Double = delegate.norm1()

    /**
     * Two norm
     * @return    maximum singular value.
     */
    def norm2: Double = delegate.norm2()

    /**
     * Frobenius norm
     * @return    sqrt of sum of squares of all elements.
     */
    def normF: Double = delegate.normF()

    /**
     * Infinity norm
     * @return    maximum row sum.
     */
    def normInf: Double = delegate.normInf()


    /**
     * p-norm
     *
     * @return
     */
    def normP(p: Double): Double = throw new UnsupportedOperationException("normP is not yet implemented for Jama")

    /**
     * Matrix rank
     * @return     effective numerical rank, obtained from SVD.
     */
    def rank: Int = delegate.rank()

    /**
     * Matrix trace.
     * @return     sum of the diagonal elements.
     */
    def trace: Double = delegate.trace()

    /**
     * Matrix transpose.
     * @return    A'
     */
    def t = new JMatrix(delegate.transpose())

    /**
     * Cholesky Decomposition
     * @return     CholeskyDecomposition
     * @see CholeskyDecomposition
     */
    def chol: CholeskyDecomposition[JMatrix] = new JCholeskyDecomposition(this)

    /**
     * Eigenvalue Decomposition
     * @return     EigenvalueDecomposition
     * @see EigenvalueDecomposition
     */
    def eig: EigenvalueDecomposition[JMatrix] = new JEigenvalueDecomposition(this)

    /**
     * LU Decomposition
     * @return     LUDecomposition
     * @see LUDecomposition
     */
    def lu: LUDecomposition[JMatrix] = new JLUDecomposition(this)

    /**
     * QR Decomposition
     * @return     QRDecomposition
     * @see QRDecomposition
     */
    def qr: QRDecomposition[JMatrix] = new JQRDecomposition(this)

    /**
     * Singular Value Decomposition
     * @return     SingularValueDecomposition
     * @see SingularValueDecomposition
     */
    def svd: SingularValueDecomposition[JMatrix] = new JSingularValueDecomposition(this)


    /**
     * Solve A*X = B
     * @param b (B)    right hand side
     * @return     solution if A is square, least squares solution otherwise
     */
    def solve(b: Matrix[_]): JMatrix = b match {
        case j: JMatrix => new JMatrix(delegate.solve(j.delegate))
        case _ => throw new UnsupportedOperationException
    }


    /**
     * Solve X*A = B, which is also A'*X' = B'
     * @param b (B)    right hand side
     * @return     solution if A is square, least squares solution otherwise.
     */
    def solveTranspose(b: Matrix[_]): JMatrix = b match {
        case j: JMatrix => new JMatrix(delegate.solveTranspose(j.delegate))
        case _ => throw new UnsupportedOperationException
    }

    /**
     * Matrix inverse or pseudoinverse
     * @return     inverse(A) if A is square, pseudoinverse otherwise.
     */
    def inverse: JMatrix = new JMatrix(delegate.inverse())

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

/**
 * The JMatrix object is our implementation of the MatrixFactory.
 */
object JMatrix extends MatrixFactory[JMatrix] {

    def apply(data: Product): JMatrix = {
        def size = MatrixFactory.productSize(data)
        def array = MatrixFactory.toArray(data)
        new JMatrix(new JamaMatrix(MatrixFactory.rowArrayTo2DArray(size._1, size._2, array)))
    }

    def apply(rows: Int, columns: Int): JMatrix = apply(rows, columns, 0D)

    def apply(rows: Int, columns: Int, fillValue: Double): JMatrix = new JMatrix(new JamaMatrix(Array.tabulate(rows, columns) {(u, v) => fillValue}))

    def apply[@specialized(Int, Long, Float, Double) A: Numeric](rows: Int, columns: Int,
            data: Array[A], orientation: Orientations.Orientation = Orientations.Row): JMatrix = orientation match {
        case Orientations.Row => apply(MatrixFactory.rowArrayTo2DArray(rows, columns, data))
        case Orientations.Column => throw new UnsupportedOperationException("I haven't implemented reshaping a column " +
                " oriented matrix yet")
    }

    def eye(rows: Int, columns: Int): JMatrix = {
        val a = apply(rows, columns, 0D)
        for (i <- 0 until rows; j <- 0 until columns; if i == j) {
            a(i, j) = 1D
        }
        return a
    }


    def apply[@specialized(Int, Long, Float, Double)B: Numeric](data: Array[B], orientation: Orientations.Orientation): JMatrix = {
        orientation match {
            case Orientations.Row => apply(1, data.size, data)
            case Orientations.Column => apply(data.size, 1, data)
        }
    }

    def ones(rows: Int, columns: Int): JMatrix = apply(rows, columns, 1D)

    def rand(rows: Int, columns: Int): JMatrix = new JMatrix(JamaMatrix.random(rows, columns))

    def nans(rows: Int, columns: Int): JMatrix = apply(rows, columns, Double.NaN)

    def zeros(rows: Int, columns: Int): JMatrix = apply(rows, columns, 0)

    /**
     * Creates a new Matrix from an Array[Array[A]]. This creates a copy
     * of the array that you provide
     *
     * @tparam A A numeric type
     */
    def apply[@specialized(Int, Long, Float, Double) A: Numeric](array: Array[Array[A]]): JMatrix = {
        val numeric = implicitly[Numeric[A]]
        new JMatrix(new JamaMatrix(Array.tabulate(array.size, array(0).size) {
            (u, v) =>
                numeric.toDouble(array(u)(v))
        }))
    }

}
