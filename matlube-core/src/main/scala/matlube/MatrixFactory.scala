package matlube

trait MatrixFactory[A]  {

    def apply(data: Array[Double], orientation: Orientations.Orientation): A
    def apply(data: Product): A
    def apply(rows: Int, columns: Int): A
    def apply(rows: Int, columns: Int, fillValue: Double): A

}

object MatrixFactory {

    /**
     * Flattens out a nested tuple and returns the contents as an iterator
     */
    def flattenProduct(t: Product): Iterator[Any] = t.productIterator.flatMap {
        case p: Product => flattenProduct(p)
        case x => Iterator(x)
    } 

    /**
     * Convert a nested tuple to a flattened row-oriented array. This assumes that
     * all values in the tuple are of the same type. Usage is:
     * {{{
     *  val t = ((1, 2, 3), (4, 5, 6))
     *  val a = MatrixFactory.toArray[Int](t)
     *  // a: Array[Int] = Array(1, 2, 3, 4, 5, 6)
     * }}}
     *
     * @param t The tuple to convert to an array
     * @tparam A The type that the tuple contains. 
     */
    def toArray[A: ClassManifest](t: Product) = flattenProduct(t).map( _.asInstanceOf[A]).toArray

}
