package com.fragment.line

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener

class TestViewPagerActivity : FragmentActivity() {

    private val logTag = "LOG/TestViewPagerActivity@${Integer.toHexString(this.hashCode())}"

    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_viewpager)
        Log.d(logTag, "onCreate")

        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = TestFragmentPagerAdapter(supportFragmentManager)
        viewPager.addOnPageChangeListener(object : OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                Log.d(logTag, "onPageScrolled, position=$position")
            }

            override fun onPageSelected(position: Int) {
                Log.d(logTag, "onPageSelected, position=$position")
            }

            override fun onPageScrollStateChanged(state: Int) {
                Log.d(logTag, "onPageScrollStateChanged, state=$state")
            }

        })
    }

    override fun onStart() {
        super.onStart()
        Log.d(logTag, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(logTag, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(logTag, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(logTag, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(logTag, "onDestroy")
    }
}