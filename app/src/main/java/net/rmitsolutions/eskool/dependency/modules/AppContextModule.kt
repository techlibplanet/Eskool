package net.rmitsolutions.eskool.dependency.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import net.rmitsolutions.eskool.dependency.qualifiers.ApplicationContextQualifier
import net.rmitsolutions.eskool.dependency.scopes.ApplicationScope

/**
 * Created by Madhu on 19-Jun-2017.
 */

@Module
class AppContextModule(@ApplicationContextQualifier val context: Context) {
    @Provides
    @ApplicationScope
    @ApplicationContextQualifier
    fun provideContext(): Context {
        return context
    }
}