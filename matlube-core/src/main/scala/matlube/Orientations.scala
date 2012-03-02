package matlube

/**
 * Enumration to indicate data orientation (Row or Column)
 * 
 * Overview of Orientation:
 * {{{
 * val array = Array(0, 1, 2, 3, 4, 5, 6, 7,)
 *
 * // as row-oriented 2 x 4 matrix
 * [0, 1, 2, 3;
 *  4, 5, 6, 7]   
 *
 * // as column-oriented 2 x 4 matrix
 * [0, 2, 4, 6;
 *  1, 3, 5, 7]
 * 
 * }}}
 */

object Orientations extends Enumeration("Row", "Column") {
    type Orientation = Value
    val Row, Column = Value
}