package matlube

/**
 * Extension methods to a [[matlube.Matrix]]. This trait contains implementations of methods.
 */
trait MatrixEnhancements {

    self: Matrix =>

    def size = (rows, columns)

    def isMutable = false
    
}

