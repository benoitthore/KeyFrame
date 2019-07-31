package com.benoitthore.keyframe.core

import com.benoitthore.keyframe.core.utils.d
import com.benoitthore.keyframe.core.utils.f
import com.benoitthore.keyframe.core.utils.œ
import kotlin.math.pow
import kotlin.math.sin


internal typealias Interpolator = (Float) -> Float

val endInterpolator: Interpolator = { if (it > 1f - œ) 1f else 0f }
val linearInterpolator: Interpolator = { it }
val sinInterpolator: Interpolator = { ((sin((it - 0.5) * Math.PI) * 0.5 + 0.5)).f }
val bounceInterpolator: Interpolator = {

    fun bounce(t: Float): Float {
        return t * t * 8.0f
    }

    val t = it * 1.1226f
    when {
        t < 0.3535f -> bounce(t)
        t < 0.7408f -> bounce(t - 0.54719f) + 0.7f
        t < 0.9644f -> bounce(t - 0.8526f) + 0.9f
        else -> bounce(t - 1.0435f) + 0.95f
    }
}

fun overshootInterpolator(tension: Number = 1f): Interpolator = tension.toFloat().let { T ->
    { x ->
        (T + 1) * (x - 1).pow(3) + T * (x - 1).pow(2) + 1
    }
}

infix fun Number.interpolateWith(interpolator: Interpolator) = interpolator(this.f)

object EasingInterpolators {
    val quadInOut: Interpolator = {
        getPowInOut(
            it,
            2
        )
    }
    val cubicInOut: Interpolator = {
        getPowInOut(
            it,
            3
        )
    }
    val quartInOut: Interpolator = {
        getPowInOut(
            it,
            4
        )
    }
    val quintInOut: Interpolator = {
        getPowInOut(
            it,
            5
        )
    }
    val sineInOut: Interpolator = { -0.5f * (Math.cos(Math.PI * it) - 1f).f }
    fun accelerate(pow: Number = 2.0): Interpolator = { Math.pow(it.toDouble(), pow.toDouble()).toFloat() }
    fun deccelerate(pow: Number = 2.0): Interpolator = { 1f - Math.pow(1.0 - it, pow.toDouble()).toFloat() }
}

private fun getPowInOut(elapsedTimeRate: Float, pow: Number): Float {
    val elapsedTimeRate = elapsedTimeRate * 2f
    return if (elapsedTimeRate < 1) {
        (0.5 * Math.pow(elapsedTimeRate.toDouble(), pow.d)).f
    } else (1 - 0.5 * Math.abs(Math.pow((2 - elapsedTimeRate).toDouble(), pow.d))).f

}

private fun getPowOut(elapsedTimeRate: Float, pow: Number): Float {
    return (1.f - Math.pow((1f - elapsedTimeRate).d, pow.d)).f
}

private fun getBackInOut(elapsedTimeRate: Float, amount: Float): Float {
    val amount = amount * 1.525f
    var elapsedTimeRate = elapsedTimeRate * 2f

    return if (elapsedTimeRate < 1) {
        (0.5 * (elapsedTimeRate * elapsedTimeRate * ((amount + 1) * elapsedTimeRate - amount))).f
    } else {
        elapsedTimeRate -= 2f
        (0.5 * ((elapsedTimeRate) * elapsedTimeRate * ((amount + 1) * elapsedTimeRate + amount) + 2)).f
    }
}
