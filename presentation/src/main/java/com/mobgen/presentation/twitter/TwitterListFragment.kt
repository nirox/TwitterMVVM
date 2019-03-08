package com.mobgen.presentation.twitter

import android.app.Activity
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobgen.domain.check
import com.mobgen.presentation.BaseViewModel
import com.mobgen.presentation.FragmentListener
import com.mobgen.presentation.MainActivity
import com.mobgen.presentation.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.twitter_fragment.*
import javax.inject.Inject


class TwitterListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: TwitterViewModel


    companion object {
        const val TAG = "TwitterListFragment"
        private const val SCROLL_RANGE_TO_NEXT_PAGE = 4
        private const val ARG_SEARCH = "search"

        fun newInstance() = TwitterListFragment()
        fun newInstance(search: String) =
            TwitterListFragment().apply { arguments = Bundle().apply { putString(ARG_SEARCH, search) } }
    }

    lateinit var linearLayoutManager: LinearLayoutManager
    private val twitterListAdapter: TwitterListAdapter =
        TwitterListAdapter(listOf(), object : TwitterListAdapter.OnClickItemListener {
            override fun onClickItem(id: Long) {
                (context as MainActivity).goTweetFragment(id)
            }

        })


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.twitter_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.status.observe(this, Observer {
            it?.let { status ->
                when (status) {
                    BaseViewModel.Status.LOADING -> {
                        swipeRefresh.isRefreshing = true
                    }
                    BaseViewModel.Status.LOADING_NEXT_PAGE -> {
                        load_more_item_progress.visibility = View.VISIBLE
                    }
                    BaseViewModel.Status.SUCCESS -> {
                        swipeRefresh.isRefreshing = false
                        load_more_item_progress.visibility = View.INVISIBLE
                        twitterListAdapter.setData(viewModel.tweets)
                    }
                    BaseViewModel.Status.ERROR -> {
                        //TODO Error method
                    }
                }

            }
        })
        getData()
    }

    override fun onResume() {
        super.onResume()
        arguments?.getString(ARG_SEARCH).check(
            ifNull = {
                (context as FragmentListener).searchVisibily(true)
                (context as FragmentListener).buttonBackInActionBar(false)

            },
            ifNotNull = {
                (context as FragmentListener).searchVisibily(false)
                (context as FragmentListener).buttonBackInActionBar(true)
            }
        )
    }

    private fun initView() {
        linearLayoutManager = LinearLayoutManager(context)
        twitterList.layoutManager = linearLayoutManager
        twitterList.adapter = twitterListAdapter
        twitterList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = linearLayoutManager.childCount
                val totalItemCount = linearLayoutManager.itemCount
                val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount - SCROLL_RANGE_TO_NEXT_PAGE
                    && firstVisibleItemPosition >= 0 && viewModel.status.value != BaseViewModel.Status.LOADING
                ) {
                    viewModel.nextPage()
                }
            }

        })

        swipeRefresh.setOnRefreshListener {
            getData()
        }

    }

    private fun getData() {
        arguments?.getString(ARG_SEARCH).check(
            ifNull = {
                (context as Activity).title = getText(R.string.mobgen_time_line)
                viewModel.loadData()
            },
            ifNotNull = {
                (context as Activity).title = "${getText(R.string.search)} - ${it.capitalize()}"
                viewModel.loadData(it)
            }
        )
    }


}