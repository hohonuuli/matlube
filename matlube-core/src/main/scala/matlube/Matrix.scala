package matlube

import scala.Array
import scala.math.{floor, max}

/**
 * Base trait for all matrices. This trait defines methods for an immutable matrix. All methods
 * should return a new Matrix
 *
 */
trait Matrix[A <: Matrix[A]] extends VectorOps[A] {

    /**
     *
     * @return The factory used to create new instances of this type of Matrix
     */
    def factory: MatrixFactory[A]

    def copy: A

    /**
     * Unary minus
     * @return    -A
     */
    def unary_- : A

    /**
     * C = A + B
     * @param that    another matrix
     * @return     A + B
     */
    def +(that: Matrix[_]): A

    /**
     * C = A - B
     * @param that    another matrix
     * @return     A - B
     */
    def -(that: Matrix[_]): A

    /**
     * Element-by-element multiplication, C = A.*B. 
     *
     * **Tip:** The sum of the result of '**' between 2 vectors is also known as the 
     * __dot product__. E.g.
     * {{{
     * val a = // Matrix of [1 2 3]
     * val b = // Matrix of [1 0 1]
     * val dotProduct = ((a ** b) rowArray) sum // 4
     * *}}}
     *
     * **REMEMBER** The dot product a . b is equal to the signed length of the 
     * projection of b onto any line parallel to a, multiplied by the length of
     * a
     */
    def **(that: Matrix[_]): A

    /**
     * Element-by-element right division, C = A./B
     */
    def /(that: Matrix[_]): A


    /**
     * Linear algebraic matrix multiplication, A * B
     */
    def *(that: Matrix[_]): A

    /**
     * Matrix transpose.
     * @return    A'
     */
    def t: A

    /**
     * Solve A*X = B
     * @param b    right hand side
     * @return     solution if A is square, least squares solution otherwise
     */
    def solve(b: Matrix[_]): A

    /**
     * Solve A*X = B . Same syntax as Matlab
     */
    def \(that: Matrix[_]): A = solve(that)

    /**
     * Solve X*A = B, which is also A'*X' = B'
     * @param b    right hand side
     * @return     solution if A is square, least squares solution otherwise.
     */
    def solveTranspose(b: Matrix[_]): A

    /**
     * Matrix inverse or pseudoinverse
     * @return     inverse(A) if A is square, pseudoinverse otherwise.
     */
    def inverse: A

    /**
     * Retrieve a value from the Matrix
     * @param i The row index
     * @param j The column index
     * @return The value of A(i, j)
     */
    def apply(i: Int, j: Int): Double

    /**
     *
     * @param i
     * @param j
     * @return Just returns this. Same as Matlab
     */
    def apply(i: SelectAll, j: SelectAll): A

    /**
     * Returns a column matrix of all matrix elements in column order
     * @param i
     * @return
     */
    def apply(i: SelectAll): A

    /**
     * Retrieve a row from the matrix
     * @param i The [[matlube.SelectAll]] constant ( or ::)
     * @param j The column index
     * @return A(:, j)
     */
    def apply(i: SelectAll, j: Int): A = apply(0 until rows, Seq(j))

    /**
     * Get a submatrix
     * @param i The [[matlube.SelectAll]] constant ( or ::)
     * @param j The column indices
     * @return A(:, [j0 j1 ... jn])
     */
    def apply(i: SelectAll, j: Seq[Int]): A = apply(0 until rows, j)

    /**
     * Get a submatrix
     * @param i The [[matlube.SelectAll]] constant ( or ::)
     * @param j The column indices
     * @return A(:, [j0 j1 ... jn])
     */
    def apply(i: SelectAll, j: Array[Int]): A  = apply(0 until rows, j)


    /**
     * Retrive a column from the matrix
     * @param i The row index
     * @param j The [[matlube.SelectAll]] constant ( or ::)
     * @return A(i, :)
     */
    def apply(i: Int, j: SelectAll): A = apply(Seq(i), 0 until columns)

    /**
     * Get a submatrix
     * @param i The row index
     * @param j The [[matlube.SelectAll]] constant ( or ::)
     * @return A(i, :)
     */
    def apply(i: Seq[Int], j: SelectAll): A = apply(i, 0 until columns)

    /**
     * Get a submatrix
     * @param i The row index
     * @param j The [[matlube.SelectAll]] constant ( or ::)
     * @return A(i, :)
     */
    def apply(i: Array[Int], j: SelectAll): A = apply(i, 0 until columns)

    /**
     * Get a submatrix
     * @param r An array of row indices
     * @param c An array of column indices
     * @return A(r, c)
     */
    def apply(r: Array[Int], c: Array[Int]): A

