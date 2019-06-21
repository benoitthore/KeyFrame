package com.thorebenoit.keyframe

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.thorebenoit.lib.keyframe.FrameAnimationBuilder
import com.thorebenoit.lib.keyframe.FrameProperty
import com.thorebenoit.lib.keyframe.Normalisable
import com.thorebenoit.lib.keyframe.animateColor

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(valueAnimatorExampleView())

    }
}

// TODO Examples:
/**
 * - With value animator
 * - With progress bar
 * - With ScrollView/RecyclerView
 * - Advanced Canvas with createNormalised
 */
