package com.example.workmanagertutorial.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class RecyclerBindingAdapter<T>(
    private val holderLayout: Int,
    private val variableId: Int,
    private val actionsVariableId: Int = 0,
    private val actions: Any? = null,
    items: MutableList<T> = mutableListOf()
) :
    RecyclerView.Adapter<RecyclerBindingAdapter.BindingHolder>() {

    var items: MutableList<T> = items
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private var onItemClickListener: ((position: Int, item: T) -> Unit?)? = null

    init {
        this.items = ArrayList(items)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(holderLayout, parent, false)
        return BindingHolder(v)
    }


    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        val item = items[position]

        holder.binding?.apply {
            if (onItemClickListener != null)
                root?.setOnClickListener {
                    onItemClickListener?.invoke(position, item)
                }
            setVariable(variableId, item)
            if (actionsVariableId != 0) {
                setVariable(actionsVariableId, actions)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setOnItemClickListener(onItemClickListener: (position: Int, item: T) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }

    class BindingHolder(v: View) : RecyclerView.ViewHolder(v) {
        var binding: ViewDataBinding? = DataBindingUtil.bind(v)
    }
}


class PositionRecyclerBindingAdapter<T>(
    private val holderLayout: Int,
    private val variableId: Int,
    private val positionVariableId: Int,
    private val actionsVariableId: Int = 0,
    private val actions: Any? = null,
    items: MutableList<T> = mutableListOf()
) : RecyclerBindingAdapter<T>(holderLayout, variableId, actionsVariableId, actions, items) {

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.binding?.setVariable(positionVariableId, position)
    }
}

/*

fun RecyclerView.withDivider(@DrawableRes drawable: Int = R.drawable.default_divider): RecyclerView {
    this.context?.let {
        this.addItemDecoration(DividerItemDecoration(it).apply {
            orientation = DividerItemDecoration.VERTICAL
            divider = (it.getDrawable(drawable)!!)
        })
    }
    return this
}
*/
