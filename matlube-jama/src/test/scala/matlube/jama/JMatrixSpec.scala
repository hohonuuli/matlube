package matlube.jama

import org.scalatest.matchers.ShouldMatchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec}
import matlube.Matrix

/**
 *
 * @author Brian Schlining
 * @since 2012-04-15
 */

@RunWith(classOf[JUnitRunner])
class JMatrixSpec extends FlatSpec with ShouldMatchers {

    "A JMatrix" should "implement all Matrix constructors" in {


    }

}

object JMatrixSpec {

    def areEqual(m0: Matrix[_], m1: Matrix[_]) = {
        true
    }

}
