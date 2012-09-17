package matlube

import java.util.Arrays
import math._
import scala.Tuple2
import scala.Numeric

trait MatrixFactory[A <: Matrix[A]]  {

    /**
     * Build a Matrix from a single dimension Array
     * @param data The array
     * @param orientation The orientation of the Array (Row means fill in array order across rows,
     *                    Column means fill in array order down columns)
     * @tparam B The type of data in the array.
     * @return A new Matrix
     */
    def apply[@specialized(Int, Long, Float, Double) B : Numeric](data: Array[B], orientation: Orientations.Orientation): A

    /**
     *
     * @param rows
     * @param columns
     * @param data
     * @param orientation
     * @tparam B
     * @return
     */
    def apply[@specialized(Int, Long, Float, Double) B : Numeric](rows: Int,  columns: Int, data: Array[B], orientation: Orientations.Orientation): A
    def apply[@specialized(Int, Long, Float, Double) B: Numeric](array: Array[Array[B]]):A
    def apply(data: Product): A
    def apply(rows: Int, columns: Int, fillValue: Double): A
    def apply(rows: Int, columns: Int): A

    /**
     * Identity matrix
     * @param rows
     * @param columns
     * @return
     */
    def eye(rows: Int,  columns: Int): A

    /**
     * Identity matrix
     * @param dimensions Tuple or (rows, columns). e.g. from matrix.size
     * @return an identity matrix of the given dimension
     */
    def eye(dimensions: Tuple2[Int, Int]): A = eye(dimensions._1, dimensions._2)

    /**
     * A Matrix of ones
     * @param rows number of rows
     * @param columns number of columns
     * @return a matrix filled with ones
     */
    def ones(rows: Int,  columns: Int): A

    /**
     * A Matrix of ones
     * @param dimensions Tuple or (rows, columns). e.g. from matrix.size
     * @return a matrix filled with ones
     */
    def ones(dimensions: Tuple2[Int, Int]): A = ones(dimensions._1, dimensions._2)

    /**
     * Random matrix
     * @param rows number of rows
     * @param columns number of columns
     * @return Matrix of random numbers between 0 ad 1
     */
    def rand(rows: Int,  columns: Int): A

    /**
     * Random matrix
     * @param dimensions Tuple or (rows, columns). e.g. from matrix.size
     * @return Matrix of random numbers between 0 ad 1
     */
    def rand(dimensions: Tuple2[Int, Int]): A = rand(dimensions._1, dimensions._2)

    /**
     * A Matrix of NaNs
     * @param rows number of rows
     * @param columns number of columns
     * @return a matrix filled with NaNs
     */
    def nans(rows: Int, columns: Int): A

    /**
     * A Matrix of NaNs
     * @param dimensions Tuple or (rows, columns). e.g. from matrix.size
     * @return a matrix filled with NaNs
     */
    def nans(dimensions: Tuple2[Int, Int]): A = nans(dimensions._1, dimensions._2)

    /**
     * A Matrix of zeros
     * @param rows number of rows
     * @param columns number of columns
     * @return a matrix filled with zeros
     */
    def zeros(rows: Int, columns: Int): A

    /**
     * A Matrix of zeros
     * @param dimensions Tuple or (rows, columns). e.g. from matrix.size
     * @return a matrix filled with zeros
     */
    def zeros(dimensions: Tuple2[Int, Int]): A = zeros(dimensions._1, dimensions._2)

    /**
     * Horizontal or Column append. In matlab we would do:
     * {{{
     *     a = [1 2 3];
     *     b = [a a];
     *     % [1 2 3 1 2 3]
     * }}}
     * In Matlube we woudl do:
     * {{{
     *     // Import MaxtrixFactory and alias as Mx
     *     val a = Mx((1d, 2d, 3d))
     *     val b = Mx.hcat(a, a)
     * }}}
     * @param a
     * @param b
     * @return
     */
    def hcat(a: A, b: A): A = {
        val (ar, ac) = a.size
        val (br, bc) = b.size
        require(ar == br, "Matrices did not have the same number of rows")
        val c = apply(ar, ac + bc)
        c(0 until ar, 0 until ac) = a
        c(0 until br, ac until (ac + bc)) = b
        c
    }

