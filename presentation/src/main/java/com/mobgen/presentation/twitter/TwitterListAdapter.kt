package com.mobgen.presentation.twitter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mobgen.presentation.R
import com.mobgen.presentation.ViewHolder
import kotlinx.android.synthetic.main.item_tweet.view.*

class TwitterListAdapter(private var list: List<TweetBindView>, private val listener: OnClickItemListener) :
    RecyclerView.Adapter<TwitterListAdapter.DataViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int) = DataViewHolder(
        LayoutInflater.from(viewGroup.context).inflate(
            R.layout.item_tweet, viewGroup, false
        ), listener
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(dataViewHolder: TwitterListAdapter.DataViewHolder, position: Int) {
        dataViewHolder.bind(list[dataViewHolder.adapterPosition])
    }

    fun setData(list: List<TweetBindView>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class DataViewHolder(itemView: View, listener: OnClickItemListener) : RecyclerView.ViewHolder(itemView),
        ViewHolder<TweetBindView> {

        init {
            itemView.setOnClickListener {
                listener.onClickItem(list[adapterPosition].id)
            }
        }

        override fun bind(value: TweetBindView) {
            Glide.with(itemView.context).load(value.image).apply(RequestOptions.circleCropTransform())
                .into(itemView.userImage)
            itemView.userId.text = value.userId
            itemView.userName.text = value.name
            itemView.twitterContent.text = value.content
        }

    }

    interface OnClickItemListener {
        fun onClickItem(id: Long)
    }
}