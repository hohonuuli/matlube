package matlube

package object ejml {
    implicit def asLeftNumberOps[B : Numeric](b: B) = new LeftNumberOps[EMatrix, B](EMatrix, b)
    implicit def asRightNumberOps(b: EMatrix) = new RightNumberOps[EMatrix](EMatrix, b)
}