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
                color lockSince last(color) // Color won't change until this frame
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

        paint.color = color
        canvas.drawCircle(x, y, radius, paint)

        invalidate()
    }
}

fun Context.valueAnimatorNormalizedExampleView(): View {

    val frame: AlphaXY = FrameAnimationBuilder.createNormalized {
        with(it) {
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

    animator.duration = 2000L
    animator.repeatCount = ValueAnimator.INFINITE
    animator.repeatMode = ValueAnimator.REVERSE
    val paint = Paint()
    return canvasView { canvas ->
        if (!animator.isStarted) {
            animator.start()
        }
        val animationProgress = animator.animatedValue as Float


        // multiply by Canvas size because using .percent
        val x = frame.x.animate(animationProgress) * canvas.width
        val y = frame.y.animate(animationProgress) * canvas.height
        val radius = frame.radius.animate(animationProgress)
        val color = frame.color.animateColor(animationProgress)

        paint.color = color
        canvas.drawCircle(x, y, radius, paint)

        invalidate()
    }
}