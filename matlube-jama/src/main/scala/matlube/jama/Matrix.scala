package matlube.jama

import Jama.{Matrix => JMatrix}
import matlube.MutableMatrix
import org.ejml.alg.dense.decomposition.CholeskyDecomposition

class Matrix protected[jama] (protected[jama] val jmatrix: JMatrix) extends MutableMatrix {
    
    val rows = jmatrix.getRowDimension()
    val columns = jmatrix.getColumnDimension() 

    
    /**
     * @return A distinct copy of the matrix
     */
    def copy: Matrix = new Matrix(new JMatrix(jmatrix.getArrayCopy()))
    
    def apply(i: Int, j: Int): Double = jmatrix.get(i, j)
    
    
    /**
     * Get a submatrix
     * @param i0   Initial row index
     * @param i1   Final row index
     * @param j0   Initial column index
     * @param j1   Final column index
     * @return     A(i0:i1,j0:j1)
     */
    def apply(i0: Int, i1: Int, j0: Int, j1: Int): Matrix = {
        Matrix(Array.tabulate[Double](i1 - i0 + 1, j1 - j0 + 1) { (u, v) => 
            this(i0 + u, j0 + v)
        })
    }
    
    /**
    * Get a submatrix
    * @param r An array of row indices
    * @param c An array of column indices
    * @return A(r, c)
    */
    def apply(r: Array[Int], c: Array[Int]): Matrix = {
       Matrix(Array.tabulate[Double](r.size, c.size) { (u, v) => 
           this(r(u), c(v))
       })
    }
   
   /**
    * Get a submatrix
    * @param i0   Initial row index
    * @param i1   Final row index
    * @param c An array of column indices
    * @return A(i0:i1, c)
    */
    def apply(i0: Int, i1: Int, c: Array[Int]): Matrix = {
        Matrix(Array.tabulate[Double](i1 - i0 + 1, c.size) { (u, v) => 
           this(i0 + u, c(v))
        })
    }
   
   /**
    * Get a submatrix
    * @param r An array of row indices
    * @param j0   Initial column index
    * @param j1   Final column index
    * @return A(r, j0:j1)
    */
    def apply(r: Seq[Int], j0: Int, j1: Int): Matrix = {
        Matrix(Array.tabulate[Double](r.size, j1 - j0 + 1) { (u, v) => 
           this(r(u), j0 + v)
        })
    }
    
    /**
     * Set/change a value in the matrix. A(i, j) = v
     * @param i The row index
     * @param j The column index
     * @param v The value to change
     * @return
     */
    def update[A](i: Int, j: Int, v: A)(implicit numeric: Numeric[A]) {
        jmatrix.set(i, j, numeric.toDouble(v))
    }
    
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
    def update(r: Seq[Int], c: Seq[Int], that: Matrix) {
        for (i <- 0 until r.size; j <- 0 until c.size) {
            this(r(i), c(j)) =  that(i, j)
        }
    }
    
