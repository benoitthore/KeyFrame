# Documentation

## Introduction
TODO


## Building

### Define your model
In order to use this library you first need to define the class where the data will be held, following these constraints:

- Each property that you want to animate needs to be of type MutableList<FrameProperty<T>>
- The class itself **MUST HAVE AN EMPTY CONSTRUCTOR**

```kotlin
class CircleData(
    val x: MutableList<FrameProperty<Float>> = mutableListOf(),
    val y: MutableList<FrameProperty<Float>> = mutableListOf(),
    val radius: MutableList<FrameProperty<Float>> = mutableListOf(),
    val color: MutableList<FrameProperty<Int>> = mutableListOf()
) 
```

### Building the frames
In order to use the library, you need to call `FrameAnimationBuilder.createNormalized<T>` where `T` is the class we've just defined

In order to make the DSL easier to use, everything should be in a `it.apply` block, this is a workaround to get a double scope in Kotlin. So here's everything we need to get started:


```kotlin
FrameAnimationBuilder.createNormalized<CircleData> {
        it.apply {
            // frame{} goes here 
        }
}
```

From now on the DSL is setup so we can start setting up keyframes. Each frame should be defined in a `frame { ... }` or `frameWithDelay { ... }` block.

The **first block** must be using `frame` and calling the `set` infix function in order to setup the initial values

Then, if order to animate the value, the next `frame` block needs to use `goto` in order to express the fact that the value should be animated

```
    frame {
        x set 100f
    }
    
    frame {
        x goto 500f
    }
``` 

# Interpolator
By default, the animation will use a LinearInterpolator, but each property can use a different interpolator on each frame:
```
    frame {
        x set 100f
        radius set 20f
    }
    
    frame {
        x goto 500f
    }
    
   frame {
        radius goto 50f by bounceInterpolator
   }
``` 

# More control
### Locking
In the previous example the radius is going to be animated from frame 0 to frame 2, so over 3 frames. In order to start the radius animation from frame 1, you can lock any property to keep the same value until a certain frame:
```
    frame {
        x set 100f
        radius set 20f
    }
    
    frame {
        x goto 500f
        radius lockSince last(radius)
    }
    
   frame {
        x goto 800f by EasingInterpolators.quadInOut
        radius goto 50f by bounceInterpolator
   }
``` 

### Frame position
By default, `frame{}` will add a new keyframe at the next integer position.
Ex: 
- If last frame is at position 1, next frame will be at position 2
- If last frame is at 1.5, next frame will be at position 2

If some delay is required between the frames, you can call `frameWithDelay` instead:
```kotlin
    frame { // At frame 0
        x set 0f
    }
    
    frame(2) { // At frame 2
        x set 0f
    }
    frameWithDelay(2) { // At frame 4 (2 frames after the previous one)
        x goto 100f
    }
```



### Normalizing (Optional)
This is an optional but recommended step. When the `frame` function is called, the position of the frame is by default the last frame position +1. Howver it's often useful to work with values between 0 and 1, the normalize feature of the library will do this for you if you follow the following steps: 

In order to use the `normalize()` feature, your class needs to implement the `Normalizable` interface.
You only need to override 1 val which represents the list of properties to be animated, like so:
```kotlin
class CircleData(
    val x: MutableList<FrameProperty<Float>> = mutableListOf(),
    val y: MutableList<FrameProperty<Float>> = mutableListOf(),
    val radius: MutableList<FrameProperty<Float>> = mutableListOf(),
    val color: MutableList<FrameProperty<Int>> = mutableListOf()
) : Normalizable {
    override val propertyList: List<List<FrameProperty<*>>> = mutableListOf(
        x,
        y,
        radius,
        color
    )
}
```
 
Once your model is built you can call the `normalize()` extension function on it. Alternatively you can call directly `FrameAnimationBuilder.createNormalized { ... }`
By default it will be normalized over 1, which means the first frame is at position 0 and the last one is at position 1

## Drawing

Once the keyframe object is built, there's extensions on List<FrameProperty> to animate:
```kotlin
    val frame = FrameAnimationBuilder.createNormalized<CircleData> { ... }
    ... 
    val currentX = frame.x.animate(animationProgress)
    val color = frame.color.animateColor(animationProgress) // Use animateColor to use ARGB evaluation
```

These values can then be used in a Canvas. Check the `app` module for examples  
 