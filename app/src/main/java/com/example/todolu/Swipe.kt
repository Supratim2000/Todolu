/*
package com.example.todolu

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class Swipe(todoAdapter: TodoAdapter, listener: SwipedLeft): ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
    private val todoAdapter: TodoAdapter
    private val listener: SwipedLeft
    init {
        this.todoAdapter = todoAdapter
        this.listener = listener
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        if(direction == ItemTouchHelper.LEFT) {
            listener.onSwipeLeft(position)
        } else {
            //need to add edit item code
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        var icon: Drawable? = null
        var background: ColorDrawable? = null

        val itemView: View = viewHolder.itemView
        val backgroundCornerOffset = 20

        if(dX > 0) {
            icon = ContextCompat.getDrawable(todoAdapter.getActivity(), R.drawable.ic_baseline_edit)
            background= ColorDrawable(ContextCompat.getColor(todoAdapter.getActivity(), R.color.dark_pink))
        } else {
            icon = ContextCompat.getDrawable(todoAdapter.getActivity(), R.drawable.ic_baseline_delete)
            background = ColorDrawable(ContextCompat.getColor(todoAdapter.getActivity(), R.color.blue))
        }

        val iconMargin: Int = (itemView.height - icon!!.intrinsicHeight)/2
        val iconTop: Int = itemView.top + ((itemView.height - icon.intrinsicHeight)/2)
        val iconBottom: Int = iconTop + icon.intrinsicHeight

        if(dX > 0) {
            val iconLeft: Int = itemView.left + iconMargin
            val iconRight: Int = itemView.left + iconMargin + icon.intrinsicWidth
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

            background.setBounds(itemView.left, itemView.top, itemView.left + (dX.toInt()) + backgroundCornerOffset, itemView.bottom )
        } else if (dX < 0) {
            val iconLeft: Int = itemView.right - iconMargin - icon.intrinsicWidth
            val iconRight: Int = itemView.right - iconMargin
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

            background.setBounds(itemView.right + (dX.toInt()) - backgroundCornerOffset, itemView.top, itemView.right, itemView.bottom)
        } else {
            background.setBounds(0, 0, 0, 0)
        }

        background.draw(c)
        icon.draw(c)
    }
}

interface SwipedLeft {
    fun onSwipeLeft(position: Int)
}*/




package com.example.todolu

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class Swipe(todoAdapter: TodoAdapter, listener1: SwipedLeft, listener2: SwipedRight): ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
    private val todoAdapter: TodoAdapter
    private val listener1: SwipedLeft
    private val listener2: SwipedRight
    init {
        this.todoAdapter = todoAdapter
        this.listener1 = listener1
        this.listener2 = listener2
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        if(direction == ItemTouchHelper.LEFT) {
            listener1.onSwipeLeft(position)
        } else {
            listener2.onSwipeRight(position)
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        var icon: Drawable? = null
        var background: ColorDrawable? = null

        val itemView: View = viewHolder.itemView
        val backgroundCornerOffset = 20

        if(dX > 0) {
            icon = ContextCompat.getDrawable(todoAdapter.getActivity(), R.drawable.ic_baseline_edit)
            background= ColorDrawable(ContextCompat.getColor(todoAdapter.getActivity(), R.color.dark_pink))
        } else {
            icon = ContextCompat.getDrawable(todoAdapter.getActivity(), R.drawable.ic_baseline_delete)
            background = ColorDrawable(ContextCompat.getColor(todoAdapter.getActivity(), R.color.blue))
        }

        val iconMargin: Int = (itemView.height - icon!!.intrinsicHeight)/2
        val iconTop: Int = itemView.top + ((itemView.height - icon.intrinsicHeight)/2)
        val iconBottom: Int = iconTop + icon.intrinsicHeight

        if(dX > 0) {
            val iconLeft: Int = itemView.left + iconMargin
            val iconRight: Int = itemView.left + iconMargin + icon.intrinsicWidth
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

            background.setBounds(itemView.left, itemView.top, itemView.left + (dX.toInt()) + backgroundCornerOffset, itemView.bottom )
        } else if (dX < 0) {
            val iconLeft: Int = itemView.right - iconMargin - icon.intrinsicWidth
            val iconRight: Int = itemView.right - iconMargin
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

            background.setBounds(itemView.right + (dX.toInt()) - backgroundCornerOffset, itemView.top, itemView.right, itemView.bottom)
        } else {
            background.setBounds(0, 0, 0, 0)
        }

        background.draw(c)
        icon.draw(c)
    }
}

interface SwipedLeft {
    fun onSwipeLeft(position: Int)
}

interface SwipedRight {
    fun onSwipeRight(position: Int)
}