package com.thorebenoit.keyframe

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        setContentView(valueAnimatorExampleView())
//        setContentView(valueAnimatorNormalizedExampleView())
        setContentView(graphView())

    }
}

// TODO Examples:
/**
 * - With value animator
 * - With progress bar
 * - With ScrollView/RecyclerView
 * - Advanced Canvas with createNormalized
 */
