package matlube

trait MutableMatrix extends Matrix {

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
    def *=[@specialized(Int, Long, Float, Double) A: Numeric](s: A): Matrix

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
            this(r(i), c(j)) =  that(i, j)
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
    
}