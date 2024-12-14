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

    private val hashCode = Integer.toHexString(this.hashCode())

    private val logTag = "Test/BlankFragment@$hashCode"

    private var isResume = false

    companion object {
        @JvmStatic
        fun newInstance() = BlankFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(logTag, "onAttach, hidden: $isHidden")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(logTag, "onCreate, hidden: $isHidden")
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

    override fun onStart() {
        super.onStart()
        Log.d(logTag, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(logTag, "onResume, hidden: $isHidden")
        isResume = true
    }

    override fun onPause() {
        super.onPause()
        Log.d(logTag, "onPause, hidden: $isHidden")
        isResume = false
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
//        Log.d(logTag, "onHiddenChanged, hidden: $hidden, isResume: $isResume")
    }

//    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
//        Log.d(logTag, "onCreateAnimation, transit: $transit, enter: $enter, nextAnim: $nextAnim")
//
//        val animation = AnimationUtils.loadAnimation(context, nextAnim)
//        animation.setAnimationListener(object : Animation.AnimationListener {
//            override fun onAnimationStart(animation: Animation?) {
//                Log.d(logTag, "onAnimationStart")
//            }
//
//            override fun onAnimationEnd(animation: Animation?) {
//                Log.d(logTag, "onAnimationEnd")
//            }
//
//            override fun onAnimationRepeat(animation: Animation?) {
//                Log.d(logTag, "onAnimationRepeat")
//            }
//        })
//
//        return animation
//    }

    override fun onCreateAnimator(transit: Int, enter: Boolean, nextAnim: Int): Animator? {
        Log.d(logTag, "onCreateAnimator, enter=$enter")
        if (nextAnim == 0) return null
        val animator = AnimatorInflater.loadAnimator(context, nextAnim)
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {
                Log.d(logTag, "onAnimationStart")
            }

            override fun onAnimationEnd(animator: Animator) {
                Log.d(logTag, "onAnimationEnd")
            }

            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
        return animator
    }
}