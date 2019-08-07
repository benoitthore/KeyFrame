package com.benoitthore.keyframe

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.view.View

fun Context.canvasView(drawBlock: View.(Canvas) -> Unit): View = object : View(this) {
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBlock(canvas)
    }
}


inline val Number.f get() = toFloat()
inline val Number.dp get() = Resources.getSystem().displayMetrics.density * toFloat()
fun random(min: Number, max: Number) = random(min.toDouble(), max.toDouble())
fun random(min: Double, max: Double) = min + (Math.random() * ((max - min)))