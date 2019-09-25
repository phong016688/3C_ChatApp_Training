package com.ccc.chatapp

import android.content.Context
import com.ccc.chatapp.data.source.local.sharedprf.SharedPrefsApi
import com.ccc.chatapp.data.source.local.sharedprf.SharedPrefsImpl
import com.ccc.chatapp.utils.rx.SchedulerProvider
import com.ccc.chatapp.utils.rx.SchedulerProviderImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val mContext: Context) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return mContext
    }

    @Provides
    @Singleton
    fun provideSharedPrefsApi(): SharedPrefsApi {
        return SharedPrefsImpl(mContext)
    }

    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider {
        return SchedulerProviderImpl.instance
    }
}
