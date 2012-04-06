package matlube


/**
 * Extension methods to a [[matlube.Matrix]]. This trait contains implementations of methods.
 */
trait MatrixEnhancements {

    self: Matrix =>

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
        if (rows == 1) {
            apply(1, i)
        }
        else if (columns == 1) {
            apply(i, 1)
        }
        else {
            throw new UnsupportedOperationException("Vector access on a Matrix without a " +
                    "singleton dimension is not permitted")
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
     * @return true if any dimension of the matrix is 1
     */
    def isVector = {
        val d = dimensions
        d._1 == 1 || d._2 == 1
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

