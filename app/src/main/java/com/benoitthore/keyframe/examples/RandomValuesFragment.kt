package com.benoitthore.keyframe.examples

import android.animation.Animator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.benoitthore.keyframe.*
import com.benoitthore.keyframe.core.*
import com.benoitthore.keyframe.ui.CircleDataView
import kotlinx.android.synthetic.main.seek_bar_example.*


class RandomValuesFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        CircleDataView(context!!).apply {

            setBackgroundColor(Color.LTGRAY)

//            val colors = listOf(
//                Color.RED,
//                Color.GREEN,
//                Color.BLUE,
//                Color.YELLOW,
//                Color.CYAN,
//                Color.MAGENTA
//            )

            val interpolators = listOf(
                EasingInterpolators.cubicInOut,
                EasingInterpolators.deccelerate(),
                EasingInterpolators.sineInOut,
                bounceInterpolator,
                linearInterpolator,
                EasingInterpolators.accelerate()
            )

            val numberOfFrames = 10

            fun createFrameData(): CircleData = FrameAnimationBuilder.createNormalized {

                val maxRadius = 60.dp
                val minRadius = 10.dp
                // Dont forget this
                it.apply {

                    frame {
                        x set random(0.1, 0.9).f
                        radius set random(minRadius, maxRadius).f
                        y set random(0.1, 0.9).f
                        color set randomColor()
                    }


                    (0 until numberOfFrames - 1).forEach { _ ->
                        frame {
                            x goto random(0.1, 0.9).f by interpolators.random()
                            radius goto random(minRadius, maxRadius).f by interpolators.random()
                            y goto random(0.1, 0.9).f by interpolators.random()
                            color goto randomColor()
                        }
                    }


                }
            }

            data = createFrameData()

            ValueAnimator.ofFloat(0f, 1f) // Over 1 because it's normalized over 1
                .apply {
                    duration = numberOfFrames * 2000L
                    repeatCount = ValueAnimator.INFINITE
                    repeatMode = ValueAnimator.RESTART

                    addUpdateListener {
                        progress = it.animatedFraction
                    }
                    addListener(object : Animator.AnimatorListener {
                        override fun onAnimationRepeat(animation: Animator?) {
                            data = createFrameData()
                        }

                        override fun onAnimationEnd(animation: Animator?) {

                        }

                        override fun onAnimationCancel(animation: Animator?) {

                        }

                        override fun onAnimationStart(animation: Animator?) {

                        }

                    })
                    start()
                }
        }


}