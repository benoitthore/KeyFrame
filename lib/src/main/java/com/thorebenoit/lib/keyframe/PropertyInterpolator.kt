package com.thorebenoit.lib.keyframe

import com.thorebenoit.lib.keyframe.colors.ARGB_evaluate
import com.thorebenoit.lib.keyframe.utils.Scale
import com.thorebenoit.lib.keyframe.utils.d
import com.thorebenoit.lib.keyframe.utils.f
import com.thorebenoit.lib.keyframe.utils.i

typealias PropertyInterpolator<T> = (fraction: Number, fromValue: T, toValue: T) -> T


object FloatAnimator : PropertyInterpolator<Float> {

    override fun invoke(fraction: Number, fromValue: Float, toValue: Float): Float {
        return Scale.map(fraction, 0f, 1f, fromValue, toValue)
    }
}

object DoubleAnimator : PropertyInterpolator<Double> {
    override fun invoke(fraction: Number, fromValue: Double, toValue: Double): Double {
        return Scale.map(fraction, 0f, 1f, fromValue, toValue).d
    }
}


object IntAnimator : PropertyInterpolator<Int> {
    override fun invoke(fraction: Number, fromValue: Int, toValue: Int): Int {
        return Scale.map(fraction, 0f, 1f, fromValue, toValue).i
    }
}

object ColorAnimator : PropertyInterpolator<Int> {
    override fun invoke(fraction: Number, fromValue: Int, toValue: Int): Int {
        return ARGB_evaluate(fraction.f, fromValue, toValue)
    }
}
