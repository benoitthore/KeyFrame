package com.benoitthore.keyframe.android

import android.view.animation.Interpolator
import com.benoitthore.keyframe.core.FrameAnimationBuilder
import com.benoitthore.keyframe.core.Normalizable


infix fun <T : Normalizable, T2> FrameAnimationBuilder<T>.AnimPropBuilder<T2>.by(interpolator: Interpolator):
        FrameAnimationBuilder<T>.AnimPropBuilder<T2> {
    this.interpolator = { interpolator.getInterpolation(it) }
    return this
}