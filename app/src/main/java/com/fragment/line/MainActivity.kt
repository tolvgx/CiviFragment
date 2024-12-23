package com.fragment.line

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import com.fragment.line.proxy.HostActivity

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = supportFragmentManager.beginTransaction()

        val blankFragment = BlankFragment.newInstance()
        val emptyFragment = EmptyFragment.newInstance()

        transaction.add(R.id.fragment_container, blankFragment)
        transaction.hide(blankFragment)
        transaction.setMaxLifecycle(blankFragment, Lifecycle.State.CREATED)
        transaction.add(R.id.fragment_container, emptyFragment)
        transaction.setMaxLifecycle(emptyFragment, Lifecycle.State.RESUMED)

        transaction.commit()

        findViewById<Button>(R.id.btn1).setOnClickListener {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim)
                .hide(emptyFragment)
                .setMaxLifecycle(emptyFragment, Lifecycle.State.CREATED)
//                .add(R.id.fragment_container, blankFragment)
                .show(blankFragment)
                .setMaxLifecycle(blankFragment, Lifecycle.State.RESUMED)
                .commit()
        }

        findViewById<Button>(R.id.btn2).setOnClickListener {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_anim, R.anim.enter_anim)
                .hide(blankFragment)
                .setMaxLifecycle(blankFragment, Lifecycle.State.CREATED)
                .show(emptyFragment)
                .setMaxLifecycle(emptyFragment, Lifecycle.State.RESUMED)
                .commit()
        }

        findViewById<Button>(R.id.btn_jump).setOnClickListener {
            startActivity(Intent(this, HostActivity::class.java))
        }
    }
}