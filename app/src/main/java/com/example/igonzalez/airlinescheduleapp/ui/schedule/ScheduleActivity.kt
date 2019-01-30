package com.example.igonzalez.airlinescheduleapp.ui.schedule

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.example.igonzalez.airlinescheduleapp.R
import com.example.igonzalez.airlinescheduleapp.databinding.ActivityScheduleBinding
import com.example.igonzalez.airlinescheduleapp.model.Entities
import com.example.igonzalez.airlinescheduleapp.ui.base.BaseActivity

class ScheduleActivity : BaseActivity<SchedulePresenter>(), ScheduleView {

    private lateinit var binding: ActivityScheduleBinding
    private val scheduleAdapter = ScheduleAdapter(this)

    companion object {
        private const val INTENT_ORIGIN = "origin"
        private const val INTENT_DESTINATION = "destination"
        private const val INTENT_FROM_DATE_TIME = "fromDateTime"

        fun newIntent(context: Context, origin: String, destination: String, fromDateTime: String): Intent {
            val intent = Intent(context, ScheduleActivity::class.java)
            intent.putExtra(INTENT_ORIGIN, origin)
            intent.putExtra(INTENT_DESTINATION, destination)
            intent.putExtra(INTENT_FROM_DATE_TIME, fromDateTime)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_schedule)
        binding.adapter = scheduleAdapter
        binding.layoutManager = LinearLayoutManager(this)
        binding.dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)


        val origin = intent.getStringExtra(INTENT_ORIGIN)
            ?: throw IllegalStateException("field $INTENT_ORIGIN missing in Intent")
        val destination = intent.getStringExtra(INTENT_DESTINATION)
            ?: throw IllegalStateException("field $INTENT_DESTINATION missing in Intent")
        val fromDateTime = intent.getStringExtra(INTENT_FROM_DATE_TIME)
            ?: throw IllegalStateException("field $INTENT_FROM_DATE_TIME missing in Intent")

        presenter.makeScheduleRequest(origin, destination, fromDateTime)
    }

    override fun setPresenter(): SchedulePresenter {
        return SchedulePresenter(this)
    }

    override fun showSchedules(schedules: List<Entities.Schedule>) {
        scheduleAdapter.updateSchedules(schedules)
    }

    override fun showLoading() {
        binding.progressVisibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressVisibility = View.GONE
    }

    override fun showError(error: String?) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun onPause() {
        super.onPause()
        presenter.disposeSubscription()
    }
}