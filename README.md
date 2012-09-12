# Matlube

## Scala wrapper for Matrix libraries.

The plan is to create a Matlab-like linear algebra DSL for Scala. This library only supports dense double matrices.I've written the a first cut at a core library and am working on two back-ends: one for Jama (http://math.nist.gov/javanumerics/jama/) and another for EJML (http://code.google.com/p/efficient-java-matrix-library/). Why have different back-ends? Well, everyone has thier favorite Matrix library. Different back-ends allows Matlube to easily integrate with whatever your prefered library is. Currently the Jama implementation is useable. I'm still working on the EJML one. Once these two are created, folks can use them as examples for wrapping other Matrix libraries in Matlube.

## Teaser code using Jama as a backend

```scala
import matlube._
//import matlube.ejml.{EMatrix => Mx, _} // Uncomment to use EJML instead of Jama
import matlube.jama.{JMatrix => Mx, _} // Alias JMatrix to something shorter

// Creating Matrices
val a = Mx(2, 2, Array(2, 3, 4, 5)) // 2 x 2 Matrix
val b = Mx(((2D, 3D), (4D, 5D)))    // Same as above
val c = Mx.ones(10, 10)             // 10 x 10 Matrix of ones
val d = Mx.rand(5, 5)               // 5 x 5 random values
val e = Mx(4, 3, 5)                 // 4 x 3 matrix filled with the value 5
val f = Mx(Array(Array(2, 3), Array(4, 5))) // 2-D Arrays work with Jama

val ab = a * b // Matrix multiplication
val a5 = 5 * a // we can operate with scalars!
val a6 = a + 6 // scalars can be on either side!

val c15 = c(1, 5)               // Access a value in the matrix
c(1, 5) = 15                    // Set a value in the Matrix
val r = c(0 to 30 by 3)         // grab elements 0, 3, 6, 9, ... 30 as a row vector

println(r.asString)  // Display the matrix. Not recommended for BIG matrices

val dr = d(0, ::)   // grab the first row as a Matrix
val dc = d(::, 0)   // grab the first column as a Matrix

/*
 Linear Regression.

 In Matlab:
  x = [1 2 4 5 7 9 11 13 14 16]';
  y = [101 105 109 112 117 116 122 123 129 130]';
  m = x \ y;      % Solve w/o intercept
  xi = [ones(size(x)) x];
  bm = xi \ y     % Solve w/ intercept. 1st val is intercept, second is slope
 */

val x = Mx((1d, 2d, 4d, 5d, 7d, 9d, 11d, 13d, 14d, 16d)).t
val y = Mx((101d, 105d, 109d, 112d, 117d, 116d, 122d, 123d, 129d, 130d)).t
val m = x \ y     // 10.8900
val xi = Mx.ccat(Mx.ones(x.size), x)
val bm = xi \ y   // 1st is intercept; 2nd is slope = [101.3021; 1.8412]
```

## How to run the above code sample 
1. Check out the matlube source using Git
2. Build it
    cd matlube
    mvn install
3. Run a console
    cd matlube-jama
    mvn scala:console
4. Cut and past the code samples above into the console

The built jama library can be included in another maven project by adding the following to your pom.xml

```xml
<dependency>
    <groupId>matlube</groupId>
    <artifactId>matlube-jama</artifactId>
    <version>1.0-SNAPSHOT</version>
    <classifier>2.9.2</classifier>
</dependency>
```

Note the classifier is the version of scala used to build the library. You can change it in the pom.xml by editing the 'scala.version' property.