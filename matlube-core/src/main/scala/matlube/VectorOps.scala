package matlube

import scala.math._

/**
 *
 * @author Brian Schlining
 * @since 2012-09-13
 */
trait VectorOps[A <: Matrix[A]] {

    self: Matrix[A] =>

    /**
     * Dot product between 2 vectors. The dot product is commutative so the order of the arguments
     * is not important (i.e. `a.dot(b) == b.dot(a)`)
     *
     * The dot project is the sum of the element by element multiplication between
     * 2 vectors. E.g.
     * {{{
     *     val a = // Matrix of [1 2 3]
     *     val b = // Matrix of [1 0 1]
     *     // val dotProduct = ((a ** b) rowArray) sum // 4
     *     val dotProduct = a.dot(b)
     * *}}}
     *
     * '''REMEMBER''': The dot product a . b is equal to the signed length of the
     * projection of b onto any line parallel to a, multiplied by the length of
     * a. To convert it to parallel and perpendicular vectors  do the following: {{{
     *     val a = // Matrix of [1 2 3]
     *     val b = // Matrix of [1 0 1]
     *     val bParallel = a.dot(b) * a
     *     val bPerpendicular = b - bParallel
     * }}}
     *
     *
     *
     * Reference: Dunn, F., Parberry, I., 3D Math primer for Graphics and Game Development, 2nd Ed.
     * p56-66.
     *
     */
    def dot(that: Matrix[_]): Double = {
        require(that.isVector, "dot project is only allowed between vectors")
        require(this.isVector, "dot project is only allowed between vectors")
        // The sum of the result of '**' between 2 vectors is the dot project
        ((this ** that) rowArray) sum

    }

    /**
     * The magnitude of a vector
     *  '''REMEMBER''': The magnitude of a vector is: {{{
     *     import scala.math._
     *     val a = // Matrix of [1 2 3]
     *     val magnitude = sqrt(a.dot(a))
     * }}}
     *
     * '''REMEMBER''': To normalize a vector (so it becomes a unit vector with a length of 1). Do
     * the following: {{{
     *     val a = // Matrix of [12 5]
     *     val normalizedA = a / a.magnitude
     * }}}
     *
     * @return
     */
    def magnitude: Double = {
        require(isVector, "Magnitude is only allowed on vectors")
        sqrt(self.dot(self))
    }

    /**
     * The angle between 2 vectors in radians. The angle is on the plane that contains both vectors.
     *
     * '''REMEMBER''': `a.dot(b) = ||a|| ||b|| cos(theta)` or to solve for theta:
     * `theta = ||a|| ||b|| acos(a.dot(b))` where theta is the angle
     * between a and b in radians. In code this is
     * @param that
     * @return
     */
    def angle(that: Matrix[_]): Double = acos(this.dot(that) / (this.magnitude * that.magnitude))


    /**
     * Vector cross product. requires 3-element vectors.
     *
     * '''REMEMBER''': Cross product is used to create a vector that is perpendicular to a plane.
     *
     *
     * @param that
     * @return
     */
    def cross(that: Matrix[_]): A = {
        require(isVector, "cross product is only allowed on vectors")
        require(rows * columns == 3, "cross product requres a 3 element vector")
        require(that.isVector, "cross product is only allowed on vectors")
        require(that.rows * that.columns == 3, "cross product requres a 3 element vector")
        val p = (this(1) * that(2) - this(2) * that(1),
                this(2) * that(0) -  this(0) * that(2),
                this(0) * that(1) - this(1) * that(0))
        this.factory(p)
    }



}
