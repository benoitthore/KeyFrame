package com.benoitthore.keyframe

import com.benoitthore.keyframe.core.FrameProperty
import com.benoitthore.keyframe.core.Normalizable

class CircleData(
    val x: MutableList<FrameProperty<Float>> = mutableListOf(),
    val y: MutableList<FrameProperty<Float>> = mutableListOf(),
    val radius: MutableList<FrameProperty<Float>> = mutableListOf(),
    val color: MutableList<FrameProperty<Int>> = mutableListOf()
) : Normalizable {
    override val propertyList: List<MutableList<out FrameProperty<out Any>>> = listOf(
        x,
        y,
        radius,
        color
    )

}


class ColorData(
    val red: MutableList<FrameProperty<Float>> = mutableListOf(),
    val green: MutableList<FrameProperty<Float>> = mutableListOf(),
    val blue: MutableList<FrameProperty<Float>> = mutableListOf()
) : Normalizable {
    override val propertyList: List<MutableList<out FrameProperty<out Any>>> =
        listOf(red, green, blue)
}