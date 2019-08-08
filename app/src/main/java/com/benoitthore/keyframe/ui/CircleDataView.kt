package com.benoitthore.keyframe.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.benoitthore.keyframe.CircleData
import com.benoitthore.keyframe.core.animate
import com.benoitthore.keyframe.core.animateColor
import kotlin.math.*

class CircleDataView : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    init {
        setOnClickListener {  }
    }

    private val paint = Paint()

    var progress = 0f
        set(value) {
            field = min(max(0f, value), 1f)
            invalidate()
        }

    var data: CircleData? = null
        set(value) {
            field = value
            invalidate()
        }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        data?.let { circleData ->
            val x = circleData.x.animate(progress) * width
            val y = circleData.y.animate(progress) * height

            val radius = circleData.radius.animate(progress)
            val color = circleData.color.animateColor(progress)

            paint.color = color
            canvas.drawCircle(x, y, radius, paint)
        }

    }

}