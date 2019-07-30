package com.thorebenoit.keyframe

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.View
import com.thorebenoit.lib.keyframe.*
import com.thorebenoit.lib.keyframe.frame.FrameAnimationBuilder


fun Context.graphView(): View {


    val frame: ColorData = FrameAnimationBuilder.createNormalized {

        it.apply {

            frame {
                red set 5.percent
                green set 10.percent
                blue set 15.percent
            }

//            frame {
//                y goto 10.percent
//            }

            frame {
                red goto 90.percent by EasingInterpolators.cubicInOut
                blue goto 100.percent by bounceInterpolator
            }

//            frame {
//                y goto 50.percent
//            }
        }
    }

    val precision = 100

    val redPath = Path()
    val greenPath = Path()
    val bluePath = Path()
    val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 10f
    }

    return canvasView { canvas ->

        canvas.drawColor(0xFF_bbbbbb.toInt())
        redPath.reset()
        greenPath.reset()
        bluePath.reset()

        (0..precision).map { it / 100f }.forEach { progress ->

            val yRed = height - (frame.red.animate(progress) * height)
            val yGreen = height - (frame.green.animate(progress) * height)
            val yBlue = height - (frame.blue.animate(progress) * height)
            val x = width * progress
            if (progress == 0f) {
                redPath.moveTo(x, yRed)
                greenPath.moveTo(x, yGreen)
                bluePath.moveTo(x, yBlue)

            } else {
                redPath.lineTo(x, yRed)
                greenPath.lineTo(x, yGreen)
                bluePath.lineTo(x, yBlue)

            }

            paint.color = Color.BLUE
            canvas.drawPath(bluePath, paint)

            paint.color = Color.GREEN
            canvas.drawPath(greenPath, paint)

            paint.color = Color.RED
            canvas.drawPath(redPath, paint)

        }
    }


}

fun Context.valueAnimatorExampleView(): View {

    // Keyframes definition
    val frame: CircleData = FrameAnimationBuilder.create {

        // Dont forget this
        it.apply {

            frame {
                x set 100f
                radius set 25f
                y set 100f
                color set Color.BLUE
            }

            frame {
                y goto 500f
                x goto 300f
                color lockSince last(color) // Color won't change until this frame
            }

            frame {
                color goto Color.RED
                y goto 500f by EasingInterpolators.quadInOut
            }


            frame {
                x goto 1500f
            }


            frame {
                x set 0f
            }
            frameWithDelay(2) {
                // 2 frames after the previous one
                x goto 100f
            }
        }
    }

    val animator = ValueAnimator.ofFloat(0f, frame.lastFrame)
        .apply {
            duration = 2000L
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
        }

    val paint = Paint()

    fun Canvas.drawFrame(frame: CircleData, animationProgress: Float) {
        val x = frame.x.animate(animationProgress)
        val y = frame.y.animate(animationProgress)
        val radius = frame.radius.animate(animationProgress)
        val color = frame.color.animateColor(animationProgress)

        paint.color = color
        drawCircle(x, y, radius, paint)
    }


    return canvasView { canvas ->
        if (!animator.isStarted) {
            animator.start()
        }

        val animationProgress = animator.animatedValue as Float

        canvas.drawFrame(frame, animationProgress)

        invalidate()
    }
}

fun Context.valueAnimatorNormalizedExampleView(): View {

    // Keyframes definition
    val frame: CircleData = FrameAnimationBuilder.createNormalized {

        // Dont forget this
        it.apply {

            frame {
                x set 10.percent
                radius set 25f
                y set 10.percent
                color set Color.BLUE
            }

            frame {
                x goto 50.percent
                y goto 75.percent
                color lockSince last(color)
            }

            frame {
                radius lockSince last(radius) // Radius won't change until this frame
                color goto Color.RED
            }


            frame {
                x goto 90.percent
                radius goto 50f by bounceInterpolator
                color goto Color.YELLOW
            }

        }
    }

    val animator = ValueAnimator.ofFloat(0f, 1f) // Over 1 because it's normalized over 1
        .apply {
            duration = 2000L
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
        }

    val paint = Paint()

    fun Canvas.drawFrame(frame: CircleData, animationProgress: Float) {

        // multiply by Canvas size because using .percent
        val x = frame.x.animate(animationProgress) * width
        val y = frame.y.animate(animationProgress) * height

        val radius = frame.radius.animate(animationProgress)
        val color = frame.color.animateColor(animationProgress)

        paint.color = color
        drawCircle(x, y, radius, paint)
    }

    return canvasView { canvas ->
        if (!animator.isStarted) {
            animator.start()
        }
        val animationProgress = animator.animatedValue as Float


        canvas.drawFrame(frame, animationProgress)

        invalidate()
    }
}


