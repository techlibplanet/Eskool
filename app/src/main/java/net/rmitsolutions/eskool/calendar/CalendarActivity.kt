package net.rmitsolutions.eskool.calendar

import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import com.github.sundeepk.compactcalendarview.domain.Event
import net.rmitsolutions.eskool.CalendarBaseActivity
import net.rmitsolutions.eskool.ESkoolApplication
import net.rmitsolutions.eskool.R
import net.rmitsolutions.eskool.dependency.components.DaggerBaseActivityComponent
import net.rmitsolutions.eskool.helpers.*
import net.rmitsolutions.eskool.models.CalendarParams
import net.rmitsolutions.eskool.network.ICalendar
import kotlinx.android.synthetic.main.activity_calendar.*
import kotlinx.android.synthetic.main.include_calendar_appbar_layout.*
import java.util.*
import javax.inject.Inject

/**
 * Created by Madhu on 01-Aug-2017.
 */
class CalendarActivity : CalendarBaseActivity() {
    @Inject
    lateinit var calendarService: ICalendar

    private val adapter: CalendarAdapter by lazy { CalendarAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val depComponent = DaggerBaseActivityComponent.builder()
                .applicationComponent(ESkoolApplication.applicationComponent)
                .build()

        depComponent.injectCalendarActivity(this)

        Handler().post { titleView.text = getString(R.string.dashboard_module_calendar) }
        compactCalendarView.shouldDrawIndicatorsBelowSelectedDays(true)

        calendarEventsRecyclerView.layoutManager = LinearLayoutManager(this)
        calendarEventsRecyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH))
        getEvents(cal.time)
    }

    private fun getEvents(firstDayOfMonth: Date) {
        showProgress()
        val cal = Calendar.getInstance()
        cal.time = firstDayOfMonth
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH))

        compactCalendarView.removeAllEvents()
        adapter.items = emptyList()
        adapter.notifyDataSetChanged()
        val eventColor = resources.getColor(R.color.light_green_shade)

        val data = CalendarParams(firstDayOfMonth, cal.time)
        compositeDisposable.add(calendarService.getEvents(apiAccessToken, data)
                .processRequest(
                        { result ->
                            hideProgress()

                            val events = result.list
                            adapter.items = events
                            adapter.notifyDataSetChanged()

                            compactCalendarView.addEvents(
                                    events.map { Event(eventColor, it.eventDate.time) }
                            )
                        },
                        { message ->
                            hideProgress(true, message)
                        }
                )
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishNoAnim()
    }

    override fun onDateSelected(selectedDate: Date) {
        //nothing to do here
    }

    override fun onMonthSelected(firstDayOfNewMonth: Date) {
        getEvents(firstDayOfNewMonth)
    }
}