# Matlube
http://hohonuuli.github.com/matlube/

## Scala DSL for Matrix libraries.

Matlube is a Matlab-like linear algebra DSL for Scala. This library only supports dense double matrices.

It's designed to work as a front end to other Java Matrix libraries. Currently, there are two back-ends: one for Jama (http://math.nist.gov/javanumerics/jama/) and another for EJML (http://code.google.com/p/efficient-java-matrix-library/). Why have different back-ends? Well, everyone has thier favorite Matrix library. Different back-ends allows Matlube to easily integrate with whatever your prefered library is.


## Example code 

```scala
import matlube._
import matlube.ejml.{EMatrix => Mx, _} // Use EJML 
//import matlube.jama.{JMatrix => Mx, _} // or Use Jama

// Creating Matrices
val a = Mx(2, 2, Array(2, 3, 4, 5))         // Build 2 x 2 matrix using an Array
val b = Mx(((2, 3), (4, 5)))                // Build 2 x 2 matrix using a Tuple
val c = Mx.ones(10, 10)                     // 10 x 10 matrix of ones
val d = Mx.rand(5, 5)                       // 5 x 5 matrix random values
val e = Mx(4, 3, 5)                         // 4 x 3 matrix filled with the value 5
val f = Mx(Array(Array(2, 3), Array(4, 5))) // Build 2 x 2 matrix using a 2D Array

// Math Operations
val ab = a * b // Matrix multiplication
val a5 = 5 * a // we can operate with scalars!
val a6 = a + 6 // scalars can be on either side!

// Get elements
val d14 = d(1, 4)               // access a value in the matrix
val dr = d(0, ::)               // grab the first row as a Matrix
val dc = d(::, 0)               // grab the first column as a Matrix
val c3  = c(3)                  // single index access ala Matlab (column primary index)
val cc = c(0 to 30 by 3)        // grab elements 0, 3, 6, 9, ... 30 as a row vector
val cs = c(0 to 3, 1 to 2)      // grab a submatrix
val dl = d(::)                  // Same as Matlab's d(:), returns all elements as column vector

// Set elements
c(1, 5) = 15                                                   // Set a value in the Matrix
c(3 to 5, 4 to 6) = Mx(3, 3, Array(1, 2, 3, 4, 5, 6, 7, 8, 9)) // Set a submatrix
c(1 to 4 by 2, 1 to 4 by 2) = 404                              // Set a range of indicies
c(::, 8) = 8.33                                                // Set a column to a value
c(9, ::) = 9.01                                                // Set a row to a value

println(c.asString)  // Display the matrix. Not recommended for BIG matrices

/*
 Linear Regression.

 In Matlab:
  x = [1 2 4 5 7 9 11 13 14 16]';
  y = [101 105 109 112 117 116 122 123 129 130]';
  m = x \ y;      % Solve w/o intercept
  xi = [ones(size(x)) x];
  bm = xi \ y     % Solve w/ intercept. 1st val is intercept, second is slope
 */

val x = Mx((1, 2, 4, 5, 7, 9, 11, 13, 14, 16)).t
val y = Mx((101, 105, 109, 112, 117, 116, 122, 123, 129, 130)).t
val m = x \ y                          // 10.8900
val xi = Mx.hcat(Mx.ones(x.size), x)   // hcat is 'horizontal concatenate'
val bm = xi \ y   // 1st is intercept; 2nd is slope = [101.3021; 1.8412]

// Vector operations
val u = Mx((1, 2, 3))         // Build 1x3 matrix using a Tuple
val v = Mx((1, 0, 1))
v.isVector                    // Returns true if the matrix is a vector
val dotProduct = u.dot(v)  
val crossProduct = u.cross(v)
val magnitude = u.magnitude
val angle = u.angle(v)

/*
  Each matrix has an underlying delegate. The type of the delegate depends on
  the implementation. For EJML the delegate is a org.ejml.data.DenseMatrix64F
  for Jama it is Jama.Matrix. With the delegate you have access to all the
  underlying libraries functionality.
 */
 val delegate = u.delegate

```

## How to run the above code sample 
1. Check out the matlube source using Git
2. Build it ```
    cd matlube
    mvn install ```
3. Run a console ```
    cd matlube-jama
    mvn scala:console ```
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

You can use the ejml libray in your maven project with the following:

```xml
<dependency>
    <groupId>matlube</groupId>
    <artifactId>matlube-ejml</artifactId>
    <version>1.0-SNAPSHOT</version>
    <classifier>2.9.2</classifier>
</dependency>
```

Note the classifier is the version of scala used to build the library. You can change it in the pom.xml by editing the 'scala.version' property.