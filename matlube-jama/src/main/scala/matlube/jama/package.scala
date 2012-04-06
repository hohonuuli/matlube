package matlube

package object jama {

    implicit def scalarToMatrix[B : Numeric](b: B) = new JNumberMatrix[B](b)
}