package com.benoitthore.lib.keyframe.frame.utils

internal inline val Number.i get() = toInt()
internal inline val Number.f get() = toFloat()
internal inline val Number.d get() = toDouble()
internal inline val Number.L get() = toLong()
internal inline val Number.b get() = toByte()
internal inline val Number.bool get() = this.toInt() == 1
internal inline val Boolean.i get() = if (this) 1 else 0


internal inline val Number.percent get() = f / 100f
internal const val œ = 0.001f // ALT + Q on Mac


internal fun Number.constrain(min: Number, max: Number): Float {
    val max = max.f
    val min = min.f
    val number = this.f
    return if (number < min) min else if (number > max) max else number
}
