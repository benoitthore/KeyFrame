package com.thorebenoit.keyframe

import android.content.Context
import android.graphics.Canvas
import android.view.View

fun Context.canvasView(drawBlock: View.(Canvas) -> Unit): View = object : View(this) {
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBlock(canvas)
    }
}