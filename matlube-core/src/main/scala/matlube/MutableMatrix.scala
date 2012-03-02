package matlube

trait MutableMatrix[A <: MutableMatrix] extends Matrix[A] {

    /** 
     * A = A + B
     * @param that (B)    another matrix
     * @return     A + B (actually returns this)
     */
    def +=(that: A): A

    /** 
     * A = A - B
     * @param that (B)    another matrix
     * @return     A - B
     */
    def -=(that: A): A

    /**
     * Element-by-element multiplication in place, A = A.*B
     */
    def **=(that: A): A

    /**
     * Element-by-element right division in place, A = A./B
     */
    def /=(that: A): A

    /**
     * Element-by-element left division in place, A = A.\B
     */
    def \=(that: A): A

     /**
     * Multiply a matrix by a scalar in place, A = s*A
     */
    def *=[@specialized(Int, Long, Float, Double) A: Numeric](s: A): A

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