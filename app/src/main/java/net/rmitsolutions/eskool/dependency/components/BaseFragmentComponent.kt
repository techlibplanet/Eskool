package net.rmitsolutions.eskool.dependency.components

import net.rmitsolutions.eskool.circulars.CircularsFragment
import net.rmitsolutions.eskool.dependency.scopes.ActivityScope
import net.rmitsolutions.eskool.fee.FeeFragment
import net.rmitsolutions.eskool.library.LibraryFragment
import net.rmitsolutions.eskool.profile.ProfileFragment
import net.rmitsolutions.eskool.timetable.TimeTableFragment
import net.rmitsolutions.eskool.transport.TransportFragment
import dagger.Component

/**
 * Created by Madhu on 06-Jul-2017.
 */
@ActivityScope
@Component(dependencies = arrayOf(ApplicationComponent::class))
interface BaseFragmentComponent {
    fun injectProfileFragment(fragment: ProfileFragment)
    fun injectCircularsFragment(fragment: CircularsFragment)
    fun injectLibraryFragment(fragment: LibraryFragment)
    fun injectTransportFragment(fragment: TransportFragment)
    fun injectFeeFragment(fragment: FeeFragment)
    fun injectTimeTableFragment(fragment: TimeTableFragment)
}