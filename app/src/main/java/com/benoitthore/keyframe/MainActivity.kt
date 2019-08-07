package com.benoitthore.keyframe

import android.animation.ValueAnimator
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.BounceInterpolator
import android.widget.SeekBar
import com.benoitthore.keyframe.android.fromAndroid
import com.benoitthore.keyframe.core.EasingInterpolators
import com.benoitthore.keyframe.core.FrameAnimationBuilder
import com.benoitthore.keyframe.core.linearInterpolator
import com.benoitthore.keyframe.core.percent
import kotlinx.android.synthetic.main.seek_bar_example.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        setContentView(graphView())
//        setContentView(valueAnimatorExampleView())
//        setContentView(valueAnimatorNormalizedExampleView())
//        setContentView(randomFrameExampleView())
        setContentView(R.layout.seek_bar_example)


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

        circle_data_view.data = FrameAnimationBuilder.createNormalized {
            it.apply {

                defaultInterpolator = EasingInterpolators.cubicInOut

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

// TODO Examples:
/**
 * - With value animator
 * - With progress bar
 * - With ScrollView/RecyclerView
 * - Advanced Canvas with createNormalized
 */
