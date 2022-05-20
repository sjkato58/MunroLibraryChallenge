package com.katocoding.munrolibrarychallenge.daggerhilt

import android.content.Context
import com.katocoding.munrolibrarychallenge.data.errors.DataErrorHandler
import com.katocoding.munrolibrarychallenge.data.munrolist.MunroListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModue {

    @Singleton
    @Provides
    fun providesDataErrorHandler(
        @ApplicationContext context: Context
    ): DataErrorHandler = DataErrorHandler(context)

    @Singleton
    @Provides
    fun providesMunroListRepository(
        dataErrorHandler: DataErrorHandler
    ) = MunroListRepository(dataErrorHandler)
}