package matlube

/**
 *
 * @author Brian Schlining
 * @since 2012-03-05
 */

trait Matrixlib[A <: Matrix] {
    
    def factory: MatrixFactory[A]
    
    def applyByElement(fn: (Double,  Double, Double, Double) => Double, m0: Matrix, m1: Matrix, m2: Matrix, m3: Matrix) = {
        val r = m0.rows
        val c = m0.columns
        val mm = factory.nans(r, c)
        for (i <- 0 until r; j <- 0 until c) {
            mm(i, j) = fn(m0(i, j), m1(i, j), m2(i, j), m3(i, j))
        }
        mm
    }

    def applyByElement(fn: (Double,  Double, Double) => Double, m0: Matrix, m1: Matrix, m2: Matrix) = {
        val r = m0.rows
        val c = m0.columns
        val mm = factory.nans(r, c)
        for (i <- 0 until r; j <- 0 until c) {
            mm(i, j) = fn(m0(i, j), m1(i, j), m2(i, j))
        }
        mm
    }

    def applyByElement(fn: (Double,  Double) => Double, m0: Matrix, m1: Matrix) = {
        val r = m0.rows
        val c = m0.columns
        val mm = factory.nans(r, c)
        for (i <- 0 until r; j <- 0 until c) {
            mm(i, j) = fn(m0(i, j), m1(i, j))
        }
        mm
    }

    def applyByElement(fn: (Double) => Double, m0: Matrix) = {
        val r = m0.rows
        val c = m0.columns
        val mm = factory.nans(r, c)
        for (i <- 0 until r; j <- 0 until c) {
            mm(i, j) = fn(m0(i, j))
        }
        mm
    }

}
