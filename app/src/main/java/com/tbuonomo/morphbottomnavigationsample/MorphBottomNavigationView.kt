package com.tbuonomo.morphbottomnavigationsample

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Style
import android.graphics.Paint.Style.STROKE
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapePathModel

class MorphBottomNavigationView : BottomNavigationView, OnNavigationItemSelectedListener {
  private val paint = Paint().apply {
    isAntiAlias = true
    color = Color.BLUE
    strokeWidth = 2f
    style = STROKE
    alpha = 50
  }
  private val height: Float

  private val topEdgeTreatment: MorphBottomNavigationViewTopEdgeTreatment
  private val materialShapeDrawable: MaterialShapeDrawable

  private var selectedItem = 0

  private var selectionAnimator: ValueAnimator? = null

  open var morphItemRadius: Float = 0f
    set(radius) {
      field = radius
      topEdgeTreatment.morphItemRadius = radius
      invalidate()
    }

  open var morphCornerRadius: Float = 0f
    set(radius) {
      field = radius
      topEdgeTreatment.morphCornerRadius = radius
      invalidate()
    }

  open var morphVerticalOffset: Float = 0f
    set(offset) {
      field = offset
      topEdgeTreatment.morphVerticalOffset = offset
      if (layoutParams != null) {
        layoutParams.height = (height + morphVerticalOffset).toInt()
      }

      invalidate()
    }

  open var drawDebug: Boolean = false
    set(enable) {
      field = enable
      invalidate()
    }

  constructor(context: Context) : this(context, null)
  constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs,
          defStyleAttr) {

    val a = resources.obtainAttributes(attrs, R.styleable.MorphBottomNavigationView)

    val typedValue = TypedValue()
    context.theme?.resolveAttribute(R.attr.colorPrimary, typedValue, true)
    val backgroundTint = a.getColor(R.styleable.MorphBottomNavigationView_backgroundTint, typedValue.data)

    val morphItemRadius = a.getDimensionPixelSize(R.styleable.MorphBottomNavigationView_morphItemRadius, dpToPx(32f).toInt()).toFloat()
    val morphVerticalOffset = a.getDimensionPixelSize(R.styleable.MorphBottomNavigationView_morphVerticalOffset, dpToPx(8f).toInt()).toFloat()
    val morphCornerRadius = a.getDimensionPixelSize(R.styleable.MorphBottomNavigationView_morphCornerRadius, dpToPx(92f).toInt()).toFloat()

    a.recycle()

    height = dpToPx(56f)

    topEdgeTreatment = MorphBottomNavigationViewTopEdgeTreatment(
            menu,
            morphItemRadius,
            morphVerticalOffset,
            morphCornerRadius)

    this.morphItemRadius = morphItemRadius
    this.morphVerticalOffset = morphVerticalOffset
    this.morphCornerRadius = morphCornerRadius

    val appBarModel = ShapePathModel()
    appBarModel.topEdge = topEdgeTreatment
    materialShapeDrawable = MaterialShapeDrawable(appBarModel)
    materialShapeDrawable.isShadowEnabled = true
    materialShapeDrawable.paintStyle = Style.FILL
    materialShapeDrawable.setTint(backgroundTint)
    background = materialShapeDrawable

    val bottomNavigationMenuView = getBottomNavigationMenuView()
    val menuParams = bottomNavigationMenuView?.layoutParams as FrameLayout.LayoutParams
    menuParams.gravity = Gravity.BOTTOM

    setOnNavigationItemSelectedListener(this)

    setWillNotDraw(false)
  }

  /**
   * Proxy for listener
   */
  override fun setOnNavigationItemSelectedListener(listener: OnNavigationItemSelectedListener?) {
    super.setOnNavigationItemSelectedListener({
      onNavigationItemSelected(it)
      if (listener !is MorphBottomNavigationView) {
        listener?.onNavigationItemSelected(it)
      }
      true
    })
  }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    val indexOfItemSelected = menu.indexOfItem(item)
    if (indexOfItemSelected != selectedItem) {
      topEdgeTreatment.lastSelectedItem = selectedItem
      topEdgeTreatment.selectedItem = indexOfItemSelected
      selectedItem = indexOfItemSelected

      selectionAnimator?.end()
      selectionAnimator?.cancel()

      selectionAnimator = ValueAnimator.ofFloat(0f, 1f)
      selectionAnimator?.addUpdateListener {
        materialShapeDrawable.interpolation = it.animatedValue as Float
      }

      selectionAnimator?.duration = 200
      selectionAnimator?.interpolator = DecelerateInterpolator()

      selectionAnimator?.start()
    }

    return true
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    // Change height
    layoutParams.height = (height + morphVerticalOffset).toInt()
  }

  private fun getBottomNavigationMenuView(): BottomNavigationMenuView? {
    for (i in 0 until childCount) {
      if (getChildAt(i) is BottomNavigationMenuView) return getChildAt(i) as BottomNavigationMenuView
    }
    return null
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    if (drawDebug) {
      topEdgeTreatment.drawDebug(canvas, paint)
    }
  }

  private fun dpToPx(dp: Float): Float {
    return resources.displayMetrics.density * dp
  }

  private fun Menu.indexOfItem(item: MenuItem): Int {
    for (i in 0 until this.size()) {
      val menuItem = menu.getItem(i)
      if (menuItem.itemId == item.itemId) {
        return i
      }
    }
    return -1
  }
}

