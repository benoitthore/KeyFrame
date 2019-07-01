package com.thorebenoit.keyframe

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import com.thorebenoit.lib.keyframe.*
import com.thorebenoit.lib.keyframe.frame.FrameAnimationBuilder

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
            frameWithDelay(2) { // 2 frames after the previous one
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


