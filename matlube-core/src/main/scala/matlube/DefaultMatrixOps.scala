package matlube

/**
 * Implementations must define the factory
 * @author Brian Schlining
 * @since 2012-03-02
 */

trait DefaultMatrixOps[A <: Matrix[_]] {

    def factory: MatrixFactory[A]

    /**
     * Element-by-element addition, C = A + B
     */
    def plus(a: Matrix[_], b: Matrix[_]): A = {
        checkSize(a, b)
        val array = Array.ofDim[Double](a.rows * a.columns)
        var k = 0
        for (i <- 0 until a.rows; j <- 0 until a.columns) {
            array(k) = a(i, j) + b(i, j)
        }
        factory(a.rows, a.columns, array, Orientations.Row)
    }

    /**
     * Element-by-element subtraction, C = A - B
     */
    def minus(a: Matrix[_], b: Matrix[_]): A = {
        checkSize(a, b)
        val array = Array.ofDim[Double](a.rows * a.columns)
        var k = 0
        for (i <- 0 until a.rows; j <- 0 until a.columns) {
            array(k) = a(i, j) - b(i, j)
        }
        factory(a.rows, a.columns, array, Orientations.Row)
    }

    /**
     * Element-by-element multiplication, C = A.*B
     */
    def elementTimes(a: Matrix[_],  b: Matrix[_]) = {
        checkSize(a, b)
        val array = Array.ofDim[Double](a.rows * a.columns)
        var k = 0
        for (i <- 0 until a.rows; j <- 0 until a.columns) {
            array(k) = a(i, j) * b(i, j)
        }
        factory(a.rows, a.columns, array, Orientations.Row)
    }

    def checkSize(a: Matrix[_], b: Matrix[_]) {
        require(a.rows == b.rows && a.columns == b.columns,
            "Matrix dimensions must agree")
    }


    /**
     * Builds a string representation of a Matrix
     * @param a The Matrix of interest
     * @return A string representation of a Matrix
     */
    def toString(a: Matrix[_]): String = {
        val b = new StringBuilder("[")
        for (r <- 0 until a.rows) {
            if (r > 0) {
                b.append(" ")
            }
            for (c <- 0 until a.columns) {
                b.append(a(r, c)).append(" ")
            }
            b.setCharAt(b.length - 1, '\n')
        }
        b.setCharAt(b.length - 1, ']')
        b.toString()
    }

}
