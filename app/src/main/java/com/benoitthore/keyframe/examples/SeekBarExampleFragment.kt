package com.benoitthore.keyframe.examples

import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.benoitthore.keyframe.CircleData
import com.benoitthore.keyframe.R
import com.benoitthore.keyframe.core.EasingInterpolators
import com.benoitthore.keyframe.core.FrameAnimationBuilder
import com.benoitthore.keyframe.core.percent
import com.benoitthore.keyframe.dp
import kotlinx.android.synthetic.main.seek_bar_example.*


class SeekBarExampleFragment : Fragment() {

    private val exampleData: CircleData =
        FrameAnimationBuilder.createNormalized<CircleData> {
            it.apply {

//                defaultInterpolator = EasingInterpolators.cubicInOut

                frame {
                    x set 0.percent
                    y set 0.percent
                    radius set 20.dp
                    color set Color.RED
                }

                frame {
                    x goto 50.percent
                    y goto 50.percent
                    radius goto 40.dp
                    color lockSince last(color)
                }

                frame {
                    x goto 75.percent
                    y lockSince last(y)
                    color goto Color.GREEN
                    radius lockSince last(radius)
                }


                frame {
                    x goto 5.percent
                    y goto 90.percent
                    radius goto 10.dp
                    color goto Color.MAGENTA
                }


            }
        }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.seek_bar_example, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        button.setOnClickListener {
            ValueAnimator.ofFloat(0f, 1f).apply {

                addUpdateListener {
                    circle_data_view.progress = it.animatedFraction
                    seekbar.progress = (it.animatedFraction * 100).toInt()
                }
                duration = 2000L
                start()
            }
        }

        circle_data_view.data = exampleData


        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                circle_data_view.progress = progress / 100f
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }
}