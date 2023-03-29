package com.maetimes.android.pokekara.section.song.detail.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maetimes.android.pokekara.widget.KaraViewHolder

abstract class BaseRecyclerViewAdapter(
    open val onItemChildClick: (intent: String, data: Any) -> Unit,
    open val onItemClick: (data: Any) -> Unit
) : RecyclerView.Adapter<KaraViewHolder>() {
    protected val typeList: MutableList<Int> = mutableListOf()
    protected val itemList: MutableList<Any> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KaraViewHolder {
        return extensionOnCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: KaraViewHolder, position: Int) {
        extensionOnBindViewHolder(holder, position)
    }

    override fun onBindViewHolder(holder: KaraViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.size > 0) {
            extensionOnBindViewHolder(holder, position, payloads)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return typeList[position]
    }

    override fun getItemCount(): Int {
        return typeList.size
    }

    /**
     * 创建扩展的视图
     */

    abstract fun extensionOnCreateViewHolder(parent: ViewGroup, viewType: Int): KaraViewHolder

    /**
     * 绑定扩展的视图
     *
     * @param holder
     * @param position
     */
    abstract fun extensionOnBindViewHolder(holder: KaraViewHolder, position: Int)

    open fun extensionOnBindViewHolder(holder: KaraViewHolder, position: Int, payloads: MutableList<Any>) {}

    open fun clearData() {
        typeList.clear()
        itemList.clear()
    }

    open fun isEmpty(): Boolean {
        return typeList.isEmpty()
    }

    fun removeData(index: Int): Boolean {
        if (index < 0) {
            return false
        }
        if (index >= typeList.size || index >= itemList.size) {
            return false
        }

        typeList.removeAt(index)
        itemList.removeAt(index)

        return true
    }

    fun removeDataAndRefresh(index: Int): Boolean {
        if (removeData(index)) {
            notifyItemRemoved(index)
            if (index != itemList.size) { // 如果移除的是最后一个，忽略
                notifyItemRangeChanged(index, itemList.size - index);
            }
            return true
        }
        return false
    }

    open fun containType(type: Int): Boolean {
        return typeList.indexOf(type) > -1
    }

    fun addElement(bean: Any, type: Int) {
        addElements(bean, type, -1)
    }

    fun addElements(bean: Any, type: Int, index: Int) {
        if (index < 0 || index > itemList.size) {
            typeList.add(type)
            itemList.add(bean)
        } else {
            typeList.add(index, type)
            itemList.add(index, bean)
        }
    }

    companion object {
        var typeCount: Int = 0
    }

}

const val INTENT_CLICK_FOLLOW = "click_follow"