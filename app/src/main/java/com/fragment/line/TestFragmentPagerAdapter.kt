package com.fragment.line

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import androidx.viewpager.widget.PagerAdapter

class TestFragmentPagerAdapter(
    private val mFragmentManager: FragmentManager
): PagerAdapter() {

    private val logTag = "LOG/TestFragmentPagerAdapter@${Integer.toHexString(this.hashCode())}"

    private var mCurTransaction: FragmentTransaction? = null

    private var mCurrentPrimaryItem: Fragment? = null

    private var mExecutingFinishUpdate = false

    override fun getCount(): Int {
        return Int.MAX_VALUE
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return (`object` as Fragment).view == view
    }

    override fun startUpdate(container: ViewGroup) {
        check(container.id != View.NO_ID) {
            ("ViewPager with adapter $this requires a view id")
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction()
        }

        val fragment = if (position % 2 == 0) {
            BlankFragment()
        } else {
            EmptyFragment()
        }

        mCurTransaction?.add(container.id, fragment)

        return fragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val fragment = `object` as Fragment

        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction()
        }

        mCurTransaction?.remove(fragment)
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        val fragment = `object` as Fragment
        Log.d(logTag, "setPrimaryItem, position=$position, new=$fragment, old=$mCurrentPrimaryItem")
        if (fragment != mCurrentPrimaryItem) {
            if (mCurTransaction == null) {
                mCurTransaction = mFragmentManager.beginTransaction()
            }

            if (mCurrentPrimaryItem != null) {
                mCurTransaction?.setMaxLifecycle(mCurrentPrimaryItem!!, Lifecycle.State.STARTED)
            }

            mCurTransaction?.setMaxLifecycle(fragment, Lifecycle.State.RESUMED)

            mCurrentPrimaryItem = fragment
        }
    }

    override fun finishUpdate(container: ViewGroup) {
        if (mCurTransaction != null) {
            // We drop any transactions that attempt to be committed
            // from a re-entrant call to finishUpdate(). We need to
            // do this as a workaround for Robolectric running measure/layout
            // calls inline rather than allowing them to be posted
            // as they would on a real device.
            if (!mExecutingFinishUpdate) {
                try {
                    mExecutingFinishUpdate = true
                    mCurTransaction?.commitNowAllowingStateLoss()
                } finally {
                    mExecutingFinishUpdate = false
                }
            }
            mCurTransaction = null
        }
    }
}