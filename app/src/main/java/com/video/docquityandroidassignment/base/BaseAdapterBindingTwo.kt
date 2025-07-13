package com.video.docquityandroidassignment.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class BaseAdapterBindingTwo<S : Any>(
    private val mContext: Context,
    objects: List<S>?,
    private val mListener: BindAdapterListener,
    private val layoutId: Int,
    private val stringExtractor: (item: S) -> String={""},
    private val diffCallback: ItemDiffCallback<S> = ItemDiffCallback()
) : RecyclerView.Adapter<BaseAdapterBindingTwo.DataBindingViewHolder>(), Filterable {
    private val TYPE_FOOTER: Int = 2
    private val TYPE_ITEM: Int = 1
    private val TYPE_HEADER: Int = 0
    private val TYPE_ERROR: Int = 0
    private var FOOTER: Int = 0
    private var HEADER: Int = 0
    private var ERROR: Int = 0
    private var layoutFooterId: Int = -1
    private var layoutHeaderId: Int = -1
    private var layoutErrorId: Int = -1
    var responseHeader: Any? = null
    var responseFooter: Any? = null

    private var mObjects: MutableList<S>? = ArrayList()
    private var originalItems: MutableList<S>? = ArrayList()

    private val filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val query = constraint!!.trim().toString().toLowerCase(Locale.getDefault())
            val filteredList = if (query.isEmpty()) {
                this@BaseAdapterBindingTwo.originalItems
            } else {
                this@BaseAdapterBindingTwo.originalItems!!.filter { item ->
                    stringExtractor(item).lowercase(Locale.getDefault()).contains(query)

                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

            val filteredList = (results?.values as? List<S> ?: emptyList()).toMutableList()
            this@BaseAdapterBindingTwo.mObjects = filteredList
            this@BaseAdapterBindingTwo.notifyDataSetChanged()
        }
    }

    override fun getFilter(): Filter {
        return filter
    }





    fun setFooter(layoutFooterId: Int) {
        this.FOOTER = 1
        this.layoutFooterId = layoutFooterId
        this.notifyDataSetChanged()
    }

    fun setFooter(layoutFooterId: Int,response:Any) {
        this.FOOTER = 1
        this.layoutFooterId = layoutFooterId
        this.responseFooter = response
        this.notifyDataSetChanged()
    }

    fun setHeader(layoutHeaderId: Int) {
        this.HEADER = 1
        this.layoutHeaderId = layoutHeaderId
        this.notifyDataSetChanged()
    }

    fun setErrorView(layoutHeaderId: Int) {
        this.ERROR = 1
        this.layoutErrorId = layoutHeaderId
        this.notifyDataSetChanged()
    }

    fun setHeader(layoutHeaderId: Int, response: Any) {
        this.HEADER = 1
        this.layoutHeaderId = layoutHeaderId
        this.responseHeader = response
        this.notifyDataSetChanged()
    }

    fun headerData(obj:Any) {
        responseHeader = obj
    }


    fun removeFooter() {
        this.FOOTER = 0
    }

    fun removeHeader() {
        this.HEADER = 0
    }
    fun removeERROR() {
        this.ERROR = 0
    }


//    private val mHolder: T? = null

    val list: List<S>?
        get() = mObjects


    init {
        mObjects = objects as MutableList<S>? ?: mObjects
        originalItems = objects ?: originalItems
    }
    //
//    fun setData(objects: List<S>?) {
//
//        this.mObjects = objects as MutableList<S>
//        this.originalItems = objects
//        this.notifyDataSetChanged()
//    }
    fun setData(objects: List<S>?) {
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return mObjects?.size ?: 0
            }

            override fun getNewListSize(): Int {
                return objects?.size ?: 0
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return diffCallback.areItemsTheSame(mObjects!![oldItemPosition], objects!![newItemPosition])
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return diffCallback.areContentsTheSame(mObjects!![oldItemPosition], objects!![newItemPosition])
            }
        })

        mObjects = objects?.toMutableList()
        originalItems = objects  as MutableList<S>
        diffResult.dispatchUpdatesTo(this)
    }



    fun addAll(objects: List<S>) {
        val oldSize = mObjects?.size ?: 0
        mObjects?.addAll(objects)
        notifyItemRangeInserted(oldSize, objects.size)
    }



    fun add(obj: S) {
        mObjects?.add(obj)
        notifyItemInserted(mObjects?.size ?: 0)
    }

    fun addNewItem(obj: S) {
        mObjects?.add(obj)
        notifyItemInserted(mObjects?.size ?: 0)
    }

    fun remove(index: Int) {
        if (index in 0 until (mObjects?.size ?: 0)) {
            mObjects?.removeAt(index)
            notifyItemRemoved(index)
        }
        //notifyItemRangeRemoved(index,mObjects.size());
//        this.notifyItemChanged(index, mObjects!!.size)

    }
    fun updateItem(position: Int,url:S){
        if (position in 0 until (mObjects?.size ?: 0)) {
            mObjects!![position] = url
            notifyItemChanged(position)
        }
    }
    fun remove(obj: S) {
        val index = mObjects?.indexOf(obj)
        if (index != null && index != -1) {
            mObjects?.removeAt(index)
            notifyItemRemoved(index)
        }
    }
    fun filteredList(objects: List<S>?) {
        mObjects = objects?.toMutableList()
        notifyDataSetChanged()
    }


    fun getItem(index: Int): S {
        if (mObjects?.size ?: 0 > 0) {
            return mObjects!![index - this.HEADER]
        } else {
            return mObjects?.get(index) ?: null as S
        }
    }

    fun getCount():Int{
        return  mObjects!!.size
    }
    override fun getItemViewType(position: Int): Int {
        return  when {
            isPositionHeader(position) -> TYPE_HEADER
            isPositionFooter(position) -> TYPE_FOOTER
            else -> TYPE_ITEM
        }
    }

    override fun getItemCount(): Int {
        // Add two more counts to accomodate header and footer
        return if (mObjects == null) 0 else (mObjects!!.size + this.HEADER + this.FOOTER + this.ERROR)

    }
    public fun getTotalListCount():Int{
        return if (mObjects == null) 0 else mObjects!!.size
    }

    private fun isPositionHeader(position: Int): Boolean {
        return if (this.HEADER == 1) position == 0 else false
    }

    private fun isPositionFooter(position: Int): Boolean {
        return if (this.FOOTER == 1) position == itemCount - 1 else false

    }
    fun getLastItem():Int{
        return if (mObjects == null) 0 else (mObjects!!.size )
    }


    fun refresh() {
        mObjects!!.clear()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)

        if (viewType == this.TYPE_FOOTER) {
            return DataBindingViewHolder(DataBindingUtil.inflate(layoutInflater, layoutFooterId, parent, false))
//            return mHolderFooterClass?.getConstructor(View::class.java)
//                ?.newInstance(DataBindingUtil.inflate(layoutInflater, layoutFooterId,parent, false))!!
        } else if (viewType == this.TYPE_HEADER) {
            return DataBindingViewHolder(DataBindingUtil.inflate(layoutInflater, layoutHeaderId, parent, false))
        } else if (viewType == this.TYPE_ITEM) {
            return DataBindingViewHolder(DataBindingUtil.inflate(layoutInflater, layoutId, parent, false))

        }else if (viewType == this.TYPE_ERROR) {
            return DataBindingViewHolder(DataBindingUtil.inflate(layoutInflater, layoutErrorId, parent, false))

        } else {
            return DataBindingViewHolder(DataBindingUtil.inflate(layoutInflater, layoutId, parent, false))
        }


    }


    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        mListener.let {
            it.onBindTwo(holder, position)
        }
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    interface BindAdapterListener {
        fun onBindTwo(holder: DataBindingViewHolder, position: Int)
    }

    companion object {
        private val TAG = "BaseRecyclerAdapter"
    }

    class DataBindingViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
}