    /**
     * Get a submatrix
     * @param r A Seq of row indices
     * @param c An Seq of column indices
     * @return A(r, c)
     */
    def apply(r: Seq[Int], c: Seq[Int]): A

    /**
     * Single index access. Order of access is like so:
     * {{{
     *     [1 5  9
     *      2 6 10
     *      3 7 11
     *      4 8 12]
     * }}}
     * @param i The index of the value to return. Like Matlab- the index procsedes down the first column
     *          then the 2nd column and so on
     * @return
     */
    def apply(i: Int): Double = {
        val r = i % rows
        val c = (i - r) / rows
        apply(r, c)
    }

    /**
     * Matlab-style access. You can use ranges to extract an array. You'll then have to
     * use a MatrixFactory to convert it to a Matrix ... sorry. Here's an example:
     * {{{
     * val m = JMatrix.random(10, 10)
     * val subArray = m(2 to 21 by 3)
     * val rowMatris = JMatrix(subArray) // default is row matrix OR
     * val colMatrix = JMatrix(subArray, Orientations.Column) // specify as a column
     * }}}
     *
     *
     * @param indices Sequence of indices
     * @return A subarray (Matlab-style
     */
    def apply(indices: Seq[Int]): A

    def apply(indices: Array[Int]): A

    /**
     * A = A + B
     * @param that (B)    another matrix
     * @return     A + B (actually returns this)
     */
    def +=(that: Matrix[_]): A

    /**
     * A = A - B
     * @param that (B)    another matrix
     * @return     A - B
     */
    def -=(that: Matrix[_]): A

    /**
     * Element-by-element multiplication in place, A = A.*B
     */
    def **=(that: Matrix[_]): A

    /**
     * Element-by-element right division in place, A = A./B
     */
    def /=(that: Matrix[_]): A

    /**
     * Element-by-element left division in place, A = A.\B
     */
    def \=(that: Matrix[_]): A

    /**
     * Multiply a matrix by a scalar in place, A = s*A
     */
    //def *[@specialized(Int, Long, Float, Double) B: Numeric](s: B): A

    /**
     * Multiply a matrix by a scalar in place, A = s*A
     */
    def *=[@specialized(Int, Long, Float, Double) B: Numeric](s: B): A


    /**
     *
     * @return The number of rows in the matrix
     */
    def rows: Int

    /**
     *
     * @return The number of columns
     */
    def columns: Int

    /**
     * {{{
     *     // Sum each row
     *     val m = // Some matrix
     *     val m.rowIterator.map { _.iterator.sum }
     * }}}
     * @return An iterator over each row of a matrix
     */
    def rowIterator: Iterator[A] = new Iterator[A] {
        @volatile var currentRowIdx = 0

        def hasNext: Boolean = currentRowIdx < rows

        def next(): A = {
            this.synchronized {
                val row = apply(Array(currentRowIdx), 0 until columns)
                currentRowIdx += 1
                row
            }
        }
    }

    /**
     * {{{
     *     // Sum each column
     *     val m = // Some matrix
     *     val m.columnIterator.map { _.iterator.sum }
     * }}}
     * @return An iterator over each column of a matrix
     */
    def columnIterator: Iterator[A] = new Iterator[A] {
        @volatile var currentColumnIdx = 0

        def hasNext: Boolean = currentColumnIdx < columns

        def next(): A = {
            this.synchronized {
                val column = apply(0 until rows, Array(currentColumnIdx))
                currentColumnIdx += 1
                column
            }
        }
    }

    /**
     *
     * @return The underlying data in a row-oriented array
     */
    def rowArray: Array[Double]

    /**
     *
     * @return The underlying data in a column-oriented array
     */
    def columnArray: Array[Double]


    /**
     * Matrix condition (2 norm)
     * @return     ratio of largest to smallest singular value.
     */
    def cond: Double

    /**
     * Matrix determinant
     */
    def det: Double

    /**
     * One norm
     * @return    maximum column sum.
     */
    def norm1: Double

    /**
     * Two norm. This is also known as the Magnitude ||M|| or Euclidean distance
     * of a matrix. (e.g. sqrt of the sum of squares). Unit vectors will have
     * a magnitude of 1. TO convert a vector to a unit vector simple divide the 
     * vector by its magnitude
     *
     * @return    maximum singular value.
     */
    def norm2: Double

    /**
     * Frobenius norm
     * @return    sqrt of sum of squares of all elements.
     */
    def normF: Double

    /**
     * Infinity norm
     * @return    maximum row sum.
     */
    def normInf: Double

    /**
     * Matrix rank
     * @return     effective numerical rank, obtained from SVD.
     */
    def rank: Int

    /**
     * Matrix trace.
     * @return     sum of the diagonal elements.
     */
    def trace: Double


