[ ![Download](https://api.bintray.com/packages/benoitthore/KotlinKeyframe/core/images/download.svg?version=0.1.0) ](https://bintray.com/benoitthore/KotlinKeyframe/core/0.1.0/link)

# Introduction
The purpose of this library is to provide an easy way of building keyframe-based animations with a simple an intuitive DSL.

# Install
Core: ```implementation 'com.benoitthore.keyframe:core:0.1.0'```

Android: ```implementation 'com.benoitthore.keyframe:android:0.1.0'```

# Get started

Create a class
```kotlin
class CircleData(
    val x: MutableList<FrameProperty<Float>> = mutableListOf(),
    val y: MutableList<FrameProperty<Float>> = mutableListOf()
) : Normalizable {
    override val propertyList: List<MutableList<out FrameProperty<out Any>>> = listOf(x,y)
}
```

Create the frames
```kotlin
val frames = FrameAnimationBuilder.createNormalized<CircleData> {
        it.apply {
            frame {
                x set 0
                y set 0
            } 
            
            frame {
                x goto 10
                y goto 30
            } 

            frame {
                x goto 5
                y goto 5
            } 
        }
}
```

Get the animated value
```kotlin

val x = frame.x.animate(animationProgress)
val y = frame.y.animate(animationProgress)

```

# Documentation

More info [here](https://github.com/benoitthore/KeyFrame/blob/master/doc.md) 
