package matlube

package object jama {

    implicit def asLeftNumberOps[B : Numeric](b: B) = new JLeftNumberOps[B](b)
    implicit def asRightNumberOps(b: JMatrix) = new JRightNumberOps(b)
}