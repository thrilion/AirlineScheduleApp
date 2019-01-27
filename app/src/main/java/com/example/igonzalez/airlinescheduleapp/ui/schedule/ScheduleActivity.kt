package com.example.igonzalez.airlinescheduleapp.ui.schedule

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.igonzalez.airlinescheduleapp.R
import com.example.igonzalez.airlinescheduleapp.model.Entities
import com.example.igonzalez.airlinescheduleapp.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_schedule.*

class ScheduleActivity : BaseActivity<SchedulePresenter>(), ScheduleView {

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
        setContentView(R.layout.activity_schedule)

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun printDuration(duration: String) {
        txt_search_result.text = duration
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(error: String?) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

}