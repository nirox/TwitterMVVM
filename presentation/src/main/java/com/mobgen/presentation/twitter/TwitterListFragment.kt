package com.mobgen.presentation.twitter

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobgen.domain.check
import com.mobgen.presentation.BaseViewModel
import com.mobgen.presentation.FragmentListener
import com.mobgen.presentation.R
import com.mobgen.presentation.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.twitter_fragment.*
import javax.inject.Inject


class TwitterListFragment : DaggerFragment() {

    lateinit var viewModel: TwitterViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var activity: FragmentListener


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
                activity.goTweetFragment(id)
            }

        })

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        context?.let {
            activity = it as FragmentListener
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.twitter_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TwitterViewModel::class.java)
        initView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.data.observe(this, Observer {
            it?.let { data ->
                when (data.status) {
                    BaseViewModel.Status.LOADING -> {
                        swipeRefresh.isRefreshing = true
                    }
                    BaseViewModel.Status.LOADING_NEXT_PAGE -> {
                        load_more_item_progress.visibility = View.VISIBLE
                    }
                    BaseViewModel.Status.SUCCESS -> {
                        swipeRefresh.isRefreshing = false
                        load_more_item_progress.visibility = View.INVISIBLE
                        twitterListAdapter.setData(data.tweets)
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
                activity.searchVisibily(true)
                activity.buttonBackInActionBar(false)

            },
            ifNotNull = {
                activity.searchVisibily(false)
                activity.buttonBackInActionBar(true)
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
                    && firstVisibleItemPosition >= 0 && viewModel.data.value?.status == BaseViewModel.Status.SUCCESS
                ) {
                    viewModel.nextPage()
                }
            }

        })

        swipeRefresh.setOnRefreshListener {
            getData(true)
        }

    }

    private fun getData(force: Boolean = false) {
        arguments?.getString(ARG_SEARCH).check(
            ifNull = {
                activity.changeTitle(getString(R.string.mobgen_time_line))
                viewModel.loadData(force)
            },
            ifNotNull = {
                activity.changeTitle("${getText(R.string.search)} - ${it.capitalize()}")
                viewModel.loadData(it, force)
            }
        )
    }


}