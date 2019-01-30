package com.example.igonzalez.airlinescheduleapp.ui.search

import android.content.Context
import android.os.Bundle
import com.example.igonzalez.airlinescheduleapp.R
import com.example.igonzalez.airlinescheduleapp.ui.base.BaseActivity
import com.example.igonzalez.airlinescheduleapp.ui.schedule.ScheduleActivity
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity<SearchPresenter>(), SearchView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        presenter.makeAuthTokenRequest()

        btn_search.setOnClickListener {
            beginSearch()
        }
    }

    override fun setPresenter(): SearchPresenter {
        return SearchPresenter(this)
    }

    override fun saveAuthToken(accessToken: String) {
        val sharedPref = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("API_ACCESS_TOKEN", "Bearer $accessToken")
            apply()
        }
    }

    private fun beginSearch() {
        val intent = ScheduleActivity.newIntent(this, "ZRH","FRA","2019-01-23")
        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
        presenter.disposeSubscription()
    }
}