    /**
     * Set/change values in the matrix. A(r, j0:j1) = that
     * @param r The row indices to modify
     * @param j0   Initial column index
     * @param j1   Final column index
     */
    def update(r: Seq[Int], j0: Int, j1: Int, that: Matrix) {
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
    def update(i0: Int, i1: Int, c: Seq[Int], that: Matrix) {
        for (i <- i0 to i1; j <- 0 until c.size) {
            this(i, c(j)) = that(i - i0, j)   
        }
    }
    

    /** 
     * Unary minus
     * @return    -A
     */
    def unary_- : Matrix = new Matrix(jmatrix.uminus())
    
    /** 
     * C = A + B
     * @param that    another matrix
     * @return     A + B
     */    
    def +(that: Matrix) = new Matrix(jmatrix.plus(that.jmatrix))
    
    /** 
     * A = A + B
     * @param B    another matrix
     * @return     A + B
     */
    def +=(that: Matrix) = {
        jmatrix.plusEquals(that.jmatrix)
        this
    }
    
    /** 
     * C = A - B
     * @param B    another matrix
     * @return     A - B
     */
    def -(that: Matrix) = new Matrix(jmatrix.minus(that.jmatrix))
    
    /** 
     * A = A - B
     * @param B    another matrix
     * @return     A - B
     */
    def -=(that: Matrix): Matrix = {
        jmatrix.minusEquals(that.jmatrix)
        this
    }
    
    /**
     * Element-by-element multiplication, C = A.*B
     */
    def **(that: Matrix): Matrix = new Matrix(jmatrix.arrayTimes(that.jmatrix))
    
    /**
     * Element-by-element multiplication in place, A = A.*B
     */
    def **=(that: Matrix): Matrix = {
        jmatrix.arrayTimesEquals(that.jmatrix)
        this
    }
    
    /**
     * Element-by-element right division, C = A./B
     */
    def /(that: Matrix): Matrix = new Matrix(jmatrix.arrayRightDivide(that.jmatrix))
    
    /**
     * Element-by-element right division in place, A = A./B
     */
    def /=(that: Matrix): Matrix = {
        jmatrix.arrayRightDivideEquals(that.jmatrix)
        this
    }
    
    /**
     * Element-by-element left division, C = A.\B
     */
    def \(that: Matrix): Matrix = new Matrix(jmatrix.arrayLeftDivide(that.jmatrix))
    
    /**
     * Element-by-element left division in place, A = A.\B
     */
    def \=(that: Matrix): Matrix = {
        jmatrix.arrayLeftDivideEquals(that.jmatrix)
        this
    }
    
    /**
     * Multiply a matrix by a scalar, C = s*A
     */
    def *[@specialized(Int, Long, Float, Double) A](s: A)(implicit numeric: Numeric[A]) = new Matrix(jmatrix.times(numeric.toDouble(s)))
    
    /**
     * Multiply a matrix by a scalar in place, A = s*A
     */
    def *=[@specialized(Int, Long, Float, Double) A](s: A)(implicit numeric: Numeric[A]): Matrix = {
        jmatrix.timesEquals(numeric.toDouble(s))
        this
    } 
    
    /**
     * Linear algebraic matrix multiplication, A * B
     */
    def *(that: Matrix): Matrix = new Matrix(jmatrix.times(that.jmatrix))
    
    /**
     * Matrix condition (2 norm)
     * @return     ratio of largest to smallest singular value.
     */
    def cond: Double = jmatrix.cond()
    
    /**
     * Matrix determinant
     */
    def det: Double = jmatrix.det()
    
    /**
     * Access the internal two-dimensional array.
     */
    def array: Array[Array[Double]] = jmatrix.getArray()
    
    /**
     * Access a copy of the internal two-dimensional array
     */
    def arrayCopy: Array[Array[Double]] = jmatrix.getArrayCopy()
    
    def rowPackedCopy: Array[Double] = jmatrix.getRowPackedCopy()
    
    /** 
     * One norm
     * @return    maximum column sum.
     */
    def norm1: Double = jmatrix.norm1()
    
    /** 
     * Two norm
     * @return    maximum singular value.
     */
    def norm2: Double = jmatrix.norm2()
    
    /** 
     * Frobenius norm
     * @return    sqrt of sum of squares of all elements.
     */
    def normF: Double = jmatrix.normF()
    
    /** 
     * Infinity norm
     * @return    maximum row sum.
     */
    def normInf: Double = jmatrix.normInf()
    
    /**
     * Matrix rank
     * @return     effective numerical rank, obtained from SVD.
     */
    def rank: Int = jmatrix.rank()
    
    /** 
     * Matrix trace.
     * @return     sum of the diagonal elements.
     */
    def trace: Double = jmatrix.trace()
    
    /** 
     * Matrix transpose.
     * @return    A'
     */
    def transpose: Matrix = new Matrix(jmatrix.transpose())
    
    /** 
     * Cholesky Decomposition
     * @return     CholeskyDecomposition
     * @see CholeskyDecomposition
     */
    def chol: CholeskyDecomposition = new CholeskyDecomposition(this)
    
    /** 
     * Eigenvalue Decomposition
     * @return     EigenvalueDecomposition
     * @see EigenvalueDecomposition
     */
    def eig: EigenvalueDecomposition = new EigenvalueDecomposition(this)
    
    /** 
     * LU Decomposition
     * @return     LUDecomposition
     * @see LUDecomposition
     */
    def lu: LUDecomposition = new LUDecomposition(this)
    
    /** 
     * QR Decomposition
     * @return     QRDecomposition
     * @see QRDecomposition
     */
    def qr: QRDecomposition = new QRDecomposition(this)
    
    /** 
     * Singular Value Decomposition
     * @return     SingularValueDecomposition
     * @see SingularValueDecomposition
     */
    def svd: SingularValueDecomposition = new SingularValueDecomposition(this)
    
    private def checkSize(that: Matrix) {
        require(rowDimension == that.rowDimension && columnDimension == that.columnDimension, 
            "Matrix dimensions must agree")
    }
    
    def displayString(): String = {
        val b = new StringBuilder("[")
        for (r <- 0 until rowDimension) {
            if (r > 0) {
                b.append(" ")
            }
            for (c <- 0 until columnDimension) {
                b.append(this(r, c)).append(" ")
            }
            b.setCharAt(b.length() - 1, '\n')
        }
        b.setCharAt(b.length() - 1, ']')
        b.toString()
    }
    
    /** 
     * Solve A*X = B
     * @param B    right hand side
     * @return     solution if A is square, least squares solution otherwise
     */
    def solve(b: Matrix) = new Matrix(jmatrix.solve(b.jmatrix))
    
    /** 
     * Solve X*A = B, which is also A'*X' = B'
     * @param B    right hand side
     * @return     solution if A is square, least squares solution otherwise.
     */
    def solveTranspose(b: Matrix) = new Matrix(jmatrix.solveTranspose(b.jmatrix))
    
    /** 
     * Matrix inverse or pseudoinverse
     * @return     inverse(A) if A is square, pseudoinverse otherwise.
     */
    def inverse: Matrix = new Matrix(jmatrix.inverse())
    
}

object Matrix {
    
