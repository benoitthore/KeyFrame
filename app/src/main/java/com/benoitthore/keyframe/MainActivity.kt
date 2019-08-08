package com.benoitthore.keyframe

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.benoitthore.keyframe.core.EasingInterpolators
import com.benoitthore.keyframe.core.FrameAnimationBuilder
import com.benoitthore.keyframe.core.percent
import com.benoitthore.keyframe.examples.SeekBarExampleFragment

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        supportFragmentManager
            .beginTransaction()
            .apply {
                add(R.id.fragment_holder, SelectorFragment())
                commit()
            }
    }

    fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .apply {
                add(R.id.fragment_holder, fragment)
                addToBackStack(null)
                commit()
            }
    }


}

// TODO Examples:
/**
 * - With value animator
 * - With progress bar
 * - With ScrollView/RecyclerView
 * - Advanced Canvas with createNormalized
 */
