package com.fragment.line.proxy

import android.app.Activity
import android.content.Intent
import android.os.Bundle

class HostActivity : Activity() {

    private val activityProxy: HostActivityProxy = HostActivityProxy()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityProxy.attach(this)
        activityProxy.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        activityProxy.onStart()
    }

    override fun onResume() {
        super.onResume()
        activityProxy.onResume()
    }

    override fun onPause() {
        super.onPause()
        activityProxy.onPause()
    }

    override fun onStop() {
        super.onStop()
        activityProxy.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        activityProxy.onDestroy()
    }

    override fun onNewIntent(intent: Intent?) {
        activityProxy.onNewIntent(intent)
        super.onNewIntent(intent)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        activityProxy.onLowMemory()
    }
}