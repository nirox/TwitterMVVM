package com.mobgen.presentation

interface FragmentListener {
    fun goTweetFragment(id: Long)
    fun goTweetListFragment()
    fun goTweetListFragment(search: String)
    fun searchVisibily(check: Boolean)
    fun buttonBackInActionBar(check: Boolean)
    fun changeTitle(title: String)
}