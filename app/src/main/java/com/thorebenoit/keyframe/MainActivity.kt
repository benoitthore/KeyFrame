package com.thorebenoit.keyframe

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.thorebenoit.lib.keyframe.FrameAnimationBuilder
import com.thorebenoit.lib.keyframe.FrameProperty
import com.thorebenoit.lib.keyframe.Normalisable
import com.thorebenoit.lib.keyframe.animateColor

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FrameAnimationBuilder.create<AlphaXY> {
            with(it) {
                frame {
                    x set 100f
                    y set 100f
                    color set 0
                }

                frame {
                    y goto 0f
                    color lockSince last(color)
                }

                frame {
                    TODO()
                }

            }
        }
//            .color.animateColor(10)

    }
}

data class AlphaXY(
    val x: MutableList<FrameProperty<Float>> = mutableListOf(),
    val y: MutableList<FrameProperty<Float>> = mutableListOf(),
    val radius: MutableList<FrameProperty<Float>> = mutableListOf(),
    val color: MutableList<FrameProperty<Int>> = mutableListOf()
) : Normalisable {
    override var propertyList: List<List<FrameProperty<*>>> = mutableListOf(
        x,
        y,
        radius,
        color
    )

}
// TODO Examples:
/**
 * - With value animator
 * - With progress bar
 * - With ScrollView/RecyclerView
 * - Advanced Canvas with createNormalised
 */
