package com.mobgen.presentation

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import com.mobgen.presentation.adapter.OnQueryTextListenerAdapter
import com.mobgen.presentation.tweet.TweetFragment
import com.mobgen.presentation.twitter.TwitterListFragment
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), FragmentListener {

    @Inject
    lateinit var viewModel: MainViewModel
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        viewModel.status.observe(this, Observer {
            it?.let { status ->
                if (status == BaseViewModel.Status.SUCCESS) {
                    goTweetListFragment()
                }
            }
        })
        viewModel.authenticate()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val visibility = if (::searchView.isInitialized) searchView.visibility else View.VISIBLE
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        searchView = searchItem?.actionView as SearchView
        searchView.queryHint = getText(R.string.search)
        searchView.visibility = visibility
        searchView.setOnQueryTextListener(object : OnQueryTextListenerAdapter() {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (it.isNotBlank()) {
                        goTweetListFragment(it)
                    }
                }
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun goTweetFragment(id: Long) {
        resetSearch()
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right,
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
            )
            .replace(R.id.main, TweetFragment.newInstance(id))
            .addToBackStack(TweetFragment.TAG)
            .commit()
    }


    override fun goTweetListFragment() {
        resetSearch()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.main, TwitterListFragment.newInstance())
            .commit()
    }

    override fun goTweetListFragment(search: String) {
        resetSearch()
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right,
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
            )
            .replace(R.id.main, TwitterListFragment.newInstance(search))
            .addToBackStack(TwitterListFragment.TAG)
            .commit()
    }

    override fun searchVisibily(check: Boolean) {
        if (::searchView.isInitialized)
            searchView.visibility = if (check) View.VISIBLE else View.INVISIBLE
    }

    override fun buttonBackInActionBar(check: Boolean) {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(check)
            setDisplayShowHomeEnabled(check)
        }
    }

    private fun resetSearch() {
        searchView.setQuery("", false)
        searchView.isIconified = true
    }
}