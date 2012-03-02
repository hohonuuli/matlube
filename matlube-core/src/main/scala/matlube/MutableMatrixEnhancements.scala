package matlube

trait MutableMatrixEnhancements extends MatrixEnhancements {

    self: MutableMatrix =>
    
    override def isMutable = true
    
}