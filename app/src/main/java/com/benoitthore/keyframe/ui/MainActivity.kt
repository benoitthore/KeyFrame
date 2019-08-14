package com.benoitthore.keyframe.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.benoitthore.keyframe.R

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