package com.ccc.chatapp.data.source

import com.ccc.chatapp.data.source.local.sharedprf.SharedPrefsApi
import com.ccc.chatapp.repositories.UserRepository
import com.ccc.chatapp.repositories.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(sharedPrefsApi: SharedPrefsApi): UserRepository {
        return UserRepositoryImpl(sharedPrefsApi = sharedPrefsApi)
    }
}
