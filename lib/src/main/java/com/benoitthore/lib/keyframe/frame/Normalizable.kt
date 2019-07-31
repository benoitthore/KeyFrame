package com.benoitthore.lib.keyframe.frame


interface Normalizable {
    val propertyList: List<MutableList<out FrameProperty<out Any>>>
}

fun Normalizable.normalize(over: Float = 1f) {
    val max = propertyList.maxBy {
        it.maxBy { it.position }?.position ?: 0f
    }
        ?.maxBy { it.position }?.position ?: return

    propertyList.forEach { property: List<FrameProperty<*>> ->
        // it = x, y , alpha

        property.forEach {
            val newPosition = over * it.position / max
            it.position = newPosition
        }

    }
}

val Normalizable.lastFrame: Float
    get() = propertyList.flatten().maxBy { it.position }?.position ?: 0f

