package com.thorebenoit.keyframe

import com.thorebenoit.lib.keyframe.FrameProperty
import com.thorebenoit.lib.keyframe.Normalizable

class CircleData(
    val x: MutableList<FrameProperty<Float>> = mutableListOf(),
    val y: MutableList<FrameProperty<Float>> = mutableListOf(),
    val radius: MutableList<FrameProperty<Float>> = mutableListOf(),
    val color: MutableList<FrameProperty<Int>> = mutableListOf()
) : Normalizable {
    override val propertyList: List<List<FrameProperty<*>>> = mutableListOf(
        x,
        y,
        radius,
        color
    )

}