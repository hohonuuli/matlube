package matlube


trait MatrixEnhancements {

    self: Matrix =>

    def size = (rows, columns)

    def isMutable = false
    
}

