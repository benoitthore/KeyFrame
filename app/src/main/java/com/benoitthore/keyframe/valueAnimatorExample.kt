package com.benoitthore.keyframe

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.View
import android.view.animation.BounceInterpolator
import com.benoitthore.keyframe.android.by
import com.benoitthore.keyframe.core.*

// TODO Cleanup this file
fun Context.graphView(): View {

    val precision = 100


    val frame: ColorData = FrameAnimationBuilder.createNormalized {
        it.apply {

            frame {
                red set 0.percent

                green set 25.percent
                blue set -1f
            }

            frame {
                red goto 100.percent by EasingInterpolators.quadInOut
                green lockSince last(green)
            }

            frame {
                red goto 50.percent by BounceInterpolator()
                green goto 90.percent
            }

        }
    }


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
                y goto 500f by com.benoitthore.keyframe.core.EasingInterpolators.quadInOut
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


fun Context.randomFrameExampleView(): View {

    val colors = listOf(
        Color.RED,
        Color.GREEN,
        Color.BLUE,
        Color.YELLOW,
        Color.CYAN,
        Color.MAGENTA
    )


    fun createFrameData(): CircleData = FrameAnimationBuilder.createNormalized {

        val numberOfFrames = 10
        val maxRadius = 50.dp
        val minRadius = 10.dp
        // Dont forget this
        it.apply {

            frame {
                x set random(0, 1).f
                radius set random(minRadius, maxRadius).f
                y set random(0, 1).f
                color set colors.random()
            }


            (0 until numberOfFrames - 1).forEach { _ ->
                frame {
                    x goto random(0, 1).f
                    radius goto random(minRadius, maxRadius).f
                    y goto random(0, 1).f
                    color goto colors.random()
                }
            }


        }
    }

    var circleData: CircleData = createFrameData()

    val animator = ValueAnimator.ofFloat(0f, 1f) // Over 1 because it's normalized over 1
        .apply {
            duration = 5000L
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                    circleData = createFrameData()
                }

                override fun onAnimationEnd(animation: Animator?) {

                }

                override fun onAnimationCancel(animation: Animator?) {

                }

                override fun onAnimationStart(animation: Animator?) {

                }

            })
        }


    return canvasView { canvas ->
        if (!animator.isStarted) {
            animator.start()
        }

        canvas.drawColor(Color.LTGRAY)
        val animationProgress = animator.animatedValue as Float


        canvas.drawFrame(circleData, animationProgress)

        invalidate()
    }
}

val paint = Paint()

fun Canvas.drawFrame(circleData: CircleData, animationProgress: Float) {

    // multiply by Canvas size because using .percent
    val x = circleData.x.animate(animationProgress) * width
    val y = circleData.y.animate(animationProgress) * height

    val radius = circleData.radius.animate(animationProgress)
    val color = circleData.color.animateColor(animationProgress)

    paint.color = color
    drawCircle(x, y, radius, paint)
}

