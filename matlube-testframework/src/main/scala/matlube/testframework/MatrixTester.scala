package matlube.test

import matlube._
import matlube.{Matrix}
import scala.math._


/**
 * Runs test on a specific matrix [1 2;3 4]. The Matrix can be any implementation; this class just
 * verifies that all the basic methods work as expected.
 *
 * @author Brian Schlining
 * @since 2012-05-04
 */

class MatrixTester[A <: Matrix[_]](matrix: A) {

    private[this] val (rows, columns) = matrix.size

    private[this] val tolerance = 0.0000000000001

    protected def testCopy {
        val b = matrix.copy.asInstanceOf[A]
        for (i <- 0 until rows; j <- 0 until columns) {
            assert(matrix(i, j) == b(i, j), "Copy failed")
        }

        // Change b and make sure a isn't changed
        if (b.isMutable) {
            b(0, 0) = 10
            assert(matrix(0, 0) == 1)
        }
    }

    protected def testUnaryMinus {
        val b = (-matrix).asInstanceOf[A]
        for (i <- 0 until rows; j <- 0 until columns) {
            assert(matrix(i, j) == -(b(i, j)), "unary minus failed")
        }
    }

    protected def testAdd {
        val b = (matrix + matrix).asInstanceOf[A]
        for (i <- 0 until rows; j <- 0 until columns) {
            assert(2 * matrix(i, j) == b(i, j), "add failed")
        }
    }

    protected def testMinus {
        val b = (matrix - matrix).asInstanceOf[A]
        for (i <- 0 until rows; j <- 0 until columns) {
            assert(0 == b(i, j), "minus failed")
        }
    }

    protected def testDotMult {
        val b = (matrix ** matrix).asInstanceOf[A]
        for (i <- 0 until rows; j <- 0 until columns) {
            assert(matrix(i, j) * matrix(i, j) == b(i, j), "dot multiply (**) failed")
        }
    }

    protected def testRightDiv {
        val b = (matrix / matrix).asInstanceOf[A]
        for (i <- 0 until rows; j <- 0 until columns) {
            assert(1 == b(i, j), "right divide failed")
        }
    }

    protected def testLeftDiv {
        val b = (matrix / matrix).asInstanceOf[A]
        for (i <- 0 until rows; j <- 0 until columns) {
            assert(1 == b(i, j), "left divide failed")
        }
    }

    protected def testMult {
        val b = (matrix * matrix).asInstanceOf[A]
        chk(b(0), 7)
        chk(b(1), 15)
        chk(b(2), 10)
        chk(b(3), 22)
    }

    protected def testTranspose {
        val b = matrix.t.asInstanceOf[A]
        assert(b(0, 0) == 1 && b(0, 1) == 3 && b(1, 0) == 2 && b(1, 1) == 4)
    }

    protected def testInverse {
        val b = matrix.inverse.asInstanceOf[A]
        chk(b(0), -2)
        chk(b(1), 1.5)
        chk(b(2), 1)
        chk(b(3), -0.5)
    }

    private def chk(a: Double, b: Double) {
        assert(abs(a - b) < tolerance)
    }

    protected def testSelectAll {
        val b = matrix(::, ::).asInstanceOf[A]
        chk(b(0), matrix(0))
        chk(b(1), matrix(1))
        chk(b(2), matrix(2))
        chk(b(3), matrix(3))

        val c = matrix(0, ::).asInstanceOf[A]
        val (rc, cc) = c.size
        assert(rc == 1 && cc == 2)
        chk(c(0, 0), 1)
        chk(c(0, 1), 2)

        val r = matrix(::, 0).asInstanceOf[A]
        val (rr, cr) = r.size
        assert(rr == 2 && cr == 1)
        chk(r(0, 0), 1)
        chk(r(1, 0), 3)

        val a = matrix(::).asInstanceOf[A]
        val (ra, ca) = a.size
        assert(ra == 4 && ca == 1)
        chk(a(0, 0), 1)
        chk(a(1, 0), 3)
        chk(a(2, 0), 2)
        chk(a(3, 0), 4)

    }

    protected def testMutableAdd {
        val b = matrix.copy.asInstanceOf[A]
        b += b
        chk(b(0), 2)
        chk(b(1), 6)
        chk(b(2), 4)
        chk(b(3), 8)
    }

    protected def testMutableMinus {
        val b = matrix.copy.asInstanceOf[A]
        b -= b
        chk(b(0), 0)
        chk(b(1), 0)
        chk(b(2), 0)
        chk(b(3), 0)
    }


    protected def testMutableDotMult {
        val b = matrix.copy.asInstanceOf[A]
        b **= b
        chk(b(0), 1)
        chk(b(1), 9)
        chk(b(2), 4)
        chk(b(3), 16)
    }

    protected def testIterator {
        val b = matrix.copy.asInstanceOf[A]
        val it = b.iterator
        val total = it.sum
        chk(total, 10)
    }

}

object MatrixTester {

    def run[A <: Matrix[_]](matrix: A) {
        val (r, c) = matrix.size
        require(r == 2, "Matrix must have 2 rows")
        require(c == 2, "Matrix must have 2 columns")
        require(matrix(0, 0) == 1 && matrix(0, 1) == 2 && matrix(1, 0) == 3 && matrix(1, 1) == 4,
            "Expected [1 2; 3 4]")
        val matrixTester = new MatrixTester(matrix)
        matrixTester.testCopy
        matrixTester.testUnaryMinus
        matrixTester.testAdd
        matrixTester.testMinus
        matrixTester.testDotMult
        matrixTester.testRightDiv
        matrixTester.testLeftDiv
        matrixTester.testMult
        matrixTester.testTranspose
        matrixTester.testInverse
        matrixTester.testSelectAll
        matrixTester.testMutableAdd
        matrixTester.testMutableMinus
        matrixTester.testMutableDotMult
        matrixTester.testIterator
    }

}
