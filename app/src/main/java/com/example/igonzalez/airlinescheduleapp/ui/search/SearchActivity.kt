package com.example.igonzalez.airlinescheduleapp.ui.search

import android.os.Bundle
import com.example.igonzalez.airlinescheduleapp.R
import com.example.igonzalez.airlinescheduleapp.ui.base.BaseActivity
import com.example.igonzalez.airlinescheduleapp.ui.schedule.ScheduleActivity
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity<SearchPresenter>(), SearchView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        btn_search.setOnClickListener {
            beginSearch()
        }
    }

    override fun setPresenter(): SearchPresenter {
        return SearchPresenter(this)
    }

    private fun beginSearch() {}
}
