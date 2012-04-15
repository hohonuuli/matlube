package matlube

import java.util.Arrays

trait MatrixFactory[A <: Matrix[_]]  {

    def apply[@specialized(Int, Long, Float, Double) B : Numeric](data: Array[B], orientation: Orientations.Orientation): A
    def apply[@specialized(Int, Long, Float, Double) B : Numeric](rows: Int,  columns: Int, data: Array[B], orientation: Orientations.Orientation): A
    def apply(data: Product): A
    def apply(rows: Int, columns: Int): A
    def apply(rows: Int, columns: Int, fillValue: Double): A

    def identity(rows: Int,  columns: Int): A

    def ones(rows: Int,  columns: Int): A

    def random(rows: Int,  columns: Int): A

    def nans(rows: Int, columns: Int): A

    def zeros(rows: Int, columns: Int): A

}

object MatrixFactory {

    def productSize(t: Product): (Int, Int) = {
        val a = t.productArity
        val one = t.productElement(0)
        if (one.isInstanceOf[Product]) {
            val b = one.asInstanceOf[Product].productArity
            (a,  b)
        }
        else {
            (1, a)
        }
    }

    /**
     * Flattens out a nested tuple and returns the contents as an iterator
     */
    def flattenProduct(t: Product): Iterator[Any] = t.productIterator.flatMap {
        case p: Product => flattenProduct(p)
        case x => Iterator(x)
    }

    /**
     * Convert a nested tuple to a flattened row-oriented array (See [[matlube.Orientations]]).
     * This assumes that all values in the tuple are of the same type. Usage is:
     * {{{
     *  val t = ((1, 2, 3), (4, 5, 6))
     *  val a = MatrixFactory.toArray[Int](t)
     *  // a: Array[Int] = Array(1, 2, 3, 4, 5, 6)
     * }}}
     *
     * @param t The tuple to convert to an array
     * @tparam A The type that the tuple contains. 
     */
    def toArray[A: ClassManifest](t: Product) = flattenProduct(t).map( _.asInstanceOf[A]).toArray

    /**
     * Create a row oriented (See [[matlube.Orientations]]) array that can be used by a
     * [[matlube.MatrixFactory]] to create an identity [[matlube.Matrix]]
     * @param r The number of rows
     * @param c The number of columns
     * @return A row-oreiented array of ones and zeros suitable for creating an identiy Matrix
     */
    def identityArray(r: Int, c: Int): Array[Double] = {
        val array = Array.ofDim[Double](r * c)
        for (i <- 0 until r; j <- 0 until c; if (i == j)) {
            val idx = i * c + j
            array(idx) = 1
        }
        array
    }

    def rowArrayTo2DArray[@specialized(Int, Long, Float, Double) A: Numeric](m: Int, n: Int,
            rowArray: Array[A]) = {
        require(rowArray.size == m * n)
        val numeric = implicitly[Numeric[A]]
        val newArray = Array.ofDim[Double](m, n)
        for (i <- 0 until m; j <- 0 until n) {
            val idx = i * n + j
            newArray(i)(j) = numeric.toDouble(rowArray(idx))
        }
        newArray
    }

    // TODO implement a tabulate method (ala array)


}