    /* Cholesky Decomposition
       * @return     CholeskyDecomposition
       * @see CholeskyDecomposition
       */
    def chol: CholeskyDecomposition[A]

    /**
     * Eigenvalue Decomposition
     * @return     EigenvalueDecomposition
     * @see EigenvalueDecomposition
     */
    def eig: EigenvalueDecomposition[A]

    /**
     * LU Decomposition
     * @return     LUDecomposition
     * @see LUDecomposition
     */
    def lu: LUDecomposition[A]

    /**
     * QR Decomposition
     * @return     QRDecomposition
     * @see QRDecomposition
     */
    def qr: QRDecomposition[A]

    /**
     * Singular Value Decomposition
     * @return     SingularValueDecomposition
     * @see SingularValueDecomposition
     */
    def svd: SingularValueDecomposition[A]

    /**
     * This is equivalent to matlab's ''size''
     * @return The size of the matrix (rows, columns)
     */
    def size = (rows, columns)

    def length = max(rows, columns)

    /**
     * default is true. If you make an immutable subclass override this to be false.
     * @return
     */
    def isMutable = true

    /**
     * Set/change a value in the matrix. A(i, j) = v
     * @param i The row index
     * @param j The column index
     * @param v The value to change
     * @return
     */
    def update[@specialized(Int, Long, Float, Double) B: Numeric](i: Int, j: Int, v: B): Unit

    def update[@specialized(Int, Long, Float, Double) B: Numeric](i: SelectAll, j: Int, v: B): Unit

    def update[@specialized(Int, Long, Float, Double) B: Numeric](i: Int, j: SelectAll, v: B): Unit

    def update[@specialized(Int, Long, Float, Double) B: Numeric](i: Int, v: B) {
        val r = i % rows
        val c = (i - r) / rows
        update(r, c, v)
    }

    /**
     * Set/change values in the matrix. A(r, c) = that
     * @param r The row indices to modify
     * @param c The column indices to modify
     * @param that The matrix containing the new values
     */
    def update(r: Array[Int], c: Array[Int], that: Matrix[_]) {
        for (i <- 0 until r.size; j <- 0 until c.size) {
            this(r(i), c(j)) = that(i, j)
        }
    }

    def update(r: Seq[Int], c: Seq[Int], that: Matrix[_]) {
        for (i <- 0 until r.size; j <- 0 until c.size) {
            this(r(i), c(j)) = that(i, j)
        }
    }


    /**
     * Iterate over all elements in a matrix. The iteration is columne oriented. So values
     * are accessed as:
     * {{{
     * [1 5  9
     *  2 6 10
     *  3 7 11
     *  4 8 12]
     * }}}
     * @return An iterator over all the values in the Matrix. Not really thread-safe so don't
     *         share this iterator between threads.
     */
    def iterator: Iterator[Double] =  new Iterator[Double] {

        @volatile private[this] var currentIdx = 0;
        private[this] val maxIdx = rows * columns - 1
        def hasNext: Boolean = currentIdx <= maxIdx

        def next(): Double = {
            this.synchronized {
                val n = apply(currentIdx)
                currentIdx += 1
                n
            }
        }
    }

    /**
     *
     * @return true if the matrix contains a single value
     */
    def isScalar = {
        val d = size
        d._1 == 1 && d._2 == 1
    }

    /**
     *
     * @return true if any dimension of the matrix is 1 and the other dimension is greater than 1
     */
    def isVector = {
        val d = size
        (d._1 == 1 && d._2 > 1) || (d._1 > 1 && d._2 == 1)
    }

    /**
     * Map the rows or columns of a vector to some value
     * @param fn Function that operates on the individual rows or columns (represented as Matrices/Vectors)
     * @param orientation The axis to operate across. If it's [[matlube.Orientations.Row]] then each
     *  row will be mapped to a value, likewise [[matlube.Orientations.Column]] will map each
     *  column to a value. The default is Column.
     * @tparam B The type to map to.
     * @return An array of values
     */
    def map[B : ClassManifest](fn: (A) => B, orientation:Orientations.Value = Orientations.Column): Array[B] = {
        val iterator = orientation match {
            case Orientations.Row => rowIterator
            case Orientations.Column => columnIterator
        }
        iterator.map(fn(_)).toArray
    }


    /**
     *
     * @return The matrix as a String. Not recommended to use for large matrices.
     */
    def asString(): String = {
        val b = new StringBuilder("[")
        for (r <- 0 until rows) {
            if (r > 0) {
                b.append(" ")
            }
            for (c <- 0 until columns) {
                b.append(apply(r, c)).append(" ")
            }
            b.setCharAt(b.length - 1, '\n')
        }
        b.setCharAt(b.length - 1, ']')
        b.toString()
    }

}