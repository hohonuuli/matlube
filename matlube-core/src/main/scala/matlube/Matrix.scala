package matlube

/**
 * Base trait for all matrices. This trait defines methods for an immutable matrix. All methods
 * should return a new Matrix
 *
 */
trait Matrix {

    def copy: Matrix

    /**
     * Unary minus
     * @return    -A
     */
    def unary_- : Matrix

    /**
     * C = A + B
     * @param that    another matrix
     * @return     A + B
     */
    def +(that: Matrix): Matrix

    /**
     * C = A - B
     * @param that    another matrix
     * @return     A - B
     */
    def -(that: Matrix): Matrix

    /**
     * Element-by-element multiplication, C = A.*B
     */
    def **(that: Matrix): Matrix

    /**
     * Element-by-element right division, C = A./B
     */
    def /(that: Matrix): Matrix

    /**
     * Element-by-element left division, C = A.\B
     */
    def \(that: Matrix): Matrix

    /**
     * Multiply a matrix by a scalar, C = s*A
     */
    //def *[@specialized(Int, Long, Float, Double) A: Numeric](s: A): Matrix

    /**
     * Linear algebraic matrix multiplication, A * B
     */
    def *(that: Matrix): Matrix

    /**
     * Matrix transpose.
     * @return    A'
     */
    def t: Matrix

    /**
     * Solve A*X = B
     * @param b    right hand side
     * @return     solution if A is square, least squares solution otherwise
     */
    def solve(b: Matrix): Matrix

    /**
     * Solve X*A = B, which is also A'*X' = B'
     * @param b    right hand side
     * @return     solution if A is square, least squares solution otherwise.
     */
    def solveTranspose(b: Matrix): Matrix

    /**
     * Matrix inverse or pseudoinverse
     * @return     inverse(A) if A is square, pseudoinverse otherwise.
     */
    def inverse: Matrix



    def apply(i: SelectAll, j: Int): Matrix

    def apply(i: Int, j: SelectAll): Matrix

    /**
     * Get a submatrix
     * @param i0   Initial row index
     * @param i1   Final row index
     * @param j0   Initial column index
     * @param j1   Final column index
     * @return     A(i0:i1,j0:j1)
     */
    def apply(i0: Int, i1: Int, j0: Int, j1: Int): Matrix

    /**
     * Get a submatrix
     * @param r An array of row indices
     * @param c An array of column indices
     * @return A(r, c)
     */
    def apply(r: Array[Int], c: Array[Int]): Matrix

    def apply(r: SelectAll, c: Array[Int]): Matrix

    def apply(r: Array[Int], c: SelectAll): Matrix

    /**
     * Get a submatrix
     * @param i0   Initial row index
     * @param i1   Final row index
     * @param c An array of column indices
     * @return A(i0:i1, c)
     */
    def apply(i0: Int, i1: Int, c: Array[Int]): Matrix

    def apply(i0: Int, i1: Int, c: SelectAll): Matrix

    /**
     * Get a submatrix
     * @param r An array of row indices
     * @param j0   Initial column index
     * @param j1   Final column index
     * @return A(r, j0:j1)
     */
    def apply(r: SelectAll, j0: Int, j1: Int): Matrix

    /**
     * A = A + B
     * @param that (B)    another matrix
     * @return     A + B (actually returns this)
     */
    def +=(that: Matrix): Matrix

    /**
     * A = A - B
     * @param that (B)    another matrix
     * @return     A - B
     */
    def -=(that: Matrix): Matrix

    /**
     * Element-by-element multiplication in place, A = A.*B
     */
    def **=(that: Matrix): Matrix

    /**
     * Element-by-element right division in place, A = A./B
     */
    def /=(that: Matrix): Matrix

    /**
     * Element-by-element left division in place, A = A.\B
     */
    def \=(that: Matrix): Matrix

    /**
     * Multiply a matrix by a scalar in place, A = s*A
     */
    def *[@specialized(Int, Long, Float, Double) A: Numeric](s: A): Matrix

    /**
     * Multiply a matrix by a scalar in place, A = s*A
     */
    def *=[@specialized(Int, Long, Float, Double) A: Numeric](s: A): Matrix


