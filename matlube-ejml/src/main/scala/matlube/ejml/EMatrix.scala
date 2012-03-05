package matlube.ejml

import org.ejml.data.DenseMatrix64F
import matlube.{SelectAll, MatrixDelegate, Matrix}


/**
 *
 * @author Brian Schlining
 * @since 2012-03-02
 */

class EMatrix protected[ejml] (val delegate: DenseMatrix64F) extends Matrix with MatrixDelegate[DenseMatrix64F] {

    def unary_- : Matrix = null

    def +(that: Matrix): Matrix = null

    def -(that: Matrix): Matrix = null

    def **(that: Matrix): Matrix = null

    def /(that: Matrix): Matrix = null

    def \(that: Matrix): Matrix = null

    def *(that: Matrix): Matrix = null

    def t: Matrix = null

    def solve(b: Matrix): Matrix = null

    def solveTranspose(b: Matrix): Matrix = null

    def inverse: Matrix = null

    def apply(i: SelectAll, j: Int): Matrix = null

    def apply(i: Int, j: SelectAll): Matrix = null

    def apply(i0: Int, i1: Int, j0: Int, j1: Int): Matrix = null

    def apply(r: Array[Int], c: Array[Int]): Matrix = null

    def apply(r: SelectAll, c: Array[Int]): Matrix = null

    def apply(r: Array[Int], c: SelectAll): Matrix = null

    def apply(i0: Int, i1: Int, c: Array[Int]): Matrix = null

    def apply(i0: Int, i1: Int, c: SelectAll): Matrix = null

    def apply(r: SelectAll, j0: Int, j1: Int): Matrix = null

    def +=(that: Matrix): Matrix = null

    def -=(that: Matrix): Matrix = null

    def **=(that: Matrix): Matrix = null

    def /=(that: Matrix): Matrix = null

    def \=(that: Matrix): Matrix = null

    def *=[@specialized(Int, Long, Float, Double) A: Numeric](s: A): Matrix = null

    def rows: Int = 0

    def columns: Int = 0

    def cond: Double = 0.0

    def det: Double = 0.0

    def norm1: Double = 0.0

    def norm2: Double = 0.0

    def normF: Double = 0.0

    def normInf: Double = 0.0

    def rank: Int = 0

    def trace: Double = 0.0

    def apply(i: Int, j: Int): Double = Double.NaN

    def update[@specialized(Int, Long, Float, Double) A: Numeric](i: Int, j: Int, v: A) {}

    def update[@specialized(Int, Long, Float, Double) A: Numeric](i: SelectAll, j: Int, v: A) {}

    def update[@specialized(Int, Long, Float, Double) A: Numeric](i: Int, j: SelectAll, v: A) {}

    def copy: Matrix = null

    def *[@specialized(Int, Long, Float, Double) A: Numeric](s: A): Matrix = null
}
