package com.thorebenoit.lib.keyframe

import com.thorebenoit.lib.keyframe.utils.Scale
import com.thorebenoit.lib.keyframe.utils.f
import com.thorebenoit.lib.keyframe.utils.i

interface PropertyInterpolator<T> {
    fun compute(fraction: Number, fromValue: T, toValue: T, interpolator: Interpolator = linearInterpolator): T
}

object FloatAnimator : PropertyInterpolator<Float> {
    override fun compute(fraction: Number, fromValue: Float, toValue: Float, interpolator: Interpolator): Float {
        return Scale.map(interpolator(fraction.f), 0f, 1f, fromValue, toValue)
    }
}

object IntAnimator : PropertyInterpolator<Int> {
    override fun compute(fraction: Number, fromValue: Int, toValue: Int, interpolator: Interpolator): Int {
        return Scale.map(interpolator(fraction.f), 0f, 1f, fromValue, toValue).i
    }
}

object ColorAnimator : PropertyInterpolator<Int> {
    override fun compute(fraction: Number, fromValue: Int, toValue: Int, interpolator: Interpolator): Int {
        return ARGB_evaluate(interpolator(fraction.f), fromValue, toValue)
    }
}
