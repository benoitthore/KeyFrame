package com.benoitthore.keyframe.examples

import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.benoitthore.keyframe.*
import com.benoitthore.keyframe.android.by
import com.benoitthore.keyframe.core.EasingInterpolators
import com.benoitthore.keyframe.core.FrameAnimationBuilder
import com.benoitthore.keyframe.core.animate
import com.benoitthore.keyframe.core.percent
import kotlinx.android.synthetic.main.seek_bar_example.*


class GraphExampleFragment : Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val precision = 100


        val frame: ColorData = FrameAnimationBuilder.createNormalized {
            it.apply {

                frame {
                    red set 0.percent

                    green set 25.percent
                    blue set 25.percent
                }

                frame {
                    red goto 100.percent by EasingInterpolators.quadInOut
                    green lockSince last(green)
                }

                frame {
                    red goto 50.percent by BounceInterpolator()
                    green goto 90.percent
                    blue goto 90.percent
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

        return context!!.canvasView { canvas ->

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

}