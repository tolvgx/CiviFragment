package com.fragment.line

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity

class SecondActivity : FragmentActivity() {

    private val hashCode = Integer.toHexString(this.hashCode())

    private val logTag = "LOG/SecondActivity@$hashCode"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        Log.d(logTag, "onCreate")
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