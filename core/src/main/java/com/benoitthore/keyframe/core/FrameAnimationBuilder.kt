package com.benoitthore.keyframe.core


class FrameAnimationBuilder<T : Normalizable>(val data: T) {


    companion object {
        inline fun <reified T : Normalizable> create(crossinline block: T.(FrameAnimationBuilder<T>) -> Unit) =
            FrameAnimationBuilder<T>(T::class.java.newInstance())
                .build(block)
                .apply {
                    propertyList.forEach { it.sortBy { it.position } }
                }

        inline fun <reified T : Normalizable> createNormalized(
            over: Float = 1f,
            crossinline block: T.(FrameAnimationBuilder<T>) -> Unit
        ) = create(block)
            .apply { normalize(over) }

    }

    var defaultInterpolator: Interpolator =
        linearInterpolator
    private var nextPosition: Float = 0f


    inline fun build(block: T.(FrameAnimationBuilder<T>) -> Unit): T {
        data.block(this)
        return data
    }

    fun frameWithDelay(delay: Number, block: FrameBuilder.() -> Unit)//
            = frame(nextPosition + delay.toFloat(), block)

    fun frame(position: Number? = null, block: FrameBuilder.() -> Unit) {
        val position = position?.toFloat() ?: nextPosition

        val frameBuilder = FrameBuilder(position)
        frameBuilder.block()
        frameBuilder.build()

        nextPosition = if (position < 0) {
            0f
        } else {
            position.toInt() + 1f
        }
    }

    inner class FrameBuilder(val position: Float) {
        val frameData: MutableList<AnimPropBuilder<*>> = mutableListOf()

        fun build() {
            frameData.forEach {
                it.addAt(position)
            }
        }

        fun <T> last(list: MutableList<FrameProperty<T>>): FrameProperty<T>? = list.maxBy { it.position }

        infix fun <T> MutableList<FrameProperty<T>>.lockSince(frame: FrameProperty<T>?) {
            frame?.position?.let { lockSince(it) }
        }

        private fun <T> MutableList<FrameProperty<T>>.lockSince(frameIndex: Number) {
            val frameIndex = frameIndex.toFloat()
            forEach {
                if (it.position == frameIndex) {
                    set(it.data)
                    return
                }
            }
            System.err.println("Cannot find frame with index $frameIndex")
        }

        infix fun <T> MutableList<FrameProperty<T>>.set(value: T) {
            this goto value by endInterpolator
        }

        infix fun <T> MutableList<FrameProperty<T>>.goto(value: T): AnimPropBuilder<T> {
            return AnimPropBuilder(value, this).apply { frameData.add(this) }
        }

    }

    inner class AnimPropBuilder<T>(val value: T, val targetList: MutableList<FrameProperty<T>>) {

        var interpolator: Interpolator = defaultInterpolator

        infix fun by(interpolator: Interpolator): AnimPropBuilder<T> {
            this.interpolator = interpolator
            return this
        }

        internal fun addAt(position: Float) {
            targetList.add(
                FrameProperty(
                    position,
                    value,
                    interpolator
                )
            )
        }
    }
}