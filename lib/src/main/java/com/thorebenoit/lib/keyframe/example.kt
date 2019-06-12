//package com.thorebenoit.lib.keyframe
//
//import com.thorebenoit.lib.keyframe.utils.i
//import com.thorebenoit.lib.keyframe.utils.percent
//
//
//data class AlphaXY(
//    val x: MutableList<FrameProperty<Float>> = mutableListOf(),
//    val y: MutableList<FrameProperty<Float>> = mutableListOf(),
//    val radius: MutableList<FrameProperty<Float>> = mutableListOf(),
//    val color: MutableList<FrameProperty<Int>> = mutableListOf()
//) : Normalisable {
//    override var propertyList: List<List<FrameProperty<*>>> = mutableListOf(
//        x,
//        y,
//        radius,
//        color
//    )
//
//}
//
//
//object KeyFrameAnimationExample {
//
//    private val red = 0xFF_FF_00_00.i
//    private val black = 0xFF_00_00_00.i
//    private val blue = 0xFF_00_00_FF.i
//    private val green = 0xFF_00_FF_00.i
//    private val yellow = 0xFF_FF_FF_00.i
//
//    fun test() {
//        keyFrameAnim.color.animateColor(.5)
//    }
//
//
//    val keyFrameAnim: AlphaXY = FrameAnimationBuilder.createNormalised<AlphaXY> {
//
//        with(it) {
//            // Avoids it.frame on every line that uses frame
//
//            frame {
//                x set 0.percent
//                y set 0.percent
//                radius set 25f
//                color set red
//            }
//
//            frame {
//                color goto blue
//                x goto 100.percent by EasingInterpolators.accelerate(3)
//                y goto 100.percent by EasingInterpolators.accelerate()
//            }
//
//            frame {
//                color goto green
//                x goto 50.percent by EasingInterpolators.deccelerate()
//                y goto 50.percent by EasingInterpolators.deccelerate(3)
//                radius lockSince last(radius) // keep last value up to this frame
//            }
//
//            frameAfter(.5) {
//                color goto blue
//                radius goto (last(radius)?.data ?: 25f) / 2f
//            }
//
//            frame {
//                color goto yellow
//                x goto 0.percent by EasingInterpolators.quadInOut
//                y goto 100.percent by EasingInterpolators.quadInOut
//                radius goto 50f by bounceInterpolator
//            }
//
//            frame {
//                x goto 10.percent by EasingInterpolators.accelerate(6)
//            }
//
//            frame {
//                color goto 0xFF_5592FF.i
//                x goto 50.percent by EasingInterpolators.deccelerate(3)
//                y goto 50.percent by EasingInterpolators.deccelerate(3)
//            }
//
//        }
//    }
//
//}
//
//
///*
//
//import com.thorebenoit.enamel.core.animations.bounceInterpolator
//import com.thorebenoit.enamel.core.math.percent
//import com.thorebenoit.enamel.core.print
//import com.thorebenoit.enamel.core.threading.coroutine
//import com.thorebenoit.enamel.core.threading.forEach
//import com.thorebenoit.enamel.core.threading.receiveAll
//import com.thorebenoit.enamel.core.time.ETimerAnimator
//import com.thorebenoit.enamel.core.toJson
//import com.thorebenoit.enamel.geometry.figures.line
//import com.thorebenoit.enamel.geometry.primitives.point
//import com.thorebenoit.enamel.geometry.toCircle
//import com.thorebenoit.enamel.kotlin.animations.keyframe.FrameAnimationBuilder
//import com.thorebenoit.enamel.kotlin.core.color.*
//import com.thorebenoit.lib.keyframe.AlphaXY
//import com.thorebenoit.lib.keyframe.KeyFrameAnimationExample
//import com.thorebenoit.enamel.kotlin.keyframe.animate
//import com.thorebenoit.enamel.kotlin.keyframe.animateColor
//import com.thorebenoit.enamel.processingtest.examples.steering.*
//import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPApplet
//import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.PlaygroundApplet
//import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.pushPop
//import com.thorebenoit.enamel.processingtest.visualgit.FileVehicleManager
//import com.thorebenoit.enamel.processingtest.visualgit.GitManager
//import com.thorebenoit.enamel.processingtest.visualgit.getFileDiff
//import kotlinx.coroutines.runBlocking
//
//
//////
//import java.io.File
//
//
////
//object ProcessingTestMain {
//
//    val animationData = FrameAnimationBuilder.createNormalised<AlphaXY> {
//
//        with(it) {
//            // Avoids it.frame on every line that uses frame
//
//            frame {
//                x set 0.percent
//                y set 0.percent
//                radius set 25f
//                color set red
//            }
//
//            frame {
//                x goto 100.percent
//                y goto 100.percent
//                color set blue
//            }
//
//            frame {
//                x goto 50.percent by bounceInterpolator
//                y goto 50.percent by bounceInterpolator
//                color goto green
//            }
//
//        }
//    }
//
//    @JvmStatic
//    fun main(args: Array<String>) {
//
//        val timer = ETimerAnimator(5_000)
//        timer.repeatMode = ETimerAnimator.RepeatMode.RESTART
//
//        PlaygroundApplet.start(900, 900) {
//
//
//            onDraw {
//                background(255)
//
////                fill(blue)
//
//                val color = animationData.color.animateColor(timer.progress)!!
//                val x = animationData.x.animate(timer.progress)!!
//                val y = animationData.y.animate(timer.progress)!!
//                val radius = animationData.radius.animate(timer.progress)!!
//
//                fill(color)
//                (x point y).selfMult(width, height).toCircle(radius).draw()
//            }
//
//        }
//
//
//        return
//
//
//    }
//
//}
//
//
// */