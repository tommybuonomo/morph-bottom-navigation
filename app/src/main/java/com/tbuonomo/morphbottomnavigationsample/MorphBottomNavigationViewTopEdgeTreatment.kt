package com.tbuonomo.morphbottomnavigationsample

import android.graphics.Canvas
import android.graphics.Paint
import android.view.Menu
import com.google.android.material.shape.EdgeTreatment
import com.google.android.material.shape.ShapePath
import com.tbuonomo.magicshapepath.MagicShapePath
import com.tbuonomo.magicshapepath.MagicShapePath.CircleShape
import com.tbuonomo.magicshapepath.MagicShapePath.PathDirection
import com.tbuonomo.magicshapepath.MagicShapePath.ShiftMode

class MorphBottomNavigationViewTopEdgeTreatment(private val menu: Menu,
        var morphItemRadius: Float,
        var morphVerticalOffset: Float,
        var morphCornerRadius: Float) :
        EdgeTreatment() {

  lateinit var easyShapePath: MagicShapePath

  var lastSelectedItem: Int = 0
  var selectedItem: Int = 0

  override fun getEdgePath(length: Float, interpolation: Float, shapePath: ShapePath) {
    easyShapePath = MagicShapePath.create(0f, morphVerticalOffset, length, morphVerticalOffset)

    for (i in 0 until menu.size()) {
      var morphHeightOffset = 0f
      if (i == selectedItem || i == lastSelectedItem) {
        if (i == selectedItem) {
          morphHeightOffset = interpolation * morphVerticalOffset
        } else if (i == lastSelectedItem) {
          morphHeightOffset = (1 - interpolation) * morphVerticalOffset
        }

        val itemWidth = length / menu.size()
        val itemRadius = itemWidth / 2

        val centerRadius = morphItemRadius
        val borderRadius = morphCornerRadius
        val centerX = itemWidth * i + itemRadius
        val centerY = morphVerticalOffset + centerRadius - morphHeightOffset

        val centerCircle = CircleShape(centerX, centerY, centerRadius, PathDirection.CLOCKWISE)

        val leftCircle = CircleShape(centerX, morphVerticalOffset - borderRadius, borderRadius, PathDirection.C_CLOCKWISE)
        centerCircle.shiftOutside(leftCircle, ShiftMode.LEFT)

        val rightCircle = CircleShape(centerX, morphVerticalOffset - borderRadius, borderRadius, PathDirection.C_CLOCKWISE)
        centerCircle.shiftOutside(rightCircle, ShiftMode.RIGHT)

        easyShapePath.addCircles(leftCircle, centerCircle, rightCircle)
      }

      easyShapePath.applyOn(shapePath)
    }
  }

  fun drawDebug(canvas: Canvas, paint: Paint) {
    easyShapePath.drawDebug(canvas, paint)
  }
}