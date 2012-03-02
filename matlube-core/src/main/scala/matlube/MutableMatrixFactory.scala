package matlube

trait MutableMatrixFactory[A <: MutableMatrix] extends MatrixFactory[A] {
    
    def apply(rows: Int, columns: Int): A

    def apply(rows: Int, columns: Int, fillValue: Double): A

}