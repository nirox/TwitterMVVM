package com.mobgen.presentation.tweet

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mobgen.presentation.BaseViewModel
import com.mobgen.presentation.FragmentListener
import com.mobgen.presentation.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.tweet_fragment.*
import javax.inject.Inject

class TweetFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: TweetViewModel
    private lateinit var activity: FragmentListener

    companion object {
        const val TAG = "TweetFragment"
        private const val ARG_ID = "id"

        fun newInstance(id: Long): TweetFragment =
            TweetFragment().apply { arguments = Bundle().apply { putLong(ARG_ID, id) } }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.tweet_fragment, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        context?.let {
            activity = (it as FragmentListener)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.status.observe(this, Observer {
            it?.let { status ->
                if (status == BaseViewModel.Status.SUCCESS) {
                    userName.text = viewModel.tweet.name
                    userId.text = viewModel.tweet.id
                    twitterContent.text = viewModel.tweet.content
                    Glide.with(this).load(viewModel.tweet.image).apply(RequestOptions.circleCropTransform())
                        .into(userImage)

                    twitterMedia.visibility = View.GONE
                    twitterVideo.visibility = View.GONE
                    if (viewModel.tweet.videos.isNotEmpty()) {
                        twitterVideo.apply {
                            visibility = View.VISIBLE
                            setVideoPath(viewModel.tweet.videos.first())
                        }.start()

                        twitterVideo.setOnClickListener {
                            if (twitterVideo.isPlaying) {
                                twitterVideo.pause()
                            } else {
                                twitterVideo.start()
                            }
                        }

                        twitterVideo.setOnCompletionListener {
                            twitterVideo.seekTo(1)
                        }
                    } else {
                        if (viewModel.tweet.medias.isNotEmpty()) {
                            twitterMedia.visibility = View.VISIBLE
                            Glide.with(this).load(viewModel.tweet.medias.first())
                                .into(twitterMedia)
                        }
                    }

                }
            }
        })
        viewModel.loadData(arguments?.getLong(ARG_ID) ?: 0L)
    }

    override fun onResume() {
        super.onResume()
        activity.searchVisibily(false)
        activity.buttonBackInActionBar(true)
    }


}