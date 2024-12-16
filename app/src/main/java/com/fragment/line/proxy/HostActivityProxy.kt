package com.fragment.line.proxy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.FragmentManagerHost
import com.fragment.line.BlankFragment
import com.fragment.line.EmptyFragment
import com.fragment.line.R

class HostActivityProxy: FragmentManagerHost() {

    override fun attach(activity: Activity) {
        super.attach(activity)
        mActivity.setContentView(R.layout.activity_host)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val transaction = supportFragmentManager.beginTransaction()

        val blankFragment = BlankFragment.newInstance()
        val emptyFragment = EmptyFragment.newInstance()

        transaction.add(R.id.fragment_container, blankFragment)
        transaction.hide(blankFragment)
//        transaction.setMaxLifecycle(blankFragment, Lifecycle.State.CREATED)
        transaction.add(R.id.fragment_container, emptyFragment)
//        transaction.setMaxLifecycle(emptyFragment, Lifecycle.State.RESUMED)

        transaction.commit()

        mActivity.findViewById<Button>(R.id.btn1).setOnClickListener {
            supportFragmentManager.beginTransaction()
//                .setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim)
                .hide(emptyFragment)
//                .setMaxLifecycle(emptyFragment, Lifecycle.State.CREATED)
//                .add(R.id.fragment_container, blankFragment)
                .show(blankFragment)
//                .setMaxLifecycle(blankFragment, Lifecycle.State.RESUMED)
                .commit()
        }

        mActivity.findViewById<Button>(R.id.btn2).setOnClickListener {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_anim, R.anim.enter_anim)
                .hide(blankFragment)
//                .setMaxLifecycle(blankFragment, Lifecycle.State.CREATED)
                .show(emptyFragment)
//                .setMaxLifecycle(emptyFragment, Lifecycle.State.RESUMED)
                .commit()
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }
}