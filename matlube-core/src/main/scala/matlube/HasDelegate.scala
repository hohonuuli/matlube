package matlube

/**
 * Trait that tells a Matrix implementation what the underlying implementation is (e.g. Jama Matrix,
 * EJML Matrix, etc.)
 *
 * @author Brian Schlining
 * @since 2012-03-02
 */
trait HasDelegate[A] {

    def delegate: A

}
