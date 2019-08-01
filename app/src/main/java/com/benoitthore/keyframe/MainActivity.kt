package com.benoitthore.keyframe

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        setContentView(graphView())
//        setContentView(valueAnimatorExampleView())
//        setContentView(valueAnimatorNormalizedExampleView())
        setContentView(randomFrameExampleView())

    }
}

// TODO Examples:
/**
 * - With value animator
 * - With progress bar
 * - With ScrollView/RecyclerView
 * - Advanced Canvas with createNormalized
 */
