package com.benoitthore.keyframe.core.colors

import com.benoitthore.keyframe.core.utils.constrain
import kotlin.math.pow
import kotlin.math.roundToInt

internal fun ARGB_evaluate(fraction: Float, startValue: Int, endValue: Int): Int {
    val startInt = startValue
    val startA = (startInt shr 24 and 0xff) / 255.0f
    var startR = (startInt shr 16 and 0xff) / 255.0f
    var startG = (startInt shr 8 and 0xff) / 255.0f
    var startB = (startInt and 0xff) / 255.0f

    val endInt = endValue
    val endA = (endInt shr 24 and 0xff) / 255.0f
    var endR = (endInt shr 16 and 0xff) / 255.0f
    var endG = (endInt shr 8 and 0xff) / 255.0f
    var endB = (endInt and 0xff) / 255.0f

    // convert from sRGB to linear
    startR = Math.pow(startR.toDouble(), 2.2).toFloat()
    startG = Math.pow(startG.toDouble(), 2.2).toFloat()
    startB = Math.pow(startB.toDouble(), 2.2).toFloat()

    endR = Math.pow(endR.toDouble(), 2.2).toFloat()
    endG = Math.pow(endG.toDouble(), 2.2).toFloat()
    endB = Math.pow(endB.toDouble(), 2.2).toFloat()

    // compute the interpolated color in linear space
    var a = startA + fraction * (endA - startA)
    var r = startR + fraction * (endR - startR)
    var g = startG + fraction * (endG - startG)
    var b = startB + fraction * (endB - startB)

    // convert back to sRGB in the [0..255] range
    a *= 255.0f
    r = r.toDouble().pow(1.0 / 2.2).toFloat() * 255.0f
    g = g.toDouble().pow(1.0 / 2.2).toFloat() * 255.0f
    b = b.toDouble().pow(1.0 / 2.2).toFloat() * 255.0f

    return try {
        a.roundToInt() shl 24 or (r.roundToInt() shl 16) or (g.roundToInt() shl 8) or b.roundToInt()
    } catch (e: Exception) {
        throw e
    }
}

class ColorScale(val colors: List<Int>) {

    operator fun get(fraction: Number): Int {
        val fraction = fraction.constrain(0, 1)

        val max = colors.size - 1

        val from = (max * fraction).toInt()
        val to = (from + 1).constrain(0, max).toInt()

        val subFraction = max * fraction - from
        return ARGB_evaluate(subFraction, colors[from], colors[to])
    }

}