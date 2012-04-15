package matlube

import scala.math._

/**
 *
 * @author Brian Schlining
 * @since 2012-03-05
 */
trait Matrixlib[A <: Matrix[_]] {

    def factory: MatrixFactory[A]

    def applyByElement(fn: (Double,  Double, Double, Double) => Double, m0: Matrix[_], m1: Matrix[_], m2: Matrix[_], m3: Matrix[_]) = {
        val r = m0.rows
        val c = m0.columns
        val mm = factory.nans(r, c)
        for (i <- 0 until r; j <- 0 until c) {
            mm(i, j) = fn(m0(i, j), m1(i, j), m2(i, j), m3(i, j))
        }
        mm
    }

    def applyByElement(fn: (Double,  Double, Double) => Double, m0: Matrix[_], m1: Matrix[_], m2: Matrix[_]) = {
        val r = m0.rows
        val c = m0.columns
        val mm = factory.nans(r, c)
        for (i <- 0 until r; j <- 0 until c) {
            mm(i, j) = fn(m0(i, j), m1(i, j), m2(i, j))
        }
        mm
    }

    def applyByElement(fn: (Double,  Double) => Double, m0: Matrix[_], m1: Matrix[_]) = {
        val r = m0.rows
        val c = m0.columns
        val mm = factory.nans(r, c)
        for (i <- 0 until r; j <- 0 until c) {
            mm(i, j) = fn(m0(i, j), m1(i, j))
        }
        mm
    }

    def applyByElement(fn: (Double) => Double, m0: Matrix[_]) = {
        val r = m0.rows
        val c = m0.columns
        val mm = factory.nans(r, c)
        for (i <- 0 until r; j <- 0 until c) {
            mm(i, j) = fn(m0(i, j))
        }
        mm
    }

    def repmat(matrix: Matrix[_], m: Int, n: Int) = {
        val rows = matrix.rows
        val cols = matrix.columns
        val mm = m * rows
        val nn = n * cols
        val aa = factory.nans(mm, nn)
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
     * @param base the logarithmic base to use
     * @return a matrix of lineraly space points.
     */
    def logspace(d0: Double,  d1: Double, n: Int, base: Double = 10D) = {
        val y = Array.ofDim[Double](n)
        val p = linspace(d0, d1, n)
        for (i <- 0 until (y.size - 1)) {
            y(i) = pow(base, p(1, i))
        }
        y(y.size - 1) = pow(base, d1)
        factory(1, n, y, Orientations.Row)
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
        factory(1, n, y, Orientations.Row)
    }




}
