package com.example.igonzalez.airlinescheduleapp.ui.search

import android.content.Context
import android.os.Bundle
import com.example.igonzalez.airlinescheduleapp.R
import com.example.igonzalez.airlinescheduleapp.ui.base.BaseActivity
import com.example.igonzalez.airlinescheduleapp.ui.schedule.ScheduleActivity
import kotlinx.android.synthetic.main.activity_search.*
import java.text.SimpleDateFormat
import java.util.*

class SearchActivity : BaseActivity<SearchPresenter>(), SearchView {

    private lateinit var datePicked: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        presenter.makeAuthTokenRequest()

        configView()
    }

    override fun setPresenter(): SearchPresenter {
        return SearchPresenter(this)
    }

    override fun saveAuthToken(accessToken: String) {
        val sharedPref = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        ) ?: return
        with(sharedPref.edit()) {
            putString("API_ACCESS_TOKEN", "Bearer $accessToken")
            apply()
        }
    }

    private fun configView() {
        // Calendar
        val calendar = Calendar.getInstance()
        calendar_view_search_calendar.minDate = calendar.time.time
        val format = SimpleDateFormat("yyyy-MM-dd")
        datePicked = format.format(calendar.time)
        calendar_view_search_calendar.setOnDateChangeListener { _, year, month, day ->
            dateFormat(year, month, day)
        }

        // Search button
        btn_search_search.setOnClickListener {
            beginSearch()
        }
    }

    private fun dateFormat(year: Int, month: Int, day: Int) {
        datePicked = "$year-"
        datePicked += if (month < 9) "0${(month + 1)}-" else "${(month + 1)}-"
        datePicked += if (day < 10) "0$day" else "$day"
    }

    private fun beginSearch() {
        val intent = ScheduleActivity.newIntent(
            this,
            edit_text_search_origin.text.toString(),
            edit_text_search_destination.text.toString(),
            datePicked
        )
        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
        presenter.disposeSubscription()
    }
}