    def rows: Int

    def columns: Int


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
     * Two norm
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


    def apply(i: Int, j: Int): Double


    /**
     * Set/change a value in the matrix. A(i, j) = v
     * @param i The row index
     * @param j The column index
     * @param v The value to change
     * @return
     */
    def update[@specialized(Int, Long, Float, Double) A: Numeric](i: Int, j: Int, v: A): Unit

    def update[@specialized(Int, Long, Float, Double) A: Numeric](i: SelectAll, j: Int, v: A): Unit

    def update[@specialized(Int, Long, Float, Double) A: Numeric](i: Int, j: SelectAll, v: A): Unit

    /* Cholesky Decomposition
       * @return     CholeskyDecomposition
       * @see CholeskyDecomposition
       */
    def chol: CholeskyDecomposition

    /**
     * Eigenvalue Decomposition
     * @return     EigenvalueDecomposition
     * @see EigenvalueDecomposition
     */
    def eig: EigenvalueDecomposition

    /**
     * LU Decomposition
     * @return     LUDecomposition
     * @see LUDecomposition
     */
    def lu: LUDecomposition

    /**
     * QR Decomposition
     * @return     QRDecomposition
     * @see QRDecomposition
     */
    def qr: QRDecomposition

    /**
     * Singular Value Decomposition
     * @return     SingularValueDecomposition
     * @see SingularValueDecomposition
     */
    def svd: SingularValueDecomposition


    def dimensions = (rows, columns)

    /**
     * default is true. If you make an immutable subclass override this to be false.
     * @return
     */
    def isMutable = true

    /**
     * Set/change values in the matrix. A(i0:i1, j0:j1) = that
     * @param i0   Initial row index
     * @param i1   Final row index
     * @param j0   Initial column index
     * @param j1   Final column index
     * @param that The matrix containing the values that are to be inserted
     *      into the existing matrix
     *
     */
    def update(i0: Int, i1: Int, j0: Int, j1: Int, that: Matrix) {
        for (i <- i0 to i1; j <- j0 to j1) {
            this(i, j) = that(i - i0, j - j0)
        }
    }

    /**
     * Set/change values in the matrix. A(r, c) = that
     * @param r The row indices to modify
     * @param c The column indices to modify
     * @param that The matrix containing the new values
     */
    def update(r: Array[Int], c: Array[Int], that: Matrix) {
        for (i <- 0 until r.size; j <- 0 until c.size) {
            this(r(i), c(j)) = that(i, j)
        }
    }

    /**
     * Set/change values in the matrix. A(r, j0:j1) = that
     * @param r The row indices to modify
     * @param j0   Initial column index
     * @param j1   Final column index
     */
    def update(r: Array[Int], j0: Int, j1: Int, that: Matrix) {
        for (i <- 0 until r.size; j <- j0 to j1) {
            this(r(i), j) = that(i, j - j0)
        }
    }

    /**
     *  Set/change values in the matrix. A(i0:i1, c) = that
     * @param i0   Initial row index
     * @param i1   Final row index
     * @param c An array of column indices
     */
    def update(i0: Int, i1: Int, c: Array[Int], that: Matrix) {
        for (i <- i0 to i1; j <- 0 until c.size) {
            this(i, c(j)) = that(i - i0, j)
        }
    }

    /**
     * Vector access. If one dimension of the matrix is 1, then we access values using
     * a single index.
     * @param i
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
    def apply(indices: Seq[Int]): Array[Double] = (for (i <- indices) yield apply(i)).toArray

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
            private[this] val maxIdx = size - 1
            def hasNext: Boolean = currentIdx < maxIdx

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
        val d = dimensions
        d._1 == 1 && d._2 == 1
    }

    /**
     *
     * @return true if any dimension of the matrix is 1 and the other dimension is greater than 1
     */
    def isVector = {
        val d = dimensions
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
    def map[B : ClassManifest](fn: (Matrix) => B, orientation:Orientations.Value = Orientations.Column): Array[B] = {
        val vectorView = new VectorView(this, orientation)
        vectorView.map(fn)
    }

}