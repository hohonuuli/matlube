package matlube

/**
 * Base trait for all matrices. This trait defines methods for an immutable matrix. All methods
 * should return a new Matris
 *
 * No method implementations will be put in here so that it is
 * essentially a pure interface. Implementations should go into [[matlube.MatrixEnhancements]]
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

}