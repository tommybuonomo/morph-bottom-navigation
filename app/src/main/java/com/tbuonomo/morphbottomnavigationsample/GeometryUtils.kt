package com.tbuonomo.morphbottomnavigationsample

import java.util.Arrays

class GeometryUtils {

  companion object {

    /**
     *  Finds tangent segments between two given circles.
     *
     *  Returns an empty, or 2x4, or 4x4 array of doubles representing
     *  the two exterior and two interior tangent segments (in that order).
     *  If some tangents don't exist, they aren't present in the output.
     *  Each segment is represent by a 4-tuple x1,y1,x2,y2.
     *
     *  Exterior tangents exist iff one of the circles doesn't contain
     *  the other. Interior tangents exist iff circles don't intersect.
     *
     *  In the limiting case when circles touch from outside/inside, there are
     *  no interior/exterior tangents, respectively, but just one common
     *  tangent line (which isn't returned at all, or returned as two very
     *  close or equal points by this code, depending on roundoff -- sorry!)
     *
     */
    fun getTangentsOfTwoCircles(x1: Double, y1: Double, r1: Double, x2: Double, y2: Double, r2: Double): Array<DoubleArray> {
      val dSq = (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)
      if (dSq <= (r1 - r2) * (r1 - r2)) return Array(0) { DoubleArray(4) }

      val d = Math.sqrt(dSq)
      val vx = (x2 - x1) / d
      val vy = (y2 - y1) / d

      val res = Array(4) { DoubleArray(4) }
      var i = 0

      // Let A, B be the centers, and C, D be points at which the tangent
      // touches first and second circle, and n be the normal vector to it.
      //
      // We have the system:
      //   n * n = 1          (n is a unit vector)
      //   C = A + r1 * n
      //   D = B +/- r2 * n
      //   n * CD = 0         (common orthogonality)
      //
      // n * CD = n * (AB +/- r2*n - r1*n) = AB*n - (r1 -/+ r2) = 0,  <=>
      // AB * n = (r1 -/+ r2), <=>
      // v * n = (r1 -/+ r2) / d,  where v = AB/|AB| = AB/d
      // This is a linear equation in unknown vector n.

      var sign1 = +1
      while (sign1 >= -1) {
        val c = (r1 - sign1 * r2) / d

        // Now we're just intersecting a line with a circle: v*n=c, n*n=1
        if (c * c > 1.0) {
          sign1 -= 2
          continue
        }
        val h = Math.sqrt(Math.max(0.0, 1.0 - c * c))

        var sign2 = +1
        while (sign2 >= -1) {
          val nx = vx * c - sign2.toDouble() * h * vy
          val ny = vy * c + sign2.toDouble() * h * vx

          val a = res[i++]
          a[0] = x1 + r1 * nx
          a[1] = y1 + r1 * ny
          a[2] = x2 + sign1.toDouble() * r2 * nx
          a[3] = y2 + sign1.toDouble() * r2 * ny
          sign2 -= 2
        }
        sign1 -= 2
      }

      return Arrays.copyOf(res, i)
    }

    fun getTangentsOfPointToCircle(px: Double, py: Double, cx: Double, cy: Double, r: Double): Array<DoubleArray> {
      val res = Array(2) { DoubleArray(4) }

      // Calculate the tangent point of the circle
      val dx = cx - px
      val dy = cy - py

      val dist = Math.sqrt((dx * dx + dy * dy))

      val a = Math.asin((r / dist))

      // The atan2 function gives the angle of a point with respect to the X axis, given the point's x and y coordinates.
      val b = Math.atan2(dy, dx)

      val theta1 = b - a
      val tanCPx1 = r * Math.sin(theta1)
      val tanCPy1 = r * -Math.cos(theta1)

      val theta2 = b + a
      val tanCPx2 = r * -Math.sin(theta2)
      val tanCPy2 = r * Math.cos(theta2)

      //The x point of the tangent
      val tanPx1 = cx + tanCPx1
      val tanPx2 = cx + tanCPx2

      //The y point of the tangent
      val tanPy1 = cy + tanCPy1
      val tanPy2 = cy + tanCPy2

      res[0][0] = px
      res[0][1] = py
      res[0][2] = tanPx1
      res[0][3] = tanPy1

      res[1][0] = px
      res[1][1] = py
      res[1][2] = tanPx2
      res[1][3] = tanPy2
      return res
    }

    /**
     * Calculates the angle from centerPt to targetPt in degrees.
     * The return should range from [0,360), rotating CLOCKWISE,
     * 0 and 360 degrees represents NORTH,
     * 90 degrees represents EAST, etc...
     *
     * Assumes all points are in the same coordinate space.  If they are not,
     * you will need to call SwingUtilities.convertPointToScreen or equivalent
     * on all arguments before passing them  to this function.
     *
     * @param cx   Point x we are rotating around.
     * @param cy   Point y we are rotating around.
     * @param px   Point x we want to calcuate the angle to.
     * @param py   Point y we want to calcuate the angle to.
     * @return angle in degrees.  This is the angle from centerPt to targetPt.
     */
    fun angleBetweenPoints(cx: Double, cy: Double, px: Double, py: Double): Double {
      // calculate the angle theta from the deltaY and deltaX values
      // (atan2 returns radians values from [-PI,PI])
      // 0 currently points EAST.
      // NOTE: By preserving Y and X param order to atan2,  we are expecting
      // a CLOCKWISE angle direction.
      var theta = Math.atan2(py - cy, px - cx)

      // rotate the theta angle clockwise by 90 degrees
      // (this makes 0 point NORTH)
      // NOTE: adding to an angle rotates it clockwise.
      // subtracting would rotate it counter-clockwise
//      theta += Math.PI / 2.0

      // convert from radians to degrees
      // this will give you an angle from [0->270],[-180,0]
      val angle = Math.toDegrees(theta)

      // convert to positive range [0-360)
      // since we want to prevent negative angles, adjust them now.
      // we can assume that atan2 will not return a negative value
      // greater than one partial rotation
//      if (angle < 0) {
//        angle += 360.0
//      }

      return angle
    }
  }
}