    /**
     * Creates m by n size matrix filled with the value you provide
     *
     * @param m The number of rows
     * @param n The number of columns
     * @param fillValue The fill-value for the matrix
     * @return A matrix filled with fillValue   
     */
    def apply(m: Int, n: Int, fillValue: Double = 0): Matrix = new Matrix(new JMatrix(Array.tabulate(m, n) { (u, v) => fillValue }))
    
    /**
     * Creates a new Matrix from an Array[Array[A]]. This creates a copy
     * of the array that you provide
     *
     * @tparam A A numeric type
     */
    def apply[@specialized(Int, Long, Float, Double) A](array: Array[Array[A]])(implicit numeric: Numeric[A]): Matrix = {
        new Matrix(new JMatrix(Array.tabulate(array.size, array(0).size) { (u, v) => 
            numeric.toDouble(array(u)(v)) 
        }))  
    }
    
    /**
     * Creates a new Matrix (row vector) from an Array[A]. This creates a copy
     * of the array that you provide
     *
     * @tparam A A numeric type
     */
    def apply[@specialized(Int, Long, Float, Double) A](array: Array[A])(implicit numeric: Numeric[A]): Matrix = {
        new Matrix(new JMatrix(Array.tabulate(1, array.size) { (u, v) => 
            numeric.toDouble(array(v)) 
        }))
    }
    
    
   /** 
    * Generate identity matrix
    * @param m    Number of rows.
    * @param n    Number of colums.
    * @return     An m-by-n matrix with ones on the diagonal and zeros elsewhere.
    */
   def identity(m: Int, n: Int): Matrix = {
       val a = apply(m, n, 0D)
       for (i <- 0 until m; j <- 0 until n; if i == j) {
           a(i, j) = 1D
       }
       return a
   }
   
   /** 
    * Generate matrix with random elements
    * @param m    Number of rows.
    * @param n    Number of colums.
    * @return     An m-by-n matrix with uniformly distributed random elements.
    */
   def random(m: Int, n: Int) = new Matrix(JMatrix.random(m, n))
}