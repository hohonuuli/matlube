package object matlube {

  /**
   * For selecting all elements from a matrix row or column.
   * See :: literal.
   *
   * @author dramage (brian: I pinched this from Scalala)
   */
  sealed trait SelectAll
  
  /**
   * For selecting all elements from a matrix row or column.
   *
   * @author dramage (brian: I pinched this from Scalala)
   */
  object :: extends SelectAll


}