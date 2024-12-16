package com.fragment.line

import android.animation.Animator
import android.animation.AnimatorInflater
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class BlankFragment : Fragment() {

    private val logTag = "LOG/BlankFragment@${Integer.toHexString(this.hashCode())}"

    companion object {
        @JvmStatic
        fun newInstance() = BlankFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(logTag, "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(logTag, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(logTag, "onCreateView")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(logTag, "onViewCreated")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(logTag, "onActivityCreated")
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

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(logTag, "onDestroyView")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(logTag, "onDetach")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.d(logTag, "onHiddenChanged, hidden: $hidden")
    }

//    override fun onCreateAnimator(transit: Int, enter: Boolean, nextAnim: Int): Animator? {
//        Log.d(logTag, "onCreateAnimator, enter=$enter")
//        if (nextAnim == 0) return null
//        val animator = AnimatorInflater.loadAnimator(context, nextAnim)
//        animator.addListener(object : Animator.AnimatorListener {
//            override fun onAnimationStart(animator: Animator) {
//                Log.d(logTag, "onAnimationStart")
//            }
//
//            override fun onAnimationEnd(animator: Animator) {
//                Log.d(logTag, "onAnimationEnd")
//            }
//
//            override fun onAnimationCancel(animator: Animator) {}
//            override fun onAnimationRepeat(animator: Animator) {}
//        })
//        return animator
//    }
}