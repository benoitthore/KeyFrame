package com.benoitthore.keyframe.examples

import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.benoitthore.keyframe.*
import com.benoitthore.keyframe.core.EasingInterpolators
import com.benoitthore.keyframe.core.FrameAnimationBuilder
import com.benoitthore.keyframe.core.percent
import kotlinx.android.synthetic.main.seek_bar_example.*


class SeekBarExampleFragment : Fragment() {

    private val exampleData: CircleData =
        FrameAnimationBuilder.createNormalized<CircleData> {
            it.apply {
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
            doAnimation { progress ->
                circle_data_view.progress = progress
                seekbar.progress = (progress * 100).toInt()
            }
        }

        circle_data_view.data = exampleData

        seekbar.onSeekChange { progress ->
            circle_data_view.progress = progress / 100f
        }
    }
}