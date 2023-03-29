package com.maetimes.android.pokekara.section.song.detail.adapter

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil

/**
 * Day：2023/3/29 15:48
 * @author zhanglei
 */
abstract class BaseDiffCallback<T>(private val oldList: List<T>, private val newList: List<T>) : DiffUtil.Callback() {
    private val tempOldList = mutableListOf<T>()
    init {
        tempOldList.addAll(oldList)
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return tempOldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun getOldListSize() = tempOldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return extensionAreContentsTheSame(tempOldList[oldItemPosition], newList[newItemPosition])
    }

    abstract fun extensionAreContentsTheSame(oldItem: T, newItem: T): Boolean

    /**
     * 调用在DiffResult.dispatchUpdatesTo
     * 所以在调用之前不要操作oldList，或者oldList为要用到的oldList的副本
     * eg：
     *  val diffCallback = SongRankItemUiStateDiffCallback(data, targetData)
     *  val diffResult = DiffUtil.calculateDiff(diffCallback)
     *  data.clear()
     *  data.addAll(targetData)
     *  diffResult.dispatchUpdatesTo(this)
     *  会导致old和new在调用该方法时相同
     */
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val bundle = extensionGetChangePayload(tempOldList[oldItemPosition], newList[newItemPosition])
        return bundle ?: super.getChangePayload(oldItemPosition, newItemPosition)
    }

    abstract fun extensionGetChangePayload(oldItem: T, newItem: T): Bundle?

    companion object {
        const val PAYLOAD_BUNDLE_KEY = "payload_bundle"
    }
}