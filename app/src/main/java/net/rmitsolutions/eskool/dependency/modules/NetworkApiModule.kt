package net.rmitsolutions.eskool.dependency.modules

import net.rmitsolutions.eskool.dependency.scopes.ApplicationScope
import net.rmitsolutions.eskool.network.*
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


/**
 * Created by Madhu on 19-Jun-2017.
 */
@Module(includes = arrayOf(HttpModule::class))
class NetworkApiModule {
    @Provides
    @ApplicationScope
    fun studentService(retrofit: Retrofit): IStudent {
        return retrofit.create(IStudent::class.java)
    }

    @Provides
    @ApplicationScope
    fun documentsService(retrofit: Retrofit): IDocuments {
        return retrofit.create(IDocuments::class.java)
    }

    @Provides
    @ApplicationScope
    fun fileDownloadService(retrofit: Retrofit): IFileDownload {
        return retrofit.create(IFileDownload::class.java)
    }

    @Provides
    @ApplicationScope
    fun libraryService(retrofit: Retrofit): ILibrary {
        return retrofit.create(ILibrary::class.java)
    }

    @Provides
    @ApplicationScope
    fun cafeteriaService(retrofit: Retrofit): ICafeteria {
        return retrofit.create(ICafeteria::class.java)
    }

    @Provides
    @ApplicationScope
    fun transportService(retrofit: Retrofit): ITransport {
        return retrofit.create(ITransport::class.java)
    }

    @Provides
    @ApplicationScope
    fun feeService(retrofit: Retrofit): IFee {
        return retrofit.create(IFee::class.java)
    }

    @Provides
    @ApplicationScope
    fun masterDataService(retrofit: Retrofit): IMasterData {
        return retrofit.create(IMasterData::class.java)
    }

    @Provides
    @ApplicationScope
    fun timeTableService(retrofit: Retrofit): ITimeTable {
        return retrofit.create(ITimeTable::class.java)
    }

    @Provides
    @ApplicationScope
    fun calendarService(retrofit: Retrofit): ICalendar {
        return retrofit.create(ICalendar::class.java)
    }

    @Provides
    @ApplicationScope
    fun attendanceService(retrofit: Retrofit): IAttendance {
        return retrofit.create(IAttendance::class.java)
    }
}