package com.tbuonomo.morphbottomnavigationsample

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.addItemButton
import kotlinx.android.synthetic.main.activity_main.bottomNavigationView
import kotlinx.android.synthetic.main.activity_main.drawDebugButton
import kotlinx.android.synthetic.main.activity_main.morphCornerRadiusSeekBar
import kotlinx.android.synthetic.main.activity_main.morphCornerRadiusValue
import kotlinx.android.synthetic.main.activity_main.morphItemRadiusSeekBar
import kotlinx.android.synthetic.main.activity_main.morphItemRadiusValue
import kotlinx.android.synthetic.main.activity_main.morphVerticalOffsetSeekBar
import kotlinx.android.synthetic.main.activity_main.morphVerticalOffsetValue
import kotlinx.android.synthetic.main.activity_main.removeItemButton

class MainActivity : AppCompatActivity() {
  private var numVisibleChildren = 4

  companion object {
    private const val MAX_MENU_ITEMS = 5
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    initMenuItemsVisibility()

    drawDebugButton.setOnClickListener {
      val enable = !drawDebugButton.isActivated
      bottomNavigationView.drawDebug = enable
      drawDebugButton.isActivated = enable
      drawDebugButton.setBackgroundColor(if (enable) ContextCompat.getColor(this, R.color.colorPrimary) else Color.GRAY)
    }

    addItemButton.setOnClickListener {
      if (numVisibleChildren < MAX_MENU_ITEMS) {
        bottomNavigationView.menu.getItem(numVisibleChildren).isVisible = true
        numVisibleChildren++
      }
    }

    removeItemButton.setOnClickListener {
      if (numVisibleChildren > 1) {
        numVisibleChildren--
        bottomNavigationView.menu.getItem(numVisibleChildren).isVisible = false
      }
    }

    setUpSeekBars()
  }

  private fun initMenuItemsVisibility() {
    for (i in 0 until bottomNavigationView.menu.size()) {
      bottomNavigationView.menu.getItem(i).isVisible = i < numVisibleChildren
    }
  }

  private fun setUpSeekBars() {
    morphItemRadiusValue.text = getString(R.string.value_dp, pxToDp(bottomNavigationView.morphItemRadius).toInt())
    morphCornerRadiusValue.text = getString(R.string.value_dp, pxToDp(bottomNavigationView.morphCornerRadius).toInt())
    morphVerticalOffsetValue.text = getString(R.string.value_dp, pxToDp(bottomNavigationView.morphVerticalOffset).toInt())

    val minItemRadius = dpToPx(8f)
    val maxItemRadius = dpToPx(128f)
    morphItemRadiusSeekBar.max = (maxItemRadius - minItemRadius).toInt()
    morphItemRadiusSeekBar.progress = (bottomNavigationView.morphItemRadius - minItemRadius).toInt()

    morphItemRadiusSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
      override fun onProgressChanged(seekbar: SeekBar?, progress: Int, p2: Boolean) {
        val morphItemRadius = progress + minItemRadius
        bottomNavigationView.morphItemRadius = morphItemRadius
        morphItemRadiusValue.text = getString(R.string.value_dp, pxToDp(morphItemRadius).toInt())
      }

      override fun onStartTrackingTouch(p0: SeekBar?) {
      }

      override fun onStopTrackingTouch(p0: SeekBar?) {
      }
    })

    val minCornerRadius = dpToPx(8f)
    val maxCornerRadius = dpToPx(128f)
    morphCornerRadiusSeekBar.max = (maxCornerRadius - minCornerRadius).toInt()
    morphCornerRadiusSeekBar.progress = (bottomNavigationView.morphCornerRadius - minCornerRadius).toInt()


    morphCornerRadiusSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
      override fun onProgressChanged(seekbar: SeekBar?, progress: Int, p2: Boolean) {
        val morphCornerRadius = progress + minCornerRadius
        bottomNavigationView.morphCornerRadius = morphCornerRadius
        morphCornerRadiusValue.text = getString(R.string.value_dp, pxToDp(morphCornerRadius).toInt())
      }

      override fun onStartTrackingTouch(p0: SeekBar?) {
      }

      override fun onStopTrackingTouch(p0: SeekBar?) {
      }
    })

    val minVerticalOffset = dpToPx(2f)
    val maxVerticalOffset = dpToPx(32f)
    morphVerticalOffsetSeekBar.max = (maxVerticalOffset - minVerticalOffset).toInt()
    morphVerticalOffsetSeekBar.progress = (bottomNavigationView.morphVerticalOffset - minVerticalOffset).toInt()

    morphVerticalOffsetSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
      override fun onProgressChanged(seekbar: SeekBar?, progress: Int, p2: Boolean) {
        val morphVerticalOffset = progress + minVerticalOffset
        bottomNavigationView.morphVerticalOffset = morphVerticalOffset
        morphVerticalOffsetValue.text = getString(R.string.value_dp, pxToDp(morphVerticalOffset).toInt())
      }

      override fun onStartTrackingTouch(p0: SeekBar?) {
      }

      override fun onStopTrackingTouch(p0: SeekBar?) {
      }
    })
  }

  fun dpToPx(dp: Float): Float = resources.displayMetrics.density * dp
  fun pxToDp(px: Float): Float = px / resources.displayMetrics.density
}
