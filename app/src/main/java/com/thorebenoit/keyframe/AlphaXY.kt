package com.thorebenoit.keyframe

import com.thorebenoit.lib.keyframe.FrameProperty
import com.thorebenoit.lib.keyframe.Normalisable

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