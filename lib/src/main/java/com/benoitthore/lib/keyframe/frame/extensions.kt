package com.benoitthore.lib.keyframe.frame

import com.benoitthore.lib.keyframe.frame.utils.Scale
import com.benoitthore.lib.keyframe.frame.utils.f


val Number.percent get() = toFloat() / 100f

fun <T> MutableList<FrameProperty<T>>.normalized(over: Number = 1f): MutableList<FrameProperty<T>> {
    if (this.isEmpty()) {
        return this
    }
    val to = maxBy { it.position }!!.position
    val from = 0f

    val scale = Scale(listOf(from, to), listOf(0f, over))

    return asSequence().map {
        it.copy(position = scale[it.position].f)
    }.toMutableList()
}

fun MutableList<FrameProperty<Float>>.animate(fraction: Number) = animate(fraction,
    FloatAnimator
)
fun MutableList<FrameProperty<Int>>.animate(fraction: Number) = animate(fraction,
    IntAnimator
)
fun MutableList<FrameProperty<Int>>.animateColor(fraction: Number) = animate(fraction,
    ColorAnimator
)

fun <T> MutableList<FrameProperty<T>>.animate(fraction: Number, animator: PropertyInterpolator<T>): T {
    if (isEmpty()) {
        throw Exception("Cannot animate empty List<FrameProperty>")
    }
    val ratio = fraction.f

    // Can't start the animation yet ? Go to first position
    val first = first()
    if (ratio < first.position) {
        return first.data
    }
    // Animation over ? Go to last position
    val last = last()
    if (ratio > last.position) {
        return last.data
    }

    // If no element found, use the first one
    var next: FrameProperty<T> = first()
    var previous: FrameProperty<T>? = null

    run loop@{
        forEachIndexed { i, it ->
            if (it.position >= ratio) {
                next = it
                if (i > 0) {
                    previous = get(i - 1)
                }
                return@loop
            }
        }
    }
    if (previous == null) {
        return next.data
    }
    val from = previous!!
    val to = next

    val fraction = Scale.map(fraction, from.position, to.position, 0, 1)
    return animator(to.interpolator(fraction), from.data, to.data)
}

//val ε = 0.00001f // Epsilon

data class FrameProperty<T>(
    var position: Float,
    var data: T,
    var interpolator: Interpolator
)