    /**
     * Vertical or row append (or concatenate). In Matlab we would do:
     * {{{
     *     a = [1 2 3];
     *     b = [a; a];
     *     %  [1 2 3
     *     %   1 2 3]
     * }}}
     * {{{
     *     val a = Mx((1d, 2d, 3d))
     *     val b = Mx.vcat(a, a)
     * }}}
     * @param a
     * @param b
     * @return
     */
    def vcat(a: A, b: A): A = {
        val (ar, ac) = a.size
        val (br, bc) = b.size
        require(ac == bc, "Matrices did not have the same number of columns")
        val c = apply(ar + br, ac)
        c(0 until ar, 0 until ac) = a
        c(ar until (ar + br), 0 until bc) = b
        c
    }

    /**
     * Replicate and tile a matrix
     * @param matrix The matrix to tile
     * @param m rows of tiles
     * @param n columns of tiles
     * @return an m by n tiling of the ''matrix''. The size of this matrix is {{{
     *      (matrix.rows * m, matrix.columns * n)
     * }}}
     */
    def repmat(matrix: Matrix[_], m: Int, n: Int) = {
        val rows = matrix.rows
        val cols = matrix.columns
        val mm = m * rows
        val nn = n * cols
        val aa = nans(mm, nn)
        for (r1 <- 0 until rows; c1 <- 0 until cols) {
            for (r0 <- 0 until m; c0 <- 0 until n) {
                val i = r1 + (r0 * rows)
                val j = c1 + (c0 * cols)
                aa(i, j) = matrix(r1, c1)
            }
        }
        aa
    }

    /**
     * generates n logarithmically-spaced points between d1 and d2 using the
     * provided base.
     *
     * @param d0 The min value
     * @param d1 The max value
     * @param n The number of points to generated
     * @param base the logarithmic base to use (default = 10)
     * @return a matrix of log-rhythmically space points.
     */
    def logspace(d0: Double,  d1: Double, n: Int, base: Double = 10D) = {
        val y = Array.ofDim[Double](n)
        val p = linspace(d0, d1, n)
        for (i <- 0 until (n - 1)) {
            y(i) = pow(base, p(i))
        }
        y(y.size - 1) = pow(base, d1)
        apply(1, n, y, Orientations.Row)
    }

    /**
     * generates n linearly-spaced points between d1 and d2.
     * @param d0 The min value
     * @param d1 The max value
     * @param n The number of points to generated
     * @return a matrix of lineraly space points.
     */
    def linspace(d0: Double, d1: Double, n: Int): A = {
        val dy = (d1 - d0) / (n - 1)
        val y = Array.tabulate[Double](n) { i => d0 + (dy * i) }
        apply(1, n, y, Orientations.Row)
    }



}

object MatrixFactory {

    /**
     * Returns the size of a series of nested tuples. Used by factory methods for converting
     * tuples to a matrix
     * @param t The tuple
     * @return
     */
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
     * Flattens out a nested tuple and returns the contents as an iterator. Used by factory
     * methods for converting tuples to a matrix
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
     */
    def toArray(t: Product): Array[Double] = flattenProduct(t).map(v =>
        v match {
            case c: Char => c.toDouble
            case b: Byte => b.toDouble
            case sh: Short => sh.toDouble
            case i: Int => i.toDouble
            case l: Long => l.toDouble
            case f: Float => f.toDouble
            case d: Double => d
            case s: String => s.toDouble
            case _ => throw new UnsupportedOperationException(v + " is not a valid element for a matrix")
        }

    ).toArray[Double]

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
