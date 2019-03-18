package com.mobgen.presentation.twitter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mobgen.presentation.R
import com.mobgen.presentation.ViewHolder
import kotlinx.android.synthetic.main.item_tweet.view.*

class TwitterListAdapter(private var list: List<TweetBindView>, private val listener: OnClickItemListener) :
    RecyclerView.Adapter<TwitterListAdapter.DataViewHolder>() {

    companion object {
        private const val RADIUS = 40
    }

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
            itemView.twitterVideo.setOnClickListener {
                if (itemView.twitterVideo.isPlaying) {
                    itemView.twitterVideo.pause()
                } else {
                    itemView.twitterVideo.start()
                }
            }
            itemView.twitterVideo.setOnCompletionListener {
                itemView.twitterVideo.seekTo(1)
            }

        }

        override fun bind(value: TweetBindView) {
            Glide.with(itemView.context).load(value.image).apply(RequestOptions.circleCropTransform())
                .into(itemView.userImage)
            itemView.userId.text = value.userId
            itemView.userName.text = value.name
            itemView.date.text = String.format(itemView.context.getString(R.string.dateFormat), value.date)
            itemView.twitterContent.text = value.content
            itemView.twitterMedia.visibility = View.GONE
            itemView.twitterVideoContent.visibility = View.GONE
            if (value.videos.isNotEmpty()) {
                itemView.twitterVideoContent.visibility = View.VISIBLE
                itemView.twitterVideo.apply {
                    setVideoPath(value.videos.first())
                    seekTo(1)
                }
            } else {
                if (value.medias.isNotEmpty()) {
                    itemView.twitterMedia.visibility = View.VISIBLE
                    Glide.with(itemView.context).load(value.medias.first())
                        .apply(RequestOptions().transform(FitCenter(), RoundedCorners(RADIUS)))
                        .into(itemView.twitterMedia)
                }
            }


        }

    }

    interface OnClickItemListener {
        fun onClickItem(id: Long)
    }
}