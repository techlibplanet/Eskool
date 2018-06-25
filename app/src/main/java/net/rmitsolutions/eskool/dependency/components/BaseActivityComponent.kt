package net.rmitsolutions.eskool.dependency.components

import dagger.Component
import net.rmitsolutions.eskool.BaseActivity
import net.rmitsolutions.eskool.attendance.AttendanceActivity
import net.rmitsolutions.eskool.cafeteria.CafeteriaActivity
import net.rmitsolutions.eskool.calendar.CalendarActivity
import net.rmitsolutions.eskool.contact.ContactActivity
import net.rmitsolutions.eskool.dashboard.DashboardActivity
import net.rmitsolutions.eskool.dependency.scopes.ActivityScope
import net.rmitsolutions.eskool.documents.DocumentsActivity
import net.rmitsolutions.eskool.network.AuthGlideModule
import net.rmitsolutions.eskool.sample.SampleActivity

/**
 * Created by Madhu on 19-Jun-2017.
 */
@ActivityScope
@Component(dependencies = arrayOf(ApplicationComponent::class))
interface BaseActivityComponent {
    fun injectBaseActivity(baseActivity: BaseActivity)
    fun injectDashboardActivity(activity: DashboardActivity)
    fun injectDocumentsActivity(activity: DocumentsActivity)
    fun injectCafeteriaActivity(activity: CafeteriaActivity)
    fun injectContactActivity(activity: ContactActivity)
    fun injectCalendarActivity(activity: CalendarActivity)
    fun injectAttendanceActivity(activity: AttendanceActivity)
    fun injectGlideModule(glideModule: AuthGlideModule)
    fun injectSampleActivity(activity: SampleActivity)
}