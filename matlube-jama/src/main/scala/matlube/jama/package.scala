package matlube

package object jama {

    implicit def asLeftNumberOps[B : Numeric](b: B) = new LeftNumberOps[JMatrix, B](JMatrix, b)
    implicit def asRightNumberOps(b: JMatrix) = new RightNumberOps[JMatrix](JMatrix, b)
}