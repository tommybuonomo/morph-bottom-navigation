package com.tbuonomo.morphbottomnavigation

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.Menu
import android.view.View
import androidx.core.view.forEachIndexed
import androidx.core.view.isVisible
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.shape.EdgeTreatment
import com.google.android.material.shape.ShapePath
import com.tbuonomo.magicshapepath.MagicShapePath
import com.tbuonomo.magicshapepath.MagicShapePath.CircleShape
import com.tbuonomo.magicshapepath.MagicShapePath.PathDirection
import com.tbuonomo.magicshapepath.MagicShapePath.ShiftMode

class MorphBottomNavigationViewTopEdgeTreatment(private val bottomNavigationMenuView: BottomNavigationMenuView,
        var morphItemRadius: Float,
        var morphVerticalOffset: Float,
        var morphCornerRadius: Float) :
        EdgeTreatment() {

  lateinit var easyShapePath: MagicShapePath

  var lastSelectedItem: Int = 0
  var selectedItem: Int = 0

  override fun getEdgePath(length: Float, interpolation: Float, shapePath: ShapePath) {
    easyShapePath = MagicShapePath.create(0f, morphVerticalOffset, length, morphVerticalOffset)

    bottomNavigationMenuView.forEachIndexed { i, view ->
      var morphHeightOffset = 0f

      //Draw only selected and last selected path
      if (view.isVisible && (i == selectedItem || i == lastSelectedItem)) {
        if (i == selectedItem) {
          morphHeightOffset = interpolation * morphVerticalOffset
        } else if (i == lastSelectedItem) {
          morphHeightOffset = (1 - interpolation) * morphVerticalOffset
        }

        val itemRect = view.globalVisibleRect

        val centerRadius = morphItemRadius
        val borderRadius = morphCornerRadius
        val centerX = itemRect.centerX().toFloat()
        val centerY = morphVerticalOffset + centerRadius - morphHeightOffset

        val centerCircle = CircleShape(centerX, centerY, centerRadius, PathDirection.CLOCKWISE)

        val leftCircle = CircleShape(centerX, morphVerticalOffset - borderRadius, borderRadius, PathDirection.C_CLOCKWISE)
        centerCircle.shiftOutside(leftCircle, ShiftMode.LEFT)

        val rightCircle = CircleShape(centerX, morphVerticalOffset - borderRadius, borderRadius, PathDirection.C_CLOCKWISE)
        centerCircle.shiftOutside(rightCircle, ShiftMode.RIGHT)

        easyShapePath.addCircles(leftCircle, centerCircle, rightCircle)
      }
    }


    easyShapePath.applyOn(shapePath)
  }

  fun drawDebug(canvas: Canvas, paint: Paint) {
    easyShapePath.drawDebug(canvas, paint)
  }

  private inline val View.globalVisibleRect: Rect
    get() {
      val r = Rect()
      getGlobalVisibleRect(r)
      return r
    }

}
