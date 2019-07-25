package com.example.mlappchallenge

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.MotionEvent
import android.view.GestureDetector
import android.view.View



private var mListener: OnItemClickListener? = null

interface OnItemClickListener {
    fun onItemClick(view: View, position: Int)
}

var mGestureDetector: GestureDetector? = null

class AccountViewItemClickListener : RecyclerView.OnItemTouchListener {

    constructor(context : Context, listener : OnItemClickListener) {
        mListener = listener

        mGestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }
        })
    }

    override fun onTouchEvent(p0: RecyclerView, p1: MotionEvent) {
    }

    override fun onInterceptTouchEvent(p0: RecyclerView, p1: MotionEvent): Boolean {
        val childView = p0.findChildViewUnder(p1.x, p1.y)
        if (childView != null && mListener != null && mGestureDetector!!.onTouchEvent(p1)) {
            mListener!!.onItemClick(childView, p0.getChildAdapterPosition(childView))
            return true
        }
        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(p0: Boolean) {

    }
}