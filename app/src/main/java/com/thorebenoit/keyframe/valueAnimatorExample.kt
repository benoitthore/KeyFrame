package com.thorebenoit.keyframe

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import com.thorebenoit.lib.keyframe.*

fun Context.valueAnimatorExampleView(): View {


    val frame: AlphaXY = FrameAnimationBuilder.create {
        with(it) {
            frame {
                x set 100f
                radius set 25f
                y set 100f
                color set Color.BLUE
            }

            frame {
                y goto 500f
                x goto 300f
                color lockSince last(color)
            }

            frame {
                color goto Color.RED
                y goto 500f by EasingInterpolators.quadInOut
            }


            frame {
                x goto 1500f
            }
        }
    }

    val animator = ValueAnimator.ofFloat(0f, frame.lastFrame)

    animator.duration = 2000L
    animator.repeatCount = ValueAnimator.INFINITE
    animator.repeatMode = ValueAnimator.REVERSE
    val paint = Paint()
    return canvasView { canvas ->
        if (!animator.isStarted) {
            animator.start()
        }
        val animationProgress = animator.animatedValue as Float


        val x = frame.x.animate(animationProgress)
        val y = frame.y.animate(animationProgress)
        val radius = frame.radius.animate(animationProgress)
        val color = frame.color.animateColor(animationProgress)

        println(
            """
            x=$x
            y=$y
            radius=$radius
            color=$color
            ----------------------------------------
        """.trimIndent()
        )

        paint.color = color
        canvas.drawCircle(x, y, radius, paint)

        invalidate()
    }